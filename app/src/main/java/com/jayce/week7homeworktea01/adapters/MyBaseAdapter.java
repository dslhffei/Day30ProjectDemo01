package com.jayce.week7homeworktea01.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayce.week7homeworktea01.AsycnTask.BitmapAsycnTask;
import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.beans.Data;
import com.jayce.week7homeworktea01.cache.MyLruCache;
import com.jayce.week7homeworktea01.utils.SdCardUtils;

import java.io.File;
import java.util.List;

/**
 * Created by 会函 on 2016/11/12.
 */
public class MyBaseAdapter extends BaseAdapter {

    private Context context;

    private List<Data.DataBean> data;

    private MyLruCache myLruCache;

    public MyBaseAdapter(Context context, List<Data.DataBean> data) {
        this.context = context;
        this.data = data;

        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
        //就是一个链表。相当于List
        //分频了内存的

        myLruCache = new MyLruCache(maxSize);
    }

    @Override
    public int getCount() {

//        if (data != null&&data.size()!=0) {
//            return  data.get(0).getData().size();
//        }else {
//            return 0;
//        }
        if (data.size() == 0) {
            return 0;
        }

        return data!=null?data.size():0;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ret = null;

        Holder holder = null;

        if (convertView != null) {
            ret = convertView;
            holder = (Holder) ret.getTag();
        }else {
            ret = LayoutInflater.from(context).inflate(R.layout.list1_item,parent,false);
            holder = new Holder();
            holder.title = (TextView) ret.findViewById(R.id.title);
            holder.creat_time = (TextView) ret.findViewById(R.id.create_time);
            holder.nickname = (TextView) ret.findViewById(R.id.nickname);
            holder.source = (TextView) ret.findViewById(R.id.source);
            holder.wap_thumb = (ImageView) ret.findViewById(R.id.wap_thumb);
            ret.setTag(holder);
        }

        Data.DataBean dataBean = data.get(position);

        holder.title.setText(data.get(position).getTitle());
        holder.source.setText(data.get(position).getSource());
        holder.nickname.setText(data.get(position).getNickname());
        holder.creat_time.setText(data.get(position).getCreate_time());
//        Log.d("flag", "getView: "+data.get(position).getTitle());

        //对图片进行赋值
        //缓存是LruCache:Latest,Recently,Used,
        //最近最少使用原则
        //LruCache 是内存缓存

        //从缓存中获取数据：第一级和第二级
        Bitmap cacheBitmap = getCache(dataBean.getWap_thumb());

        if (cacheBitmap != null) {
            holder.wap_thumb.setImageBitmap(cacheBitmap);
        }else {
            holder.wap_thumb.setTag(dataBean.getWap_thumb());
            getNetImag(dataBean.getWap_thumb(),holder.wap_thumb);
        }

//        String id = data.get(position).getId();
//
//        String imgPath = data.get(position).getWap_thumb();
//
//        final Holder imgHolder = holder;
//
//        if (imgPath != null&&imgPath.length()>0 && !id.equals("7285")) {
//            new BitmapAsycnTask(new bitmapCallBack() {
//                @Override
//                public void callback(Bitmap bitmap) {
//                    imgHolder.wap_thumb.setImageBitmap(bitmap);
//                }
//            }).execute(imgPath);
//        }else {
//            imgHolder.wap_thumb.setImageResource(R.mipmap.ic_launcher);
//        }
        return ret;
    }

    private void getNetImag(final String img, final ImageView imageView) {
        new BitmapAsycnTask(new bitmapCallBack() {
            @Override
            public void callback(byte[] dataImg) {
                String tag = (String) imageView.getTag();

                if (tag.equals(img)){
                    Bitmap bm = BitmapFactory.decodeByteArray(dataImg,0,dataImg.length);
                    imageView.setImageBitmap(bm);

                    myLruCache.put(img.replaceAll("/",""),bm);


                    String root = context.getExternalCacheDir().getAbsolutePath();
                    SdCardUtils.saveFile(dataImg,root,img.replaceAll("/",""));
                }
            }

//            @Override
//            public void callback(byte[] data) {
//                String tag = (String) imageView.getTag();
//
//                if (tag.equals(img)){
//                    Bitmap bm = BitmapFactory.decodeByteArray(data,0,data.length);
//                    imageView.setImageBitmap(bm);
//
//                    myLruCache.put(img.replaceAll("/",""),bm);
//
//
//                    String root = context.getExternalCacheDir().getAbsolutePath();
//                    SdCardUtils.saveFile(data,root,img.replaceAll("/",""));
//                }
//            }
        }).execute(img);
    }

    private Bitmap getCache(String img) {
        //从缓存中获取数据
        //现行你内存中获取数据---->MyLruCache
        img = img.replaceAll("/","");
//        Log.d("flag", "getCache: "+img);
        Bitmap bitmap = myLruCache.get(img);

        if (bitmap != null) {
            return bitmap;
        }else {
            String root = context.getExternalCacheDir().getAbsolutePath();

            String fileName = root+ File.separator+img;

            byte[] bytes = SdCardUtils.getByteFromFile(fileName);

            if (bytes != null) {
                Bitmap bitmapSd =
                        BitmapFactory.decodeByteArray(bytes,0,bytes.length);

                myLruCache.put(img,bitmapSd);

                return bitmapSd;
            }
        }

        return null;
    }

    private class Holder {
        private ImageView wap_thumb;
        private TextView title,source,creat_time,nickname;
    }

    public interface bitmapCallBack{
        void callback(byte[] bytes);
    }

}
