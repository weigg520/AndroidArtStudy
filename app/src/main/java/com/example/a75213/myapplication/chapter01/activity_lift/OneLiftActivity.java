package com.example.a75213.myapplication.chapter01.activity_lift;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a75213.myapplication.R;

/**
 * Activity生命周期
 * onCreate >> onStart >> onResume >> onPause >> onStop >>onDestroy
 * 1、onPause >> onResume
 * 2、onPause >> onStop >> onRestart >> onStart >>onResume
 * 3、onPause >> onStop >> onDestroy
 * 在Activity现在透明主体时不会调用onStop[完全不可见]
 */
public class OneLiftActivity extends AppCompatActivity {
    private static final String TAG = OneLiftActivity.class.getSimpleName();
    private android.widget.TextView oneactivitytextview;
    private android.widget.EditText testonsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_lift);
        this.testonsave = (EditText) findViewById(R.id.test_on_save);
        this.oneactivitytextview = (TextView) findViewById(R.id.one_activity_text_view);
        oneactivitytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneLiftActivity.this, TwoLiftActivity.class));
            }
        });
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

    /***********************************************************************************************************************
     * 只有在Activity异常终止时调用
     * 在屏幕发生横竖屏切换时
     * 可以设置configChange="orientation" 来防止Activity重新创建
     * 当minSdkVersion 或者targetSdkVersion 有一个 Api > 13 时，需要设置screenSize才能生效
     */



    /**
     * 数据保存
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("extra_test", testonsave.getText().toString() + "数据保存恢复");
    }

    /**
     * 数据恢复
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        testonsave.setText(savedInstanceState.getString("extra_test"));
    }

    /**
     *  当设置configChange="orientation"时 onSaveInstanceState、onRestoreInstanceState不会被调用，此方法会被调用
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG , "onConfigurationChanged");
    }

    //***********************************************************************************************************************
    /**
     * 内存不足杀死Activity优先级
     * 1、前台Activity ------正在和用户交互的优先级最高
     * 2、可见但是非前台Activity -------如Activity弹出对话框、导致Activity无法与用户直接进行交互
     * 3、后台Activity ------已经被暂停【onStop】的Activity，优先级最低
     */
}
