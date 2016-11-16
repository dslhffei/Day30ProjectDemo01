package com.jayce.week7homeworktea01.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.adapters.MyPagerAdapter2;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private Button mGo;

    //ViewPager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);



        //隐藏状态栏，显示时间，电池电量，网络状态，通知……
        //状态栏，不属于某一个特定应用，手机的Window
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();

        initViewPager();

    }

    private void initView() {
        mGo = (Button) findViewById(R.id.go);
        mGo.setOnClickListener(this);
    }

    private void initViewPager() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);

        //设置数据源
        List<ImageView> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(this);
            switch (i){
                case 0:
                    imageView.setImageResource(R.mipmap.slide1);
                    break;
                case 1:
                    imageView.setImageResource(R.mipmap.slide2);
                    break;
                case 2:
                    imageView.setImageResource(R.mipmap.slide3);
                    break;
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            data.add(imageView);
        }
        PagerAdapter adapter = new MyPagerAdapter2(data);

        viewPager.setAdapter(adapter);

        //设置滑动监听
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //当position==2Button显示
        if(position == 2){
            mGo.setVisibility(View.VISIBLE);
        }else {
            mGo.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //立即体验点击事件
    @Override
    public void onClick(View v) {
        //存储记录，用户已经第一次使用了
        SharedPreferences sp = getSharedPreferences("appConfig",MODE_PRIVATE);

        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean("isFirst",false);//用户不是第一次使用

        editor.commit();

        this.startActivity(new Intent(this,MainActivity.class));
        this.finish();
    }
}
