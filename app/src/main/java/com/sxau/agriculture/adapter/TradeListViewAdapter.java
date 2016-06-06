package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 信息专区ListView的Adapter
 *
 * @author 田帅
 */
public class TradeListViewAdapter extends BaseAdapter implements View.OnClickListener {
    /**
     * 上下文 实体类
     */
    private Context context;
    private ArrayList<TradeData> datas;
    ViewHolder holder;
    /**
     * 收藏按钮点击判断
     */
    private boolean flag;

    public TradeListViewAdapter(Context context, ArrayList<TradeData> datas) {
        this.context = context;
        this.datas = datas;
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_trade_demand, null);
            holder = new ViewHolder();
            holder.ivHead = (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.name = (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title = (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ivLocation = (ImageView) convertView.findViewById(R.id.iv_demand_location);
            holder.ivCollection = (ImageView) convertView.findViewById(R.id.iv_demand_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * 根据位置获得数据
         * */
        TradeData infoData = datas.get(position);
        /**
         * 日期格式
         * */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        /**
         * 给控件赋值
         * */
        if (infoData.getUser().getAvatar() == null) {
            holder.ivHead.setImageResource(R.mipmap.img_default_user_portrait_150px);
        } else {

        }
        holder.name.setText(infoData.getUser().getName());
        holder.date.setText(sdf.format(infoData.getWhenCreated()));
/**
 * 隐藏距离和图标
 * */
        holder.distance.setVisibility(View.GONE);
        holder.ivLocation.setVisibility(View.GONE);

        holder.distance.setText("2千米");
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getDescription());
        holder.ivLocation.setImageResource(R.drawable.ic_location_48dp);
        /**
         * 用户收藏状态
         * */
        flag=infoData.isFav();
        if (flag) {
            holder.ivCollection.setImageResource(R.mipmap.collection_fill);
        } else {
            holder.ivCollection.setImageResource(R.mipmap.collection);
        }
        holder.ivCollection.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_demand_collection:
                holder.ivCollection = (ImageView) v.findViewById(R.id.iv_demand_collection);
                if (flag) {
                    holder.ivCollection.setImageResource(R.mipmap.collection);
                    flag = false;
                } else {
                    holder.ivCollection.setImageResource(R.mipmap.collection_fill);
                    flag = true;
                }

                break;
        }
    }


    public class ViewHolder {
        ImageView ivCollection;
        ImageView ivHead;
        TextView name;
        TextView date;
        TextView distance;
        TextView title;
        TextView content;
        ImageView ivLocation;
    }

}
