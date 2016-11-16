package com.jayce.week7homeworktea01.AsycnTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.jayce.week7homeworktea01.adapters.MyBaseAdapter;
import com.jayce.week7homeworktea01.utils.HttpUtils;

/**
 * Created by 会函 on 2016/11/12.
 */
public class BitmapAsycnTask extends AsyncTask<String,Void,Bitmap>{

    private MyBaseAdapter.bitmapCallBack bitmapcallback;

    public BitmapAsycnTask(MyBaseAdapter.bitmapCallBack bitmapcallback) {
        this.bitmapcallback = bitmapcallback;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        String path = params[0];

        Log.d("flag", "doInBackground:data " + path);

        byte[] bytes = HttpUtils.parse(path);


        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            bitmapcallback.callback(bitmap);
        }
    }
}
