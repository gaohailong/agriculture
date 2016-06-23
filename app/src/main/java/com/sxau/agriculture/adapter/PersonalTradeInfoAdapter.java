package com.sxau.agriculture.adapter;

import android.content.Context;
import android.graphics.Color;
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
 *
 * @author 李秉龙
 */
public class PersonalTradeInfoAdapter extends BaseAdapter {
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
            holder.v_left = convertView.findViewById(R.id.v_left);
            holder.tv_TradeDate = (TextView) convertView.findViewById(R.id.tv_trade_date);
            holder.tv_TradeTitle = (TextView) convertView.findViewById(R.id.tv_trade_title);
            holder.tv_TradeContent = (TextView) convertView.findViewById(R.id.tv_trade_content);
            holder.tv_is_question = (TextView) convertView.findViewById(R.id.tv_is_question);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyPersonalTrade myPersonalTrade = dates.get(position);
        holder.tv_TradeDate.setText(TimeUtil.format(myPersonalTrade.getWhenUpdated()));
        holder.tv_TradeTitle.setText(myPersonalTrade.getTitle());
        holder.tv_TradeContent.setText(myPersonalTrade.getDescription());
        switch (myPersonalTrade.getTradeState()) {
            case "WAIT_AUDITED":
                holder.tv_is_question.setText("待审核");
                holder.v_left.setBackgroundColor(Color.parseColor("#FF6446"));
                break;
            case "FAILED":
                holder.tv_is_question.setText("未通过");
                holder.v_left.setBackgroundColor(Color.parseColor("#FF6446"));
                break;
            case "AUDITED":
                holder.tv_is_question.setText("已通过");
                holder.v_left.setBackgroundColor(Color.parseColor("#00b5ad"));
                break;
            default:
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_TradeDate;
        TextView tv_TradeTitle;
        TextView tv_TradeContent;
        TextView tv_is_question;
        View v_left;
    }
}
