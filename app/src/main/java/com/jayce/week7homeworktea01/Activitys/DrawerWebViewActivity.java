package com.jayce.week7homeworktea01.Activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.utils.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawerWebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    private TextView title_textView,showTime,text_From;

    private String path;

    private String title,create_time,source,id,description,nickname,wap_thumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_web_view);


        Bundle bundle = getIntent().getExtras();

        path = bundle.getString("path");

        title = bundle.getString("title");

        create_time = bundle.getString("creat_time");

        source = bundle.getString("source");

        id = bundle.getString("id");
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
}
