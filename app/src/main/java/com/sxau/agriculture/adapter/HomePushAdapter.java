package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.HomeListViewBean;

import java.util.ArrayList;

/**
 * 首页listview的adapter
 * @author 崔志泽
 */
public class HomePushAdapter extends BaseAdapter{
    ArrayList<HomeListViewBean> data;
    private Context context;

    public HomePushAdapter(ArrayList<HomeListViewBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
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
        HomeListViewBean homeListViewBean =data.get(position);
        holder.iv_news.setImageResource(R.drawable.phone_48dp);
        holder.tv_title.setText(homeListViewBean.getTitle());
        holder.tv_time.setText(homeListViewBean.getTime());
        holder.iv_browse.setImageResource(R.drawable.ic_read_48px);
        holder.tv_read.setText(homeListViewBean.getRead());

        return convertView;
    }
}
