package com.example.calreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getExtras().getString("Title");
        String text = intent.getExtras().getString("Text");
        int id = intent.getExtras().getInt("Id");
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder builder = notificationHelper.getBuilder(title, text);
        notificationHelper.getManager().notify(id, builder.build());
    }
}
