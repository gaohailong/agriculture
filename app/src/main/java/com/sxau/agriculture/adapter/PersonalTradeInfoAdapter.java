package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;

/**
 * 个人中心交易信息adapter
 * @author 李秉龙
 */
public class PersonalTradeInfoAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<MyPersonalTrade> dates;
    ViewHolder holder;

    public PersonalTradeInfoAdapter(Context context, ArrayList<MyPersonalTrade> dates) {
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.items_presonal_mytrade, null);
            holder = new ViewHolder();
            holder.rv_InfoHead= (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.tv_TradeName = (TextView) convertView.findViewById(R.id.tv_trade_name);
            holder.tv_TradeAddress = (TextView) convertView.findViewById(R.id.tv_trade_address);
            holder.tv_TradeDate = (TextView) convertView.findViewById(R.id.tv_trade_date);
            holder.tv_TradeTitle = (TextView) convertView.findViewById(R.id.tv_trade_title);
            holder.tv_TradeContent = (TextView) convertView.findViewById(R.id.tv_trade_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyPersonalTrade myPersonalTrade = dates.get(position);
        Picasso.with(context).load(myPersonalTrade.getUser().getAvatar()).resize(150,150).centerCrop().placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.rv_InfoHead);
        holder.tv_TradeName.setText(myPersonalTrade.getUser().getName());
        holder.tv_TradeAddress.setText(myPersonalTrade.getUser().getAddress());
        holder.tv_TradeDate.setText(TimeUtil.format(myPersonalTrade.getWhenUpdated()) );
        holder.tv_TradeTitle.setText(myPersonalTrade.getTitle());
        holder.tv_TradeContent.setText(myPersonalTrade.getDescription());
        return convertView;
    }

    private class ViewHolder{
        ImageView rv_InfoHead;
        TextView tv_TradeName;
        TextView tv_TradeAddress;
        TextView tv_TradeDate;
        TextView tv_TradeTitle;
        TextView tv_TradeContent;

    }
}
