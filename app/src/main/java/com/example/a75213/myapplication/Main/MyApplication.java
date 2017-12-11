package com.example.a75213.myapplication.Main;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.util.Log;

/**
 * Created by 75213 on 2017/12/11.
 */

public class MyApplication extends Application {
    private static final String TAG = MyApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        String processName = getApplicationName(getApplicationContext() , Process.myPid());
        Log.e(TAG , "application start , process name:" + processName +" PID:" +Process.myPid());
    }

    /**
     * 获取进程名
     * @param context
     * @param pid
     * @return
     */
    private String getApplicationName(Context context , int pid){
        String processName = null;
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()){
            if (processInfo.pid == pid){
                processName = processInfo.processName;
            }
        }
        return processName;
    }
}
