package com.example.thebestone.eventlombok.firebase;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebase {

    private Activity contex;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;

    public void firebase(Activity contex) {
        this.contex = contex;

        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference();
    }
}
