package com.example.thebestone.eventlombok.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.helper.PublicVar;
import com.example.thebestone.eventlombok.models.UserEvent;
import com.example.thebestone.eventlombok.sqlite.SqliteHelper;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

public class DetailsEvent extends AppCompatActivity {

    TextView tvDeskEvent, tvLokasi, tvTgl, tvWaktu;
    String lokEvent, tglEvent, waktuEvent, namaEvent, desEvent, emailuser, kodeEvent, photoUser, jenisEvent, photoEvent;
    ImageView imgPhotoEventDetail;
    SqliteHelper dbHelper;

    int tglJadi, bulanJadi, tahunJadi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getDataFromExtras();

        setTitle(namaEvent);

        init();

        tvDeskEvent.setText(desEvent);
        tvLokasi.setText(lokEvent);
        tvTgl.setText(tglEvent);
        tvWaktu.setText(waktuEvent);

        try {
            Picasso.get().load(photoEvent).into(imgPhotoEventDetail);
        } catch (Exception e) {
            Picasso.get().load(R.drawable.wall_sample).into(imgPhotoEventDetail);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dbHelper.dataTidakDouble(kodeEvent)) {
                    dbHelper.addEvent(kodeEvent, emailuser, photoUser, namaEvent, desEvent, photoEvent, jenisEvent, waktuEvent, tglEvent, lokEvent);
                    sparatedTgl(tglEvent);

                    Snackbar.make(view, "Dimasukkan Didalam Favorit", Snackbar.LENGTH_LONG)
                            .setAction("Batalkan", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dbHelper.deleteEvent(kodeEvent);
                                }
                            }).show();
                } else {
                    Toast.makeText(DetailsEvent.this, "Event ini tsudah ada di list agenda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sparatedTgl(String tgl) {
        String[] dataWaktu = tgl.split("/");

        tahunJadi = Integer.parseInt(dataWaktu[0]);
        bulanJadi = Integer.parseInt(dataWaktu[1]);
        tahunJadi = Integer.parseInt(dataWaktu[2]);

//        addAlarm(tglJadi, bulanJadi, tahunJadi);
    }

    private void getDataFromExtras() {
        UserEvent userEvent = PublicVar.userEventPublic;

        kodeEvent = userEvent.getKodeEvent();
        emailuser = userEvent.getEmailUser();
        photoUser = userEvent.getPhotoUser();
        namaEvent = userEvent.getNamaEvent();
        desEvent = userEvent.getDescEvent();
        lokEvent = userEvent.getLokasiEvent();
        waktuEvent = userEvent.getWaktuEvent();
        tglEvent = userEvent.getTglEvent();
        jenisEvent = userEvent.getJenisEvent();
        photoEvent = userEvent.getPhotoEvent();
    }

    private void init() {
        dbHelper = new SqliteHelper(this);

        tvDeskEvent = findViewById(R.id.tvDescEventDetail);
        tvLokasi = findViewById(R.id.tvLokasiDetails);
        tvTgl = findViewById(R.id.tvTglDetails);
        tvWaktu = findViewById(R.id.tvWaktuDetails);

        imgPhotoEventDetail = findViewById(R.id.imgEventDetail);
    }

    public void addAlarm(int tgl, int bulan, int tahun) {

        Intent i = new Intent(this, BroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);

        Date timeNow = new Date();
        Calendar cal_now = Calendar.getInstance();
        Calendar cal_alarm = Calendar.getInstance();

        cal_now.setTime(timeNow);
        cal_alarm.set(Calendar.DAY_OF_MONTH, tgl);
        cal_alarm.set(Calendar.MONTH, bulan);
        cal_alarm.set(Calendar.YEAR, tahun);
        cal_alarm.set(Calendar.HOUR, 0);
        cal_alarm.set(Calendar.MINUTE, 0);
        cal_alarm.set(Calendar.MILLISECOND, 0);

        if (cal_alarm.after(cal_now)) {
            Toast.makeText(this, "Alarm Ditambahkan", Toast.LENGTH_SHORT).show();
            am.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pi);
        } else {
            Toast.makeText(this, "Acara Telah Berakhir", Toast.LENGTH_SHORT).show();
        }
    }
}
