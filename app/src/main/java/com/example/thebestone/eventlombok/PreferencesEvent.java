package com.example.thebestone.eventlombok;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.thebestone.eventlombok.models.User;

import java.util.List;

public class PreferencesEvent {

    private static String PREF_NAME = "eventPref";

    SharedPreferences myPref;
    SharedPreferences.Editor editor;

    Activity context;

    public PreferencesEvent(Activity context) {

        this.context = context;

        myPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setUser(String kodeUser, String name, String email, String imgUrl) {
        editor = myPref.edit();

        editor
                .putString("kodeUser", kodeUser)
                .putString("nama", name)
                .putString("email", email)
                .putString("imgUrl", imgUrl);

        editor.commit();
    }

    public String getUserName() {
        String nama = "";
        nama = myPref.getString("nama", null);
        return nama;
    }

    public String getUserEmail() {
        String email = "";
        email = myPref.getString("email", null);
        return email;
    }

    public String getUserPhoto() {
        String photo = "";
        photo = myPref.getString("imgUrl", null);
        return photo;
    }

    public String getUserKey() {
        String kodeUser = "";
        kodeUser = myPref.getString("kodeUser", null);
        return kodeUser;
    }
}
