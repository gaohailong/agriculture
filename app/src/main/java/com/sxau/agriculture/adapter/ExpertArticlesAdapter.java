package com.sxau.agriculture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.ExpertArticles;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;

/**
 * 专家个人中心，专家文章的adapter
 *
 * @author Yawen_Li
 */
public class ExpertArticlesAdapter extends BaseAdapter {
    ArrayList<HomeArticle> data;
    private Context context;

    public ExpertArticlesAdapter(ArrayList<HomeArticle> data, Context context) {
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_list, null);
            holder.iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_read = (TextView) convertView.findViewById(R.id.tv_read);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeArticle expertArticle = data.get(position);
        Picasso.with(context).load(ConstantUtil.BASE_PICTURE_URL+expertArticle.getImage()).resize(150, 130).centerCrop().placeholder(R.mipmap.ic_loading)
                .error(R.mipmap.ic_load_fail).into(holder.iv_img);
        holder.tv_title.setText(expertArticle.getTitle());
        holder.tv_time.setText(TimeUtil.format(expertArticle.getWhenCreated()));
        holder.tv_read.setText(String.valueOf(expertArticle.getClickCount()));

        return convertView;
    }

    private static class ViewHolder {
        ImageView iv_img;
        TextView tv_title;
        TextView tv_time;
        TextView tv_read;
    }
}
