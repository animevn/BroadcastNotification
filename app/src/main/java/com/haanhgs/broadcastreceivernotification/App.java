package com.haanhgs.broadcastreceivernotification;

import android.app.Application;
import android.content.Context;
import java.lang.ref.WeakReference;

public class App extends Application {

    private static WeakReference<Boolean> appVisible;
    private static WeakReference<Context> context;

    public static boolean isAppVisible() {
        return appVisible.get();
    }

    public static Context context() {
        return context.get();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appVisible = new WeakReference<>(Boolean.TRUE);
        context = new WeakReference<>(getApplicationContext());
    }

    public static void onResume(){
        appVisible = new WeakReference<>(Boolean.TRUE);
    }

    public static void onPause(){
        appVisible = new WeakReference<>(Boolean.FALSE);
    }
}
