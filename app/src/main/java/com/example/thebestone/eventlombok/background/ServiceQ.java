package com.example.thebestone.eventlombok.background;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.activity.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceQ extends Service {

    DatabaseReference myRef;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        myRef = FirebaseDatabase.getInstance().getReference("evnts");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
                mediaPlayer.start();

                Toast.makeText(context, "Service Started", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(ServiceQ.this, MainActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(ServiceQ.this, 1, intent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                Notification.Builder notif = new Notification.Builder(ServiceQ.this);
                notif
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("Event Baru Tersedia")
                        .setContentText("Klik untuk melihat kegiatan baru");

                NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, notif.build());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
