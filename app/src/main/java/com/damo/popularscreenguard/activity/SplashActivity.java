package com.damo.popularscreenguard.activity;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.damo.popularscreenguard.R;
import com.damo.popularscreenguard.service.ScreenGuardService;

public class SplashActivity extends AppCompatActivity {
//    Button btSet, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        btSet = findViewById(R.id.bt_set);
//        btCancel = findViewById(R.id.bt_cancel);
//        final Intent intent = new Intent(SplashActivity.this, ScreenGuardService.class);
//        //开启服务
//        btSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startService(intent);
//            }
//        });
//        btCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    private boolean mIsBind = false;
    private boolean mIsConnected = false;
    private ScreenGuardService mMainService;
    private final int NOTIFICATION_ID = 98;
    private boolean mIsForegroundService = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMainService = (ScreenGuardService) service;
            if (mMainService != null) {
                SplashActivity.this.mIsConnected = true;
            } else {
                new Throwable("服务绑定失败");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            SplashActivity.this.mIsConnected = false;

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (null == startService(new Intent(SplashActivity.this,ScreenGuardService.class))) {
            new Throwable("无法启动服务");
        }
        mIsBind = bindService(new Intent(SplashActivity.this, ScreenGuardService.class), mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsConnected && mIsForegroundService) {
            mMainService.stopForeground(true);
            mIsForegroundService = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mIsBind && mMainService != null && !mIsForegroundService) {
            mMainService.startForeground(NOTIFICATION_ID, getNotification());
            mIsForegroundService = true;
        }
    }

    private Notification getNotification() {
        Notification.Builder mBuilder = new Notification.Builder(SplashActivity.this);
        mBuilder.setShowWhen(false);
        mBuilder.setAutoCancel(false);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
//        mBuilder.setLargeIcon(((BitmapDrawable) getDrawable(R.drawable.notification_drawable)).getBitmap());
        mBuilder.setContentText("thisiscontent");
        mBuilder.setContentTitle("this is title");
        return mBuilder.build();
    }
}
