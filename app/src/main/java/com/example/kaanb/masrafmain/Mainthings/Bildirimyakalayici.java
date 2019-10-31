package com.example.kaanb.masrafmain.Mainthings;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Bildirimyakalayici extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager bildirimYoneticisi = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification bildirim = intent.getParcelableExtra("bildirimNesnesi");

        bildirimYoneticisi.notify(1, bildirim);


    }
}
