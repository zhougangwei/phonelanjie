package com.example.phonelanjie;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.app.Service;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Method;

public class PhoneStatReceiver extends BroadcastReceiver {
    TelephonyManager telMgr;
    private Context contextT;
    String TAG = "tag";
    private TextView       tv;
    private LayoutInflater inflate;
    private View           phoneView;
    private WindowManager wm;
    @Override
    public void onReceive(Context context, Intent intent) {
        telMgr = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        contextT = context;
        switch (telMgr.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:
                String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.v(TAG, "number:" + number);
                // new MyTimerTask().run();
                  /*inflate = LayoutInflater.from(contextT);
              wm = (WindowManager) contextT.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.gravity = Gravity.CENTER;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    params.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                params.height = 600;
                params.format = PixelFormat.RGBA_8888;
                phoneView = inflate.inflate(R.layout.phone_alert, null);
                wm.addView(phoneView, params);
                phoneView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (phoneView!=null) {
                            wm.removeView(phoneView);
                        }
                    }
                });*/
                new MyTimerTask().run();
                 /*   Intent lockScreen = new Intent(contextT, LockScreenActivity.class);

            lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                contextT.startActivity(lockScreen);*/
                Log.e("TAG","响铃:来电号码");
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            case TelephonyManager.CALL_STATE_IDLE:

                break;
        }

    }


}

