package com.jayce.week7homeworktea01.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jayce.week7homeworktea01.Activitys.WebViewActivity;
import com.jayce.week7homeworktea01.AsycnTask.BitmapAsycnTask;
import com.jayce.week7homeworktea01.AsycnTask.HeaderAsycnTask;
import com.jayce.week7homeworktea01.AsycnTask.MyAsycnTask;
import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.Urls.myUrls;
import com.jayce.week7homeworktea01.adapters.MyBaseAdapter;
import com.jayce.week7homeworktea01.adapters.MyPagerAdapter;
import com.jayce.week7homeworktea01.adapters.MyViewPagerAdapter;
import com.jayce.week7homeworktea01.beans.Data;
import com.jayce.week7homeworktea01.beans.TitleImage;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstTileFragment extends Fragment {

    private ListView mListView1;

    private TextView empty1;

    private BaseAdapter mBaseAdapter;

    private List<Data.DataBean> mData = new ArrayList<>();

    //头布局的图片
//    private ViewPager mViewPager;

    private View mView;

    private  List<TitleImage.DataBean> mTitleImages = new ArrayList<>();

    private PagerAdapter mAdapter;

    private List<ImageView> headerImags = new ArrayList<>();

    //图片下载地址
    private String[] imagPath;
    //头布局文本信息
    private String[] header_titles;
    //存放Indicator的数组
    private View[] indicators;
    //记录上次选择的网页信息
    private int lastSelected = 0;

    //头布局数据下载地址
    private String HeaderImg = myUrls.HEADERIMAGE_URL;
    //头布局End

    private String MpathTitle = myUrls.HEADLINE_URL+myUrls.HEADLINE_TYPE;

    private int index = 1;

    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public FirstTileFragment() {
        // Required empty public constructor
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 198:
                    List<Data.DataBean> cooks1 = (List<Data.DataBean>) msg.obj;

                    mData.addAll(0,cooks1);

                    mBaseAdapter.notifyDataSetChanged();

                    if (mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_first_tile, container, false);

        initView(ret);

        initListView();

        initData();

        initSwipeRefresh();

        return ret;
    }


    private void initListView() {

        mView = LayoutInflater.from(getContext()).inflate(R.layout.header_item,mListView1,false);

//        initViewPager();

        mListView1.addHeaderView(mView);

        mBaseAdapter = new MyBaseAdapter(getContext(),mData);

        mListView1.setAdapter(mBaseAdapter);

        mListView1.setEmptyView(empty1);
    }

    private void initViewPager() {

//        initViewPagerData();

//        initViewPagerAdapter();

    }

    private void initViewPagerAdapter() {
        mAdapter = new MyPagerAdapter(headerImags);

//        mViewPager.setAdapter(mAdapter);
    }

    private void initViewPagerData() {
        new HeaderAsycnTask(new HeaderAsycnTask.headerCallBack() {
            @Override
            public void headercallback(byte[] bytes) {
                TitleImage titleImage = JSON.parseObject(new String(bytes),TitleImage.class);
//                mTitleImages.addAll(titleImage.getData());
                for (int i = 0; i < 3; i++) {
                    String imagePath = titleImage.getData().get(i).getImage_s();
//                    Log.d("flag", "headercallback: "+imagePath);
                    new BitmapAsycnTask(new MyBaseAdapter.bitmapCallBack() {
                        @Override
                        public void callback(Bitmap bitmap) {
                            ImageView add = new ImageView(getContext());
                            add.setImageBitmap(bitmap);
                            headerImags.add(add);
                            Log.d("flag", "------->callback: "+headerImags);
                        }
                    }).execute(imagePath);
                }
            }
        }).execute(HeaderImg);


    }

    //下拉属性
    private void initSwipeRefresh() {

        //1、设置背景颜色
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.YELLOW);

        //2、正在刷新过程中，旋转的颜色变换
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLUE);

        //3、设置刷新的监听
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                index++;
                initData();
            }
        });
        //4、用户向下拉动多少，才可以触发刷新
        mSwipeRefreshLayout.setDistanceToTriggerSync(200);

        //5、刷新的布局，刷新时，位置
        mSwipeRefreshLayout.setProgressViewEndTarget(true,400);


    }


    //加载数据
    private void initData() {

//        Log.d("flag", "initData: -----"+MpathTitle+"1");

        new MyAsycnTask(new MyAsycnTask.AsycnTaskCallBack() {
                @Override
                public void AsycnTaskCallBack(byte[] bytes) {
                    Data data = JSON.parseObject(new String(bytes), Data.class);
                    mHandler.sendMessage(Message.obtain(mHandler,198,data.getData()));
                    mData.addAll(data.getData());
                }
        }).execute(MpathTitle+index);
    }

    private void initView(View ret) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.refresh_first_title);

        mListView1 = (ListView) ret.findViewById(R.id.listView1);

//        mViewPager = (ViewPager) ret.findViewById(R.id.viewPager_title);

        empty1 = (TextView) ret.findViewById(R.id.empty1);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"第"+position+"条目",Toast.LENGTH_SHORT).show();
                String path = myUrls.CONTENT_URL+mData.get(position-1).getId();
                String title = mData.get(position-1).getTitle();
                String creat_time = "时间："+mData.get(position-1).getCreate_time();
                String source = "来源："+mData.get(position-1).getSource();
                String _id = mData.get(position-1).getId();
                String description = mData.get(position-1).getDescription();
                String nickname = mData.get(position-1).getNickname();
                String wap_thumb = mData.get(position-1).getWap_thumb();
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("path",path);
                bundle.putString("title",title);
                bundle.putString("creat_time",creat_time);
                bundle.putString("source",source);
                //id,description,nickname,wap_thumb
                bundle.putString("id",_id);
                bundle.putString("description",description);
                bundle.putString("nickname",nickname);
                bundle.putString("wap_thumb",wap_thumb);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mListView1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
//                Toast.makeText(getContext(),"第"+position+"长按条目",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mData.remove(position-1);
                        mBaseAdapter.notifyDataSetChanged();
                        Toast.makeText(getContext(),"已删除",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("取消",null);

                builder.setMessage("亲，确定删除吗？");

                builder.setIcon(R.mipmap.icon_dialog);

                builder.setTitle("提示");

                builder.create().show();

                return false;
            }
        });
    }

    public static Fragment newInstance() {
        FirstTileFragment fistTitle = new FirstTileFragment();
        return fistTitle;
    }
}
