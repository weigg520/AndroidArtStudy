package com.example.a75213.myapplication.chapter02.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.a75213.myapplication.R;
import com.example.a75213.myapplication.chapter02.TestIPCActivity;

/**
 * 描述：IPCActivity是独立进程
 */
public class IPCActivity extends AppCompatActivity {
   private static final String TAG =  IPCActivity.class.getSimpleName();

    private android.widget.Button testipbnt;
    private android.widget.TextView testipctext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        UserManager userManager = new UserManager();
        userManager.sUserId = 6;
        //序列化
        UserManager.setUser(userManager);
        Log.e(TAG , "进来了");
        this.testipctext = (TextView) findViewById(R.id.test_ipc_text);
        this.testipbnt = (Button) findViewById(R.id.test_ip_bnt);
        testipctext.setText("" + userManager.sUserId);
        testipbnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IPCActivity.this, TestIPCActivity.class));
            }
        });
    }
}
