package com.jayce.week7homeworktea01.Activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.Urls.myUrls;
import com.jayce.week7homeworktea01.adapters.MyCollectionAdapter;
import com.jayce.week7homeworktea01.utils.TeaDatabaseCollectHelper;

public class CollectionActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mListView;

    //从数据库获取数据，查询---->数据源
    private Cursor cursor;

    //适配器，ArrayAdapter,SimpleAdapter,BaseAdapter

    private CursorAdapter mAdapter;

    private TeaDatabaseCollectHelper helper;
    private SQLiteDatabase db;
    private String _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        initView();

        helper = new TeaDatabaseCollectHelper(this);

        db = helper.getReadableDatabase();

        cursor=db.query(TeaDatabaseCollectHelper.TABLE_NAME,null,null,null,null,null,null);

        mAdapter = new MyCollectionAdapter(this,cursor);

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        mListView.setOnItemLongClickListener(this);

    }


    private void initView() {
        mListView = (ListView) findViewById(R.id.collection_list);
    }

    public void onClick(View view) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cursor.moveToPosition(position);
        _id = cursor.getString(cursor.getColumnIndex("id"));
        String title= cursor.getString(cursor.getColumnIndex("title"));
        String source = cursor.getString(cursor.getColumnIndex("source"));
        String create_time = cursor.getString(cursor.getColumnIndex("create_time"));
        String path = myUrls.CONTENT_URL+ _id;

        Intent intent=new Intent(CollectionActivity.this, DrawerWebViewActivity.class);
        intent.putExtra("path",path);
        intent.putExtra("id", _id);
        intent.putExtra("source",source);
        intent.putExtra("creat_time",create_time);
        intent.putExtra("title",title);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, final View view, int position, long id_) {
        cursor.moveToPosition(position);
        final String id = cursor.getString(cursor.getColumnIndex("id"));
        // 对话框构建者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 设置对话框标题
        builder.setTitle("提示");
        // 设置提示信息
        builder.setMessage("确定要删除吗？");
        // 设置图标
        builder.setIcon(R.mipmap.ic_logo);

        builder.setNegativeButton("取消",null);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(id);
            }
        });
        // 创建真正的对话框对象
        AlertDialog dialog = builder.create();
        // 展示对话框
        dialog.show();

        mAdapter.notifyDataSetChanged();

        return false;
    }

    private void deleteData(String id) {
        int count = db.delete(TeaDatabaseCollectHelper.TABLE_NAME, "id = ?", new String[]{id});
        if (count > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}
