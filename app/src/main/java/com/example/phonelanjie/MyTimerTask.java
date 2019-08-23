package com.example.phonelanjie;

import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    @Override
    public void run() {
        Log.e("TAG","响铃:MyTimerTask");
        completeTask();
        Utils.wakeUpAndUnlock(App.sContext);
        Intent intent = new Intent(App.sContext, LockScreenActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        App.sContext.startActivity(intent);
        Log.e("TAG","响铃:MyTimerTask22");
    }
    private void completeTask() {
        try {
            //assuming it takes 20 secs to complete the task
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
