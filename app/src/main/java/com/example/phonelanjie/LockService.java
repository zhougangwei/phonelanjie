package com.example.phonelanjie;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import java.util.TimerTask;


/**
 * Created by xyy on 2019/6/15.
 */
public class LockService extends Service {

    private final static int GRAY_SERVICE_ID = 3;

    private static final String TAG = "LockService";

    private Context mContext;


    @Override
    public void onCreate() {
        mContext = this.getApplicationContext();
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(GRAY_SERVICE_ID, getNotification());
        }

        BroadcastReceiver receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() == Intent.ACTION_SCREEN_ON) {
                    if ( !phoneIsInUse()) {
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    Log.e(TAG, "run: "+"LockService111" );
                                    Thread.sleep(1000);
                                    Log.e(TAG, "run: "+"LockService222" );
                                 Intent lockScreen = new Intent(LockService.this, LockScreenActivity.class);
                                 lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(lockScreen);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.run();
                    }
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
    }







    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * 服务开始时，即调用startService()时，onStartCommand()被执行。
     * onStartCommand() 这里的主要作用：
     * (01) 将 appWidgetIds 添加到队列sAppWidgetIds中
     * (02) 启动线程
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startForeground(GRAY_SERVICE_ID, getNotification());

//        }
        return START_STICKY_COMPATIBILITY;
    }


    /**
     * 是否正在电话通话中
     */
    private boolean phoneIsInUse() {
        TelephonyManager mTelephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        int state = mTelephonyManager.getCallState();
        return state != TelephonyManager.CALL_STATE_IDLE;
    }

    private Notification getNotification() {
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //新增---------------------------------------------
            String CHANNEL_ONE_ID = "com.datecountdown.lock.service.cn";
            String CHANNEL_ONE_NAME = "锁屏服务";
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel(CHANNEL_ONE_ID, CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(false);
            notificationChannel.enableVibration(false);
            notificationChannel.setImportance(NotificationManager.IMPORTANCE_MIN);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(notificationChannel);
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            notification = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID).setTicker("Nature")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentIntent(pendingIntent).getNotification();
            notification.flags |= Notification.FLAG_NO_CLEAR;
        } else {
            notification = new Notification();
        }
        return notification;
    }


}
