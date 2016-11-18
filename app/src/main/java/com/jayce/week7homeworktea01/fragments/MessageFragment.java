package com.jayce.week7homeworktea01.fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jayce.week7homeworktea01.Activitys.WebViewActivity;
import com.jayce.week7homeworktea01.AsycnTask.MyAsycnTask;
import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.Urls.myUrls;
import com.jayce.week7homeworktea01.adapters.MyBaseAdapter;
import com.jayce.week7homeworktea01.beans.Data;
import com.jayce.week7homeworktea01.utils.NetWorkUtils;
import com.jayce.week7homeworktea01.utils.SdCardUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private ListView mListView1;

    private TextView empty1;

    private BaseAdapter mBaseAdapter;

    private List<Data.DataBean> mData = new ArrayList<>();

    private String MpathTitle = myUrls.BASE_URL+myUrls.CONSULT_TYPE;

    private int index = 1;

    //下拉刷新
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 200:
                    byte[] bytes = (byte[]) msg.obj;

                    Data data = JSON.parseObject(new String(bytes), Data.class);

                    List<Data.DataBean> cooks1 = data.getData();

                    String root = getContext().getExternalCacheDir().getAbsolutePath();

                    String fileName = "Message";

                    SdCardUtils.saveFile(bytes,root,fileName);

                    mData.addAll(0,cooks1);

                    mBaseAdapter.notifyDataSetChanged();

                    if (mSwipeRefreshLayout.isRefreshing()){
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    break;
            }
        }
    };

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View ret = inflater.inflate(R.layout.fragment_message, container, false);

        initView(ret);

        initListView();

        initData();

        initSwipeRefresh();

        return ret;
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


    private void initData() {
//        new MyAsycnTask(new MyAsycnTask.AsycnTaskCallBack() {
//            @Override
//            public void AsycnTaskCallBack(byte[] bytes) {
////                Data data = JSON.parseObject(new String(bytes), Data.class);
//                mHandler.sendMessage(Message.obtain(mHandler,200,bytes));
////                mData.addAll(data.getData());
//            }
//        }).execute(MpathTitle+index);

        if (NetWorkUtils.isConnected(getContext())) {
            new MyAsycnTask(new MyAsycnTask.AsycnTaskCallBack() {
                @Override
                public void AsycnTaskCallBack(byte[] bytes) {
//                    Data data = JSON.parseObject(new String(bytes), Data.class);
                    mHandler.sendMessage(Message.obtain(mHandler,200,bytes));
//                    mData.addAll(data.getData());
                }
            }).execute(MpathTitle+index);
        }else {
            //TODO 从磁盘获取网络数据
            String root = getContext().getExternalCacheDir().getAbsolutePath();

            String fileName = root+ File.separator+"Message";

            byte[] bytes = SdCardUtils.getByteFromFile(fileName);

            if (bytes != null) {
                Data data = JSON.parseObject(new String(bytes),Data.class);

                mData.addAll(data.getData());

                mBaseAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initListView() {
        mBaseAdapter = new MyBaseAdapter(getContext(),mData);

        mListView1.setAdapter(mBaseAdapter);

        mListView1.setEmptyView(empty1);
    }

    private void initView(View ret) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) ret.findViewById(R.id.refresh_message);

        mListView1 = (ListView) ret.findViewById(R.id.listView1);

        empty1 = (TextView) ret.findViewById(R.id.empty1);

        mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"第"+position+"条目",Toast.LENGTH_SHORT).show();
                String path = myUrls.CONTENT_URL+mData.get(position).getId();
                String title = mData.get(position).getTitle();
                String creat_time = "时间："+mData.get(position).getCreate_time();
                String source = "来源："+mData.get(position).getSource();
                String _id = mData.get(position).getId();
                String description = mData.get(position).getDescription();
                String nickname = mData.get(position).getNickname();
                String wap_thumb = mData.get(position).getWap_thumb();
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("path",path);
                bundle.putString("title",title);
                bundle.putString("creat_time",creat_time);
                bundle.putString("source",source);
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
        MessageFragment messageFragment = new MessageFragment();
        return messageFragment;
    }
}
