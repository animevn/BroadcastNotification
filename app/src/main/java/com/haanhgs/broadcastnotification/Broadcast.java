package com.haanhgs.broadcastnotification;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class Broadcast extends BroadcastReceiver {

    private static CharSequence getChar(Intent intent){
        Bundle bundle = RemoteInput.getResultsFromIntent(intent);
        if (bundle != null){
            return bundle.getCharSequence(Notification.REMOTE_RESULT);
        }else {
            return "";
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context instanceof BroadcastObserver){
            BroadcastObserver observer = (BroadcastObserver)context;
            observer.onBroadcastReceive(getChar(intent));
        }else {
            throw new ClassCastException("must implement BroadCastObserver");
        }
    }
}
