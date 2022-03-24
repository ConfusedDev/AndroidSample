package com.test.bradleyprieskornschedulerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class MyReceiver extends BroadcastReceiver {

    String channel_id="test";
    static int notificationId;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification n = new NotificationCompat.Builder(context, channel_id)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("message"))
                .setContentTitle("NotificationTest").build();
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId++, n);
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
    }
    private void createNotificationChannel(Context context, String channelId){
        CharSequence name = context.getResources().getString(R.string.channelName);
        String description = context.getString(R.string.channelDescription);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(channelId, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}