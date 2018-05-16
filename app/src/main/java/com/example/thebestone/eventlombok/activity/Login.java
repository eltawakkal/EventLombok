package com.example.thebestone.eventlombok.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.thebestone.eventlombok.PreferencesEvent;
import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Login extends AppCompatActivity {

    DatabaseReference dbRefUser;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount gsa;

    PreferencesEvent myPref;

    SignInButton signInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        cekUser();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = gsc.getSignInIntent();
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            final GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            Query query = dbRefUser.orderByChild("emailUser").equalTo(account.getEmail());

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    User user = null;
                    String id="";

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user = snapshot.getValue(User.class);
                        id = snapshot.getKey();
                    }

                    if (user == null) {
                        try {
                            showPersetujuan(id, account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString(), "Member");
                        } catch (Exception e) {
                            showPersetujuan(id, account.getDisplayName(), account.getEmail(), "noPhoto", "Member");
                        }
                    } else {
                        try {
                            myPref.setUser(id, account.getDisplayName(), account.getEmail(), account.getPhotoUrl().toString(), user.getStatus());
                        }catch (Exception e) {
                            myPref.setUser(id, account.getDisplayName(), account.getEmail(), "noPhoto", user.getStatus());
                        }

                        loggedIn();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } catch (ApiException e) {

        }
    }

//    public void signOut(View v) {
//        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                Toast.makeText(Login.this, "Signed Out", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void init() {

        signInButton = findViewById(R.id.sign_button_login);

        dbRefUser = FirebaseDatabase.getInstance().getReference("users");
        myPref = new PreferencesEvent(this);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
    }

    public void cekUser() {
        gsa = GoogleSignIn.getLastSignedInAccount(this);
        if (gsa != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    public void showPersetujuan(final String key, final String nama, final String email, final String photoUrl, final String status) {
        View v = getLayoutInflater().inflate(R.layout.dialog_persetujuan, null);

        Button btRegister = v.findViewById(R.id.btRegister);

        AlertDialog.Builder alert = new AlertDialog.Builder(this).setView(v);
        final Dialog dialog = alert.create();
        dialog.show();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User userAdd = new User(nama, email, photoUrl, status);

                String id = dbRefUser.push().getKey();
                dbRefUser.child(id).setValue(userAdd);

                myPref.setUser(key, nama, email, photoUrl, status);

                loggedIn();

                dialog.dismiss();
            }
        });
    }

    public void loggedIn(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
