package com.example.thebestone.eventlombok.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thebestone.eventlombok.PreferencesEvent;
import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.activity.TambahEvent;
import com.example.thebestone.eventlombok.adapter.RecyclerProfilAdapterQ;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FrgProfile extends Fragment implements View.OnClickListener{

    FloatingActionButton fabTambahEvent;
    RecyclerView mRecyclerProfil;

    RecyclerProfilAdapterQ adapterQ;
    DatabaseReference dbRefEvents;

    List<UserEvent> listUserEvent;

    PreferencesEvent myPref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frg_profile, container, false);

        init(v);

        fabTambahEvent.setOnClickListener(this);

        setDataChangeListener();

        mRecyclerProfil.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    fabTambahEvent.hide();
                } else if (dy < 0) {
                    fabTambahEvent.show();
                }
            }
        });

        return v;
    }

    public void setDataChangeListener() {

        dbRefEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listUserEvent.clear();
                List<UserEvent> eventTamp = new ArrayList<>();

                for (DataSnapshot dataEvents : dataSnapshot.getChildren()) {
                    UserEvent event = dataEvents.getValue(UserEvent.class);
                    eventTamp.add(event);
                }

                for (int i = eventTamp.size()-1; i>=0; i--) {
                    if (eventTamp.get(i).getEmailUser().equals(myPref.getUserEmail())) {
                        listUserEvent.add(eventTamp.get(i));
                    }
                }

                setRecyclerItems();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setRecyclerItems() {
        adapterQ = new RecyclerProfilAdapterQ(listUserEvent, getActivity());
        mRecyclerProfil.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerProfil.setAdapter(adapterQ);
    }

    private void init(View v) {
        myPref = new PreferencesEvent(getActivity());

        dbRefEvents = FirebaseDatabase.getInstance().getReference("events");
        listUserEvent = new ArrayList<>();

        fabTambahEvent = v.findViewById(R.id.fabTambahEvent);
        mRecyclerProfil = v.findViewById(R.id.recycler_profile);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabTambahEvent:
                startActivity(new Intent(getActivity(), TambahEvent.class));
        }
    }
}
