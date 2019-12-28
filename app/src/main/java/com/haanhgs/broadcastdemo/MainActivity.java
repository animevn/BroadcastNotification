package com.haanhgs.broadcastdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.BigPictureStyle;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int ID = 220479;
    //key for save state
    private static final String UPDATE = "update";
    private static final String NOTIFY = "notify";
    private static final String CANCEL = "cancel";
    private static final String TEXT = "text";

    private Button bnNotify;
    private Button bnUpdate;
    private Button bnCancel;
    private TextView tvReply;
    private Broadcast receiver;


    private void initViews(){
        bnNotify = findViewById(R.id.bnNotify);
        bnUpdate = findViewById(R.id.bnUpdate);
        bnCancel = findViewById(R.id.bnCancel);
        tvReply = findViewById(R.id.tvReply);
    }

    private void setButtonState(boolean notify, boolean update, boolean cancel){
        bnNotify.setEnabled(notify);
        bnUpdate.setEnabled(update);
        bnCancel.setEnabled(cancel);
    }

    private void initRecever(){
        receiver = new Broadcast();
        receiver.setListener(new BroadcastObserver() {
            @Override
            public void onBroadcastReceive(CharSequence charSequence) {
                tvReply.setText(charSequence);
                NotificationManager manager =
                        (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                if (manager != null){
                    manager.cancel(ID);
                }
            }
        });
        registerReceiver(receiver, new IntentFilter(Notification.getActionRemote()));
    }

    private void createNotify(){
        Notification.createNotification(ID);
        setButtonState(false, true, true);
    }

    private void handleNotifyButton(){
        bnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotify();
            }
        });
    }

    private void createUpdate(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(bitmap);
        style.setBigContentTitle("UPDATE BIG HUH");
        Notification.updateNotification(ID, style);
        tvReply.setText(R.string.tvReply);
        setButtonState(false, false, true);
    }

    private void handleUpdateButton(){
        bnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUpdate();
            }
        });
    }

    private void createCancel(){
        NotificationManager manager =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (manager != null){
            manager.cancel(ID);
        }
        tvReply.setText(R.string.tvReply);
        setButtonState(true, false, false);
    }

    private void handleCancelButton(){
        bnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCancel();
            }
        });
    }

    private void loadSavedState(Bundle bundle){
        if (bundle != null){
            bnNotify.setEnabled(bundle.getBoolean(NOTIFY));
            bnUpdate.setEnabled(bundle.getBoolean(UPDATE));
            bnCancel.setEnabled(bundle.getBoolean(CANCEL));
            tvReply.setText(bundle.getString(TEXT));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setButtonState(true, false, false);
        initRecever();
        loadSavedState(savedInstanceState);

        handleNotifyButton();
        handleUpdateButton();
        handleCancelButton();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT, tvReply.getText().toString());
        outState.putBoolean(NOTIFY, bnNotify.isEnabled());
        outState.putBoolean(UPDATE, bnUpdate.isEnabled());
        outState.putBoolean(CANCEL, bnCancel.isEnabled());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
