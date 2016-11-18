package com.jayce.week7homeworktea01.adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.jayce.week7homeworktea01.Activitys.CollectionActivity;
import com.jayce.week7homeworktea01.R;
import com.jayce.week7homeworktea01.utils.TeaDatabaseCollectHelper;

import java.util.List;

/**
 * Created by 会函 on 2016/11/16.
 */
public class MyCollectionAdapter extends CursorAdapter {

    private int titleIndex;
    private int sourceIndex;
    private int nameIndex;
    private int dateIndex;
    private int pictureIndex;
    private int descriptionIndex;


    public MyCollectionAdapter(Context context, Cursor c) {
        super(context, c, MyCollectionAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


/**
 *  "id": "8207",
 "title": "“日照绿茶”品牌价值8.85亿 居山东茶类之首",
 "source": "转载",
 "description": "",
 "wap_thumb": "http://s1.sns.maimaicha.com/images/2016/01/04/20160104104521_47289_suolue3.jpg",
 "create_time": "01月04日10:47",
 "nickname": "bubu123"
 *
 */
        titleIndex = c.getColumnIndex("title");
        sourceIndex = c.getColumnIndex("source");
        nameIndex = c.getColumnIndex("nickname");
        dateIndex = c.getColumnIndex("create_time");
        pictureIndex = c.getColumnIndex("wap_thumb");
        descriptionIndex=c.getColumnIndex("description");
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_collection_item,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder viewHolder = (ViewHolder) view.getTag();

        String title = cursor.getString(titleIndex);
        String source = cursor.getString(sourceIndex);
        String create_time = cursor.getString(dateIndex);


        viewHolder.titleTextView.setText(title);
        viewHolder.sourceTextView.setText(source);
        viewHolder.dateTextView.setText(create_time);



    }

    class ViewHolder{
        TextView titleTextView;
        TextView sourceTextView;
        TextView dateTextView;

        public ViewHolder(View view) {
            titleTextView= (TextView) view.findViewById(R.id.title);
            sourceTextView= ((TextView) view.findViewById(R.id.source));
            dateTextView= ((TextView) view.findViewById(R.id.create_time));
        }
    }

}
