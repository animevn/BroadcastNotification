package com.haanhgs.broadcastdemo;

import android.app.NotificationManager;
import android.content.Context;

public class Notification {

    private static final String CHANNEL_ID = "com.haanhgs.broadcastreceivernotification_ID";
    private static final String CHANNEL_NAME = "com.haanhgs.broadcastreceivernotification";
    private static final int REQUEST = 1979;
    private static final String REMOTE_ACTION = "action_remote";
    public static final String REMOTE_RESULT = "remote_result";

    private final Context context;
    private final NotificationManager manager;

    public Notification(Context context) {
        this.context = context;
        manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
