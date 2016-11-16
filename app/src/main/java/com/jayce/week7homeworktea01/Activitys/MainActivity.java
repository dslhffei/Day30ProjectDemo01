package com.jayce.week7homeworktea01.Activitys;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.adapters.MyViewPagerAdapter;
import com.jayce.week7homeworktea01.fragments.BusinnessFragment;
import com.jayce.week7homeworktea01.fragments.DataFragment;
import com.jayce.week7homeworktea01.fragments.FirstTileFragment;
import com.jayce.week7homeworktea01.fragments.KnowledgeFragment;
import com.jayce.week7homeworktea01.fragments.MessageFragment;
import com.softpo.viewpagertransformer.AccordionTransformer;
import com.softpo.viewpagertransformer.BackgroundToForegroundTransformer;
import com.softpo.viewpagertransformer.CubeOutTransformer;
import com.softpo.viewpagertransformer.DefaultTransformer;
import com.softpo.viewpagertransformer.DepthPageTransformer;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    private TabLayout mTabLayout;

    private FragmentPagerAdapter adapter;

    private List<Fragment> fragments;

    private String[] titles = {"头条","百科","资讯","经营","数据"};

    private DrawerLayout drawerLayout;

    private ImageView Id_more;

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);

        initView();

        initData();

        initViewPager();
    }

    private void initData() {
        fragments = new ArrayList<>();

        fragments.add(FirstTileFragment.newInstance());
        fragments.add(KnowledgeFragment.newInstance());
        fragments.add(MessageFragment.newInstance());
        fragments.add(BusinnessFragment.newInstance());
        fragments.add(DataFragment.newInstance());
    }



    private void initViewPager() {

        adapter = new MyViewPagerAdapter(getSupportFragmentManager(),fragments,titles);

        mViewPager.setAdapter(adapter);

        mTabLayout.setupWithViewPager(mViewPager);

        mViewPager.setPageTransformer(true,new CubeOutTransformer());
//
//        mViewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
//            @Override
//            public void transformPage(View view, float position) {
//                int pageWidth = view.getWidth();
//                int pageHeight = view.getHeight();
//
//                if (position < -1) { // [-Infinity,-1)
//                    // This page is way off-screen to the left.
//                    view.setAlpha(0);
//
//                } else if (position <= 1) { // [-1,1]
//                    // Modify the default slide transition to shrink the page as well
//                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
//                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
//                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
//                    if (position < 0) {
//                        view.setTranslationX(horzMargin - vertMargin / 2);
//                    } else {
//                        view.setTranslationX(-horzMargin + vertMargin / 2);
//                    }
//
//                    // Scale the page down (between MIN_SCALE and 1)
//                    view.setScaleX(scaleFactor);
//                    view.setScaleY(scaleFactor);
//
//                    // Fade the page relative to its size.
//                    view.setAlpha(MIN_ALPHA +
//                            (scaleFactor - MIN_SCALE) /
//                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));
//
//                } else { // (1,+Infinity]
//                    // This page is way off-screen to the right.
//                    view.setAlpha(0);
//                }
//            }
//        });
    }


    private void initView() {

        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout);

        Id_more = (ImageView) findViewById(R.id.Id_more);

        Id_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()){
                case R.id.activity_main_drawer_imageViewBackId:
                    drawerLayout.closeDrawer(GravityCompat.END);
                    break;

                case R.id.activity_main_drawer_imageViewBtn://搜索
                    Toast.makeText(this,"正在搜索···",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.activity_main_drawer_myCollect://收藏
//                    Toast.makeText(this,"收藏",Toast.LENGTH_SHORT).show();
                    Intent collection = new Intent(this,CollectionActivity.class);
                    startActivity(collection);
                    break;

                case R.id.activity_main_drawer_history://历史记录
//                    Toast.makeText(this,"历史记录",Toast.LENGTH_SHORT).show();
                    Intent history = new Intent(this,HistoryActivity.class);
                    startActivity(history);
                    break;

                case R.id.activity_main_drawer_copyrightMessage://版本信息
                    Toast.makeText(this,"版本信息",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.activity_main_drawer_suggest://意见反馈
                    Toast.makeText(this,"意见反馈",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.activity_main_drawer_login://用户登入
                    Toast.makeText(this,"用户登入",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.activity_main_drawer_exit:
                    Toast.makeText(this,"退出登入",Toast.LENGTH_SHORT).show();
                    break;
        }
    }
}
