package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.fragment.PushBean;

/**
 * Created by czz on 2016/4/8.
 */
public class HomePushAdapter extends BaseAdapter{
    PushBean data[];
    private Context context;

    public HomePushAdapter(PushBean[] data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView iv_news;
        TextView tv_title;
        TextView tv_time;
        ImageView iv_browse;
        TextView tv_read;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=new ViewHolder();

        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.home_listview_item,null);
            holder.iv_news= (ImageView) convertView.findViewById(R.id.iv_news);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            holder.iv_browse= (ImageView) convertView.findViewById(R.id.iv_browse);
            holder.tv_read= (TextView) convertView.findViewById(R.id.tv_read);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        PushBean pushBean=data[position];
        holder.iv_news.setImageResource(R.drawable.phone_48dp);
        holder.tv_title.setText(pushBean.getTitle());
        holder.tv_time.setText(pushBean.getTime());
        holder.iv_browse.setImageResource(R.drawable.ic_read_48px);
        holder.tv_read.setText(pushBean.getRead());

        return convertView;
    }
}
