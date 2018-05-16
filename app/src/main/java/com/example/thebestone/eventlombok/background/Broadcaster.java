package com.example.thebestone.eventlombok.background;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.thebestone.eventlombok.R;
import com.example.thebestone.eventlombok.activity.MainActivity;

public class Broadcaster extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);

        NotificationCompat.Builder notif = new NotificationCompat.Builder(context);
        notif.setSmallIcon(R.mipmap.ic_launcher_round);
        notif.setContentTitle("Ada Agenda Hari Ini");
        notif.setContentText("                                                                                 Siapkan dirimu hari ini untuk menghadiri agenda-agenda");
        notif.setContentIntent(pi);
        notif.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);


        NotificationManagerCompat nmc = NotificationManagerCompat.from(context);
        nmc.notify(1, notif.build());

    }
}
