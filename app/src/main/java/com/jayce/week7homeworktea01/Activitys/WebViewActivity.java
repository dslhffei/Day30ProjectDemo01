package com.jayce.week7homeworktea01.Activitys;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.utils.HttpUtils;
import com.jayce.week7homeworktea01.utils.TeaDatabaseCollectHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private TeaDatabaseCollectHelper dbHelper;

    private WebView mWebView;

    private TextView title_textView,showTime,text_From;

    private String path;

    private String title,create_time,source,id,description,nickname,wap_thumb;
//    private SQLiteCursorDriver db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        dbHelper = new TeaDatabaseCollectHelper(this);

        Bundle bundle = getIntent().getExtras();

        path = bundle.getString("path");

        title = bundle.getString("title");

        create_time = bundle.getString("creat_time");

        source = bundle.getString("source");

        id = bundle.getString("id");

        description = bundle.getString("description");

        nickname = bundle.getString("nickname");

        wap_thumb = bundle.getString("wap_thumb");
        //id,description,nickname,wap_thumb

        initView();

        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {


                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(new String(HttpUtils.parse(path)));
                    JSONObject data=jsonObject.optJSONObject("data");
                    String wap_content=data.optString("wap_content");
                    mWebView.loadDataWithBaseURL
                            (null,wap_content,"text/html","utf-8",null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        title_textView.setText(title);
        showTime.setText(create_time);
        text_From.setText(source);
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        title_textView = (TextView) findViewById(R.id.title_textView);
        showTime = (TextView) findViewById(R.id.showTime);
        text_From = (TextView) findViewById(R.id.text_From);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.webView_collect:
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.query(TeaDatabaseCollectHelper.TABLE_NAME,
                        new String[]{"id"}, null, null, null, null, null);
                int idIndex = cursor.getColumnIndex("id");
                List<String> list=new ArrayList<>();
                while (cursor.moveToNext()){
                    String string = cursor.getString(idIndex);
                    list.add(string);
                }
                if (list.contains(id)){
                    Toast.makeText(WebViewActivity.this, "已收藏", Toast.LENGTH_SHORT).show();
                }else{
                    //将数据存到数据库中
                    ContentValues values=new ContentValues();
                    values.put("id",id);
                    values.put("title",title);
                    values.put("description",description);
                    values.put("source",source);
                    values.put("nickname",nickname);
                    values.put("create_time",create_time);
                    values.put("wap_thumb",wap_thumb);
                    db.insert(TeaDatabaseCollectHelper.TABLE_NAME,null,values);
                    Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    db.close();
                }

//                Toast.makeText(this,"已收藏！",Toast.LENGTH_SHORT).show();
                break;

            case R.id.webView_back:
//                Intent intent = new Intent(this,MainActivity.class);
//                startActivity(intent);
                finish();
                break;

            case R.id.webView_share:
                Toast.makeText(this,"已分享",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
