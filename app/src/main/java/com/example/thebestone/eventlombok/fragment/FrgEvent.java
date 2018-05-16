package com.example.thebestone.eventlombok.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.adapter.RecyclerEventBerandaQ;
import com.example.thebestone.eventlombok.models.User;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FrgEvent extends Fragment {

    RecyclerView mRecycler;
    FloatingActionButton fabFilter;
//    CardView cardDatabaru;

    RecyclerEventBerandaQ adapterQ;
    DatabaseReference dbRefEvents, dbRefUsers;

    boolean firstShow = true;

    String[] arrayKab = {"Bima", "Dompu", "Lombok Barat", "Lombok Tengah", "Lombok Timur", "Lombok Utara",
            "Sumbawa", "Sumbawa Bara", "Kota Bima", "Kota Mataram"};

    List<User> users;
    List<UserEvent> eventS;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_event, null);

        init(v);

        mRecycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fabFilter.hide();
                } else if (dy < 0) {
                    fabFilter.show();
                }
            }
        });

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertFilter();
            }
        });
//        cardDatabaru.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setRecyclerItems();
//                cardDatabaru.setVisibility(View.GONE);
//            }
//        });

        return v;
    }

    public void listenDataChange() {
        dbRefEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventS.clear();
                users.clear();

                List<UserEvent> eventTamp = new ArrayList<>();

                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    UserEvent userEvent = eventSnapshot.getValue(UserEvent.class);
                    eventTamp.add(userEvent);
                }

                for (int i = eventTamp.size()-1; i>=0; i--) {
                    eventS.add(eventTamp.get(i));
                }

                setRecyclerItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRefUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        listenDataChange();
    }

    private void init(View v) {
        eventS = new ArrayList<>();
        users = new ArrayList<>();

        dbRefEvents = FirebaseDatabase.getInstance().getReference("events");
        dbRefUsers = FirebaseDatabase.getInstance().getReference("users");

        mRecycler = v.findViewById(R.id.recycler_main);
        fabFilter = v.findViewById(R.id.fabFilterMain);
//        cardDatabaru = v.findViewById(R.id.cardDataBaruBeranda);
    }

    public void setRecyclerItems() {
        firstShow = false;
        adapterQ = new RecyclerEventBerandaQ(getActivity(), users, eventS);

        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(adapterQ);
    }

    public void alertFilter() {
        Spinner spinKab, spinBulan;
        View v = getLayoutInflater().inflate(R.layout.filter_layout, null);

        spinKab = v.findViewById(R.id.spinKabMain);
        spinBulan = v.findViewById(R.id.spinBulanMain);


        AlertDialog.Builder setKab = new AlertDialog.Builder(getContext());
        setKab
                .setView(v)
                .setPositiveButton("Oke", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        frgEvent.filterData("silicon");
                    }
                });
        Dialog dialog = setKab.create();
        dialog.show();
    }

}
