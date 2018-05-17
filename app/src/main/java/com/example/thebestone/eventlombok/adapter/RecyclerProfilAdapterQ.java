package com.example.thebestone.eventlombok.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebestone.eventlombok.PreferencesEvent;
import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.activity.DetailsEvent;
import com.example.thebestone.eventlombok.activity.Login;
import com.example.thebestone.eventlombok.activity.MainActivity;
import com.example.thebestone.eventlombok.activity.TambahEvent;
import com.example.thebestone.eventlombok.helper.PublicVar;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerProfilAdapterQ extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static int TYPE_HEADER = 0;
    private static int TYPE_ITEM = 1;

    List<UserEvent> listUserEvents;
    Activity context;

    GoogleSignInClient gsc;
    GoogleSignInOptions gso;

    DatabaseReference dbRef;
    StorageReference storageRef;

    PreferencesEvent myPref;

    public RecyclerProfilAdapterQ(List<UserEvent> listUserEvents, Activity context) {
        this.context = context;
        this.listUserEvents = listUserEvents;

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(context, gso);

        myPref = new PreferencesEvent(context);

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
           View v = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.row_header_profile, parent, false);
           return new ViewHolderHeader(v);
        } else {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_event_prof, parent, false);
            return new ViewHolderItem(v);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ViewHolderItem) {
            ViewHolderItem vhItem = (ViewHolderItem) holder;

            String tglEvent = listUserEvents.get(position - 1).getTglEvent() + ", " + listUserEvents.get(position-1).getWaktuEvent();

            vhItem.txtJudul.setText(listUserEvents.get(position - 1).getNamaEvent());
            vhItem.txtLokasi.setText(listUserEvents.get(position - 1).getLokasiEvent());
            vhItem.txtWaktu.setText(tglEvent);

            vhItem.imgEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, DetailsEvent.class));
                    PublicVar.userEventPublic = listUserEvents.get(position-1);
                }
            });

            vhItem.imgPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(view, position);
                }
            });

            try {
                Picasso.get().load(listUserEvents.get(position-1).getPhotoEvent()).
                        resize(700, 400).into(vhItem.imgEvent);
            } catch (Exception e) {
                Picasso.get().load(R.drawable.wall_sample).into(vhItem.imgEvent);
            }


        } else if (holder instanceof ViewHolderHeader) {
            ViewHolderHeader vHD = (ViewHolderHeader) holder;

            vHD.tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Kamu Telah Keluar", Toast.LENGTH_SHORT).show();

                            myPref.deleteUser();
                            Pair pair = new Pair<View, String> (MainActivity.tvLogo, "transLogo");
                            Intent i = new Intent(context, Login.class);
                            ActivityOptions ao = ActivityOptions.makeSceneTransitionAnimation(context, pair);
                            context.startActivity(i, ao.toBundle());
                            context.finish();
                        }
                    });
                }
            });

            vHD.tvNama.setText(myPref.getUserName());
            vHD.tvEmail.setText(myPref.getUserEmail());

            try {
                Picasso.get().load(myPref.getUserPhoto()).placeholder(R.mipmap.ic_launcher_round)
                        .resize(200, 200).into(vHD.imgProfil);
            } catch (Exception e) {
                Picasso.get().load(R.mipmap.ic_launcher_round).resize(200, 200)
                        .into(vHD.imgProfil);
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return listUserEvents.size() + 1;
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView txtJudul, txtLokasi, txtWaktu;
        ImageView imgEvent, imgPopup;

        public ViewHolderItem(View v) {
            super(v);

            txtJudul = v.findViewById(R.id.txtJudulEventProfil);
            txtLokasi = v.findViewById(R.id.txtLokasiEventProfil);
            txtWaktu = v.findViewById(R.id.txtWaktuEventProfil);
            imgEvent = v.findViewById(R.id.imgWallEventProw);
            imgPopup = v.findViewById(R.id.imgPopupProfil);
        }
    }

    public class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView tvNama, tvEmail, tvLogout;
        CircleImageView imgProfil;

        public ViewHolderHeader(View v) {
            super(v);

            tvLogout = v.findViewById(R.id.tvLogout);
            tvNama = v.findViewById(R.id.tvNamaProfil);
            tvEmail = v.findViewById(R.id.tvEmailProfil);

            imgProfil = v.findViewById(R.id.imgUserProfil);
        }
    }

    public void showPopupMenu(View v, final int position) {
        PopupMenu popMenu = new PopupMenu(context, v);
        final MenuInflater inflater = popMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_card_event_user, popMenu.getMenu());
        popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.mnu_card_user_hapus:
                        deleteEvent(listUserEvents.get(position-1).getKodeEvent(), listUserEvents.get(position-1).getPhotoEvent());
                        break;
                    case R.id.mnu_card_user_edit:
                        PublicVar.userEventPublic = listUserEvents.get(position-1);
                        Intent intent = new Intent(context, TambahEvent.class);
                        intent.putExtra("edit", true);
                        context.startActivity(intent);
                        break;
                }
                return false;
            }
        });
        popMenu.show();
    }

    private void deleteEvent(final String kodeEvent, final String photoEventUrl) {
        AlertDialog.Builder hapus = new AlertDialog.Builder(context);
        hapus
                .setTitle("Hapus Event")
                .setMessage("Yakin Hapus Event Ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final ProgressDialog pd = new ProgressDialog(context);
                        pd.setTitle("Menghapus");
                        pd.setMessage("Proses Menghapus Event...");
                        pd.setCancelable(false);
                        pd.show();

                        dbRef = FirebaseDatabase.getInstance().getReference("events").child(kodeEvent);
                        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(photoEventUrl);

                        storageRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                dbRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        pd.dismiss();
                                        Toast.makeText(context, "Event Dihapus", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                })
                .setNegativeButton("Batal", null)
                .create().show();

    }
}
