package com.jayce.week7homeworktea01.AsycnTask;

import android.os.AsyncTask;

import com.jayce.week7homeworktea01.utils.HttpUtils;

/**
 * Created by 会函 on 2016/11/14.
 */
public class HeaderAsycnTask extends AsyncTask<String,Void,byte[]>{

    private headerCallBack headerCallBack;

    public HeaderAsycnTask(HeaderAsycnTask.headerCallBack headerCallBack) {
        this.headerCallBack = headerCallBack;
    }

    @Override
    protected byte[] doInBackground(String... params) {

        String headerPath = params[0];

        byte[] ret = null;

        ret = HttpUtils.getByteFromUrl(headerPath);

        return ret;
    }

    @Override
    protected void onPostExecute(byte[] bytes) {
        super.onPostExecute(bytes);
        headerCallBack.headercallback(bytes);
    }

    public interface headerCallBack{
        void headercallback(byte[] bytes);
    }
}
