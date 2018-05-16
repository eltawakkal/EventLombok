package com.example.thebestone.eventlombok.sqlite;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SqliteConfigQ extends SQLiteOpenHelper {

    private static String DATABSE_NAME = "dbEventL";
    private static int DATABSE_VERSION = 1;
    private static String CREATE_TB_FAV = "CREATE TABLE TBFAV (KODEEVENT TEXT, EMAILUSER TEXT, PHOTOUSER TEXT, NAMAEVENT TEXT, DESKEVENT TEXT, PHOTOEVENT TEXT, JENISEVENT, LOKASIEVENT TEXT, WAKTUEVENT TEXT, TGLEVENT TEXT)";

    Activity context;

    public SqliteConfigQ(Activity context) {
        super(context, DATABSE_NAME, null, DATABSE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TB_FAV);
        } catch (SQLiteException e) {
            Toast.makeText(context, "Error : " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
