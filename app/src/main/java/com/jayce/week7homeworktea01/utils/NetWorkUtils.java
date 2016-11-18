package com.jayce.week7homeworktea01.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.jayce.week7homeworktea01.fragments.FirstTileFragment;

/**
 * Created by 会函 on 2016/11/17.
 */

public class NetWorkUtils {

    public static boolean isConnected(Context context){

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo =
                connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            return false;
        }

        switch (networkInfo.getType()){
            case ConnectivityManager.TYPE_WIFI:
                Toast.makeText(context,"当前网络是WIFI",Toast.LENGTH_SHORT).show();
                return true;
            case ConnectivityManager.TYPE_MOBILE:
                Toast.makeText(context,"当前网络是移动数据",Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

}
