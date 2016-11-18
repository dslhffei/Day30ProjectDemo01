package com.jayce.week7homeworktea01.utils;

import android.util.Log;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by 会函 on 2016/11/12.
 */
public class HttpUtils {
    public static byte[] getByteFromUrl(String path) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            coon.setConnectTimeout(5000);
            if (coon.getResponseCode() == 200) {

                is = coon.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = 0;
                byte[] buf = new byte[1024*8];
                while ((len = is.read(buf))!=-1){
                    baos.write(buf,0,len);
                }

                return baos.toByteArray();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static byte[] parse(String path) {
        InputStream is = null;
        ByteArrayOutputStream baos = null;

        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection coon = (HttpURLConnection) url.openConnection();
            coon.setConnectTimeout(5000);
            if (coon.getResponseCode() == 200) {

                is = coon.getInputStream();
                baos = new ByteArrayOutputStream();
                int len = 0;
                byte[] buf = new byte[1024*8];
                while ((len = is.read(buf))!=-1){
                    baos.write(buf,0,len);
                }

                return baos.toByteArray();

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
