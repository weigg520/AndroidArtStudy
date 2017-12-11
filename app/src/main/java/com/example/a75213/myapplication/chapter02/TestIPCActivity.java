package com.example.a75213.myapplication.chapter02;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.a75213.myapplication.R;
import com.example.a75213.myapplication.chapter02.ipc.UserManager;

/**
 * Created by 75213 on 2017/12/9.
 */

public class TestIPCActivity extends AppCompatActivity {
    private android.widget.TextView teststatic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ipc);
        //获取序列化的值
        UserManager userManager = UserManager.getUser();
        this.teststatic = (TextView) findViewById(R.id.test_static);
        teststatic.setText("" + userManager.sUserId);
    }
}
