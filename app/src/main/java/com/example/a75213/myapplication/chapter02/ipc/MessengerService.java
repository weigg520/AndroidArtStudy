package com.example.a75213.myapplication.chapter02.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.a75213.myapplication.constant.Constant;

/**
 * Created by 75213 on 2017/12/13.
 */

public class MessengerService extends Service {
    private static final String TAG = MessengerService.class.getSimpleName();


    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.MSG_FROM_CLIENT:
                    Log.e(TAG , "receive msg from Client:" + msg.getData());
                    Log.e(TAG , msg.getData().getString("msg"));
                    //Log.e(TAG ,((Book)(msg.getData().getParcelable("Book"))).bookName);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
