package com.example.a75213.myapplication.chapter02;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.a75213.myapplication.Book;
import com.example.a75213.myapplication.R;
import com.example.a75213.myapplication.chapter02.ipc.MessengerService;
import com.example.a75213.myapplication.chapter02.ipc.UserManager;
import com.example.a75213.myapplication.constant.Constant;

/**
 * Created by 75213 on 2017/12/9.
 */

public class TestIPCActivity extends AppCompatActivity {
    private android.widget.TextView teststatic;
    private Messenger mService;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ipc);
        //获取序列化的值
        UserManager userManager = UserManager.getUser();
        this.teststatic = (TextView) findViewById(R.id.test_static);
        teststatic.setText("" + userManager.sUserId);
        Intent intent = new Intent(this , MessengerService.class);
        bindService(intent , mConnection , Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    public void testIPC(View view){
        Book book = new Book(10 , "第一行代码");
        Message msg = Message.obtain(null , Constant.MSG_FROM_CLIENT);
        Bundle data = new Bundle();
        data.putString("msg" , "hello , this is client");
        //data.putParcelable("Book" , book);
        msg.setData(data);
        try {
            mService.send(msg);
        }catch (RemoteException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
