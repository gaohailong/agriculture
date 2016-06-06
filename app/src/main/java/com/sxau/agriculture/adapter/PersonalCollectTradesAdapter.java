package com.sxau.agriculture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/6/3.
 */
public class PersonalCollectTradesAdapter extends BaseAdapter{
    private ArrayList<MyPersonalCollectTrades> dates;
    private Context context;

    public PersonalCollectTradesAdapter(Context context, ArrayList<MyPersonalCollectTrades> dates) {
        this.context = context;
        this.dates = dates;
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater  = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.items_presonal_mytrade,null);
            holder = new ViewHolder();
            holder.rv_InfoHead= (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.tv_TradeName = (TextView) convertView.findViewById(R.id.tv_trade_name);
            holder.tv_TradeAddress = (TextView) convertView.findViewById(R.id.tv_trade_address);
            holder.tv_TradeDate = (TextView) convertView.findViewById(R.id.tv_trade_date);
            holder.tv_TradeTitle = (TextView) convertView.findViewById(R.id.tv_trade_title);
            holder.tv_TradeContent = (TextView) convertView.findViewById(R.id.tv_trade_content);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        MyPersonalCollectTrades myPersonalCollectTrades = dates.get(position);
//        Picasso.with(context).load(myPersonalCollectTrades.getUser().getAvatar().toString()).resize(150,150).centerCrop().placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.rv_InfoHead);
        holder.tv_TradeName.setText(myPersonalCollectTrades.getUser().getName());
        if (myPersonalCollectTrades.getUser().getAddress() != null) {
            holder.tv_TradeAddress.setText(myPersonalCollectTrades.getUser().getAddress());
        } else {
            holder.tv_TradeAddress.setText("未知");
        }
        holder.tv_TradeDate.setText(TimeUtil.format(myPersonalCollectTrades.getUser().getWhenUpdated()) );
        holder.tv_TradeTitle.setText(myPersonalCollectTrades.getTitle());
        holder.tv_TradeContent.setText(myPersonalCollectTrades.getDescription());

        return convertView;
    }
    public class ViewHolder{
        ImageView rv_InfoHead;
        TextView tv_TradeName;
        TextView tv_TradeAddress;
        TextView tv_TradeDate;
        TextView tv_TradeTitle;
        TextView tv_TradeContent;
    }
}
