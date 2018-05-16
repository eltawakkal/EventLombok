package com.example.thebestone.eventlombok.sqlite;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;

import com.example.thebestone.eventlombok.models.User;
import com.example.thebestone.eventlombok.models.UserEvent;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper {

    SqliteConfigQ dbConfig;
    Activity activity;

    SQLiteDatabase db;
    Cursor cursor;

    public SqliteHelper(Activity activity) {
        this.activity = activity;

        dbConfig = new SqliteConfigQ(activity);
    }

    public void addEvent(String kodeEvent, String emailUser, String photoUser, String namaEvent, String deskEvent, String photoEvent
            , String jenisEvent, String waktuEvent, String tglEvent, String lokasiEvent) {
        try {
            db = dbConfig.getWritableDatabase();
            db.execSQL("INSERT INTO TBFAV (KODEEVENT, EMAILUSER, PHOTOUSER, NAMAEVENT, DESKEVENT, PHOTOEVENT, JENISEVENT, WAKTUEVENT, TGLEVENT, LOKASIEVENT)" +
                    "VALUES ('"+ kodeEvent +"', '"+ emailUser +"', '"+ photoUser +"', '"+ namaEvent +"', '"+ deskEvent +"', '"+ photoEvent +"', '"+ jenisEvent +"', '"+ waktuEvent +"','"+ tglEvent +"', '"+ lokasiEvent +"')");
        } catch (SQLiteException e) {
            Toast.makeText(activity, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteEvent(String kode) {
        db = dbConfig.getWritableDatabase();
        db.execSQL("delete from tbfav where kodeEvent = '"+ kode +"'");
    }

    public List<UserEvent> selectAllFav() {
        db = dbConfig.getReadableDatabase();
        cursor = db.rawQuery("select * from tbfav order by tglEvent asc", null);

        List<UserEvent> userEvents = new ArrayList<>();

        if (cursor.getColumnCount()!=0) {
            for (int i=0; i<cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                UserEvent userEvent = new UserEvent(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), "", cursor.getString(6), cursor.getString(7),
                        cursor.getString(8), cursor.getString(9));
                userEvents.add(userEvent);
            }
        }

        return userEvents;
    }

    public boolean dataTidakDouble(String kodeEvent) {
        db = dbConfig.getReadableDatabase();
        cursor = db.rawQuery("select * from tbFav where kodeEvent = '"+ kodeEvent +"'", null);
        if (cursor.getCount() == 0) {
            return true;
        } else
            return false;
    }
}
