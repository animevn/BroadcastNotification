package com.haanhgs.broadcastnotification;

import android.app.NotificationManager;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BroadcastObserver{

    @BindView(R.id.bnNotify)
    Button bnNotify;
    @BindView(R.id.tvReply)
    TextView tvReply;
    @BindView(R.id.bnUpdate)
    Button bnUpdate;
    @BindView(R.id.bnCancel)
    Button bnCancel;

    private Broadcast receiver;
    private static final int ID = 220479;
    private Notification notification;
    private NotificationManager manager;

    private void initReceiver(){
        receiver = new Broadcast();
        registerReceiver(receiver, new IntentFilter(Notification.REMOTE_ACTION));
    }

    private void initNotification(){
        manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notification = new Notification(this);
    }

    private void setButtonState(boolean notify, boolean update, boolean cancel){
        bnNotify.setEnabled(notify);
        bnUpdate.setEnabled(update);
        bnCancel.setEnabled(cancel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initReceiver();
        initNotification();
        setButtonState(true, false, false);
    }

    private void handleNotify(){
        notification.createNotification(ID);
        setButtonState(false, true, true);
    }

    private void handleUpdate(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
        style.bigPicture(bitmap);
        style.setBigContentTitle("BIG BIG WORLD");
        notification.updateNotification(ID, style);
        tvReply.setText(R.string.tvReply);
        setButtonState(false, false, true);
    }

    private void handleCancel(){
        if (manager != null) manager.cancel(ID);
        tvReply.setText(R.string.tvReply);
        setButtonState(true, false, false);
    }

    @OnClick({R.id.bnNotify, R.id.bnUpdate, R.id.bnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnNotify:
                handleNotify();
                break;
            case R.id.bnUpdate:
                handleUpdate();
                break;
            case R.id.bnCancel:
                handleCancel();
                break;
        }
    }

    @Override
    public void onBroadcastReceive(CharSequence charSequence) {
        tvReply.setText(charSequence);
        if (manager != null) manager.cancel(ID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
