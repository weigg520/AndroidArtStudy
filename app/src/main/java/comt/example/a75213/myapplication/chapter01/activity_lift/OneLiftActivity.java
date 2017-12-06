package comt.example.a75213.myapplication.chapter01.activity_lift;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import comt.example.a75213.myapplication.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_lift);
        this.oneactivitytextview = (TextView) findViewById(R.id.one_activity_text_view);
        oneactivitytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneLiftActivity.this , TwoLiftActivity.class));
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
        for (int i = 0 ; i < 100000 ; i++){
            Log.e(TAG , " " + i);
        }
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
