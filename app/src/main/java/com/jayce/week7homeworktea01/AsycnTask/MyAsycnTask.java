package com.jayce.week7homeworktea01.AsycnTask;

import android.os.AsyncTask;
import android.util.Log;

import com.jayce.week7homeworktea01.utils.HttpUtils;

/**
 * Created by 会函 on 2016/11/12.
 */
public class MyAsycnTask extends AsyncTask<String,Void,byte[]>{

    private AsycnTaskCallBack asycnTaskCallBack;

    public MyAsycnTask(AsycnTaskCallBack asycnTaskCallBack) {
        this.asycnTaskCallBack = asycnTaskCallBack;
    }

    @Override
    protected byte[] doInBackground(String... params) {
        String path = params[0];

        byte[] ret = null;

        ret = HttpUtils.parse(path);



        return ret;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
//        Log.d("flag", "onPostExecute: "+new String(bytes));
        asycnTaskCallBack.AsycnTaskCallBack(bytes);
    }

    public interface AsycnTaskCallBack {
        void AsycnTaskCallBack(byte[] bytes);
    }
}
