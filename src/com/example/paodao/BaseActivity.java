package com.example.paodao;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;

import java.util.List;

/**
 * Created by jingdongqi on 6/30/15.
 */
public class BaseActivity extends Activity {

    protected boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        ((MyApplication)getApplication()).addPaoDaoView();


    }


    @Override
    protected void onResume() {
        super.onResume();


        if (!isActive) {

            //app 从后台唤醒，进入前台

            isActive = true;

            ((MyApplication)getApplication()).addPaoDaoView();


        }


    }


    @Override
    protected void onStop() {
        super.onStop();

        if (!isAppOnForeground()) {
            //app 进入后台

//全局变量isActive = false 记录当前已经进入后台
            isActive = false;

            ((MyApplication) getApplication()).removePaoDaoView();

        }


    }

    @Override
    protected void onPause() {
        super.onPause();
//        ((MyApplication) getApplication()).removePaoDaoView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
