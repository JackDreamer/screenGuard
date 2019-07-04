package com.damo.popularscreenguard.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.damo.popularscreenguard.activity.DateTimeActivity;
import com.damo.popularscreenguard.listener.ScreenListener;

public class ScreenGuardService extends Service implements ScreenListener.ScreenStateListener {

    private final static int FOREGROUND_ID = 1000;
    private ScreenListener screenListener;
    private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("vivi", "onCreate: ");
        screenListener = new ScreenListener(ScreenGuardService.this);
        screenListener.begin(this);

        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(
                PowerManager.ACQUIRE_CAUSES_WAKEUP |
                        PowerManager.SCREEN_DIM_WAKE_LOCK |
                        PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("vivi", "onStartCommand: ");
        mWakeLock.acquire();

        if (screenListener == null) {
            screenListener = new ScreenListener(ScreenGuardService.this);
            screenListener.begin(this);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    //亮屏的时候调用Activity
    @Override
    public void onScreenOn() {
        Log.d("vivi", "onScreenOn: ");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ScreenGuardService.this, DateTimeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }, 100);
    }

    //锁屏的时候不做处理
    @Override
    public void onScreenOff() {

    }

    //已经处于亮屏状态的时候不做出来
    @Override
    public void onUserPresent() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mWakeLock.acquire();
        startService(new Intent(this, ScreenGuardService.class));
    }
}
