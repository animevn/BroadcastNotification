package com.haanhgs.broadcastnotification;

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
    public static final String REMOTE_ACTION = "action_remote";
    public static final String REMOTE_RESULT = "remote_result";

    private final Context context;
    private final NotificationManager manager;

    public Notification(Context context) {
        this.context = context;
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void createChannel(){
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

    private PendingIntent createOpenMainIntent(){
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        return PendingIntent.getActivity(
                context, REQUEST, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private NotificationCompat.Action createRemoteInput(){
        Intent intent = new Intent(REMOTE_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST, intent,
                PendingIntent.FLAG_ONE_SHOT);
        RemoteInput remoteInput = new RemoteInput.Builder(REMOTE_RESULT).setLabel("Reply").build();
        return new NotificationCompat.Action.Builder(R.drawable.reply, "Reply", pendingIntent)
                .addRemoteInput(remoteInput)
                .build();
    }

    private NotificationCompat.Builder createBuilder(){
        return new NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(createOpenMainIntent())
                .setSmallIcon(R.drawable.star)
                .setContentTitle("Too much")
                .setContentText("text content");
    }

    public void createNotification(int id){
        createChannel();
        NotificationCompat.Builder builder = createBuilder();
        builder.addAction(createRemoteInput());
        if (manager != null){
            manager.notify(id, builder.build());
        }
    }

    public void updateNotification(int id, NotificationCompat.BigPictureStyle style){
        createChannel();
        NotificationCompat.Builder builder = createBuilder();
        builder.setStyle(style);
        if (manager != null){
            manager.notify(id, builder.build());
        }
    }

}
