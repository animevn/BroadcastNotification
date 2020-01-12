package com.haanhgs.broadcastdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bnNotify)
    Button bnNotify;
    @BindView(R.id.tvReply)
    TextView tvReply;
    @BindView(R.id.bnUpdate)
    Button bnUpdate;
    @BindView(R.id.bnCancel)
    Button bnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.bnNotify, R.id.bnUpdate, R.id.bnCancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnNotify:
                break;
            case R.id.bnUpdate:
                break;
            case R.id.bnCancel:
                break;
        }
    }
}
