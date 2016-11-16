package com.jayce.week7homeworktea01.Activitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.jayce.week7homeworktea01.R;

public class CollectionActivity extends AppCompatActivity {

    private ListView mListView;

    //数据库对象
    private SQLiteDatabase db;

    //从数据库获取数据，查询---->数据源
    private Cursor cursor;


    //适配器，ArrayAdapter,SimpleAdapter,BaseAdapter
    //SimpleCursorAdapter
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();

        initData();
    }

    private void initData() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

        }
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.collection_list);
    }

    public void onClick(View view) {
        finish();
    }
}
