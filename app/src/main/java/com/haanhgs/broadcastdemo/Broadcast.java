package com.haanhgs.broadcastdemo;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


//this broadcast is needed solely for remoteinput, without remoteinput, this class can be disposed.
public class Broadcast extends BroadcastReceiver {

    private BroadcastObserver listener;

    public void setListener(BroadcastObserver listener) {
        this.listener = listener;
    }

    private static CharSequence getCharSequence(Intent intent){
        Bundle bundle = RemoteInput.getResultsFromIntent(intent);
        if (bundle != null){
            return bundle.getCharSequence(Notification.getRemoteResultKey());
        }else {
            return "";
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        listener.onBroadcastReceive(getCharSequence(intent));
    }
}
