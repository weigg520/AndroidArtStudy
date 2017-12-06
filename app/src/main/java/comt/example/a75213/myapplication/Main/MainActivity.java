package comt.example.a75213.myapplication.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import comt.example.a75213.myapplication.Main.Bean.MainBean;
import comt.example.a75213.myapplication.Main.adapter.MainAdapter;
import comt.example.a75213.myapplication.R;
import comt.example.a75213.myapplication.chapter01.activity_lift.OneLiftActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView main_listview;
    private MainAdapter mAdapter;
    private List<MainBean> mMainList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    private void init() {
        main_listview = (ListView) findViewById(R.id.activity_main_list_view);
        //加载按钮数据
        mMainList = new ArrayList<>();
        mMainList.add(getMainBean("android生命周期全面解析" , OneLiftActivity.class));
        //初始化适配器
        mAdapter = new MainAdapter(this , mMainList);
        main_listview.setAdapter(mAdapter);
        main_listview.setOnItemClickListener(this);
    }

    /**
     * 描述：返回Bean
     * @param name
     * @param cls
     * @return
     */
    private MainBean getMainBean(String name , Class<? extends Activity> cls){
        MainBean bean = new MainBean();
        bean.setActivityClass(cls);
        bean.setNameItem(name);
        return bean;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到相应Activity界面
        startActivity(new Intent(MainActivity.this , mMainList.get(position).getActivityClass()));
    }
}
