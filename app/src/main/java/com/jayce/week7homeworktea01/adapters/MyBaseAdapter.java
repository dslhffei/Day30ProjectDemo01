package com.jayce.week7homeworktea01.adapters;

import android.content.Context;
import android.graphics.Bitmap;
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

import java.util.List;

/**
 * Created by 会函 on 2016/11/12.
 */
public class MyBaseAdapter extends BaseAdapter {

    private Context context;

    private List<Data.DataBean> data;

    public MyBaseAdapter(Context context, List<Data.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {

//        if (data != null&&data.size()!=0) {
//            return  data.get(0).getData().size();
//        }else {
//            return 0;
//        }

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

        holder.title.setText(data.get(position).getTitle());
        holder.source.setText(data.get(position).getSource());
        holder.nickname.setText(data.get(position).getNickname());
        holder.creat_time.setText(data.get(position).getCreate_time());

        String id = data.get(position).getId();

        String imgPath = data.get(position).getWap_thumb();

        final Holder imgHolder = holder;

        if (imgPath != null&&imgPath.length()>0 && !id.equals("7285")) {
            new BitmapAsycnTask(new bitmapCallBack() {
                @Override
                public void callback(Bitmap bitmap) {
                    imgHolder.wap_thumb.setImageBitmap(bitmap);
                }
            }).execute(imgPath);
        }else {
            imgHolder.wap_thumb.setImageResource(R.mipmap.ic_launcher);
        }
        return ret;
    }

    private class Holder {
        private ImageView wap_thumb;
        private TextView title,source,creat_time,nickname;
    }

    public interface bitmapCallBack{
        void callback(Bitmap bitmap);
    }

}
