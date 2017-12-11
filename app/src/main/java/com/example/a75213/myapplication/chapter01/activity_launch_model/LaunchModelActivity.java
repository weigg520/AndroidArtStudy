package com.example.a75213.myapplication.chapter01.activity_launch_model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.a75213.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述:Android 分四种启动模式
 * 概念：任务栈【后进先出】
 * 种类:
 * 1、standard[标准]
 * 默认模式、都会重新产生一个实例
 * PS、注意！standard默认会进入到启动它的Activity所属的任务栈中
 * 2、singleTop[栈顶复用]
 * Activity是否在栈顶【显示】,是则不重新创建
 * 3、singTask[栈内复用]
 * 在此任务栈中已有该实例，就不会重新创建
 * 4、singleInstance[单实例模式]
 * 系统会单独为此实例创建一个任务栈，除非系统销毁，否则不会重建该实例.
 */
public class LaunchModelActivity extends AppCompatActivity {
    private static final String TAG = LaunchModelActivity.class.getSimpleName();

    private TextView textContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_model);
        this.textContent = (TextView) findViewById(R.id.textContent);
        textContent.setText("1、standard[标准] \n 2、singleTop[栈顶复用] \n 3、singleTask[栈内复用] \n4、单实例模式");
        textContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LaunchModelActivity.this,LaunchModelActivity.class);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
                Date date = new Date(System.currentTimeMillis());
                intent.putExtra("time" ,formatter.format(date) );
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG , "onNewIntent time = " + intent.getStringExtra("time"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在进入第二个界面时候，必须将此方法执行完毕
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }
}
