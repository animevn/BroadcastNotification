package com.haanhgs.broadcastreceivernotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

public class Notification {

    private static final String CHANNEL_ID = "com.haanhgs.broadcastreceivernotification_ID";
    private static final String CHANNEL_NAME = "com.haanhgs.broadcastreceivernotification";
    private static final int REQUEST = 1979;
    private static final String ACTION_REMOTE = "action_remote";
    private static final String REMOTE_RESULT_KEY = "remote_result";

    private static void createChannel(){
        NotificationManager manager =
                (NotificationManager)App.context().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && manager != null){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Notice");
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.GREEN);
            manager.createNotificationChannel(channel);
        }
    }

    private static PendingIntent createOpenMainIntent(){
        Intent intent = new Intent(App.context(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return PendingIntent.getActivity(App.context(), REQUEST, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static NotificationCompat.Action createRemoteInput(){
        Intent intent = new Intent(ACTION_REMOTE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(App.context(), REQUEST, intent,
                PendingIntent.FLAG_ONE_SHOT);
        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_RESULT_KEY)
                .setLabel("Reply")
                .build();
        return new NotificationCompat.Action.Builder(R.drawable.reply, "Reply", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

    private static NotificationCompat.Builder createBuilder(){
        return new NotificationCompat.Builder(App.context(), CHANNEL_ID)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(createOpenMainIntent())
                .setSmallIcon(R.drawable.star)
                .setContentTitle("Too much")
                .addAction(createRemoteInput())
                .setContentText("text content");
    }

    public static void createNotification(int id){
        NotificationManager manager =
                (NotificationManager)App.context().getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();
        if (!App.isAppVisible() && manager != null){
            manager.notify(id, createBuilder().build());
        }
    }
}
