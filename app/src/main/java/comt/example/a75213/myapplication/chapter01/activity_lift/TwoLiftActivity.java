package comt.example.a75213.myapplication.chapter01.activity_lift;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import comt.example.a75213.myapplication.R;

/**
 * Activity生命周期
 * onCreate >> onStart >> onResume >> onPause >> onStop >>onDestroy
 * 1、onPause >> onResume
 * 2、onPause >> onStop >> onRestart >> onStart >>onResume
 * 3、onPause >> onStop >> onDestroy
 * 在Activity现在透明主体时不会调用onStop[完全不可见]
 */
public class TwoLiftActivity extends AppCompatActivity {
    private static final String TAG  = TwoLiftActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_lift);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG , "onStart 2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG , "onPause 2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG , "onResume 2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG , "onStop 2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG , "onDestroy 2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG , "onRestart 2");
    }
}
