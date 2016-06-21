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
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面的adapter
 *
 * @author 高海龙
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MessageInfo> dates;
    ViewHolder holder;

    public MessageAdapter(Context context, ArrayList<MessageInfo> dates) {
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
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.items_presonal_myquestion, null);
            holder = new ViewHolder();
            holder.v_left = (View) convertView.findViewById(R.id.v_left);
            holder.iv_messagetype= (ImageView) convertView.findViewById(R.id.iv_messagetype);
            holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MessageInfo messageInfo = dates.get(position);
        if (messageInfo.getMessageType()=="QUESTION"){
            Picasso.with(context).load(R.drawable.ic_message_question_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }else if (messageInfo.getMessageType()=="RELATION"){
            Picasso.with(context).load(R.drawable.ic_message_system_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }else if (messageInfo.getMessageType()=="SYSTEM"){
            Picasso.with(context).load(R.drawable.ic_message_system_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }else if (messageInfo.getMessageType()=="WECHAT"){
            Picasso.with(context).load(R.drawable.ic_message_wechat_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }else if (messageInfo.getMessageType()=="NOTICE"){
            Picasso.with(context).load(R.drawable.ic_message_system_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }else if (messageInfo.getMessageType()=="TRADE"){
            Picasso.with(context).load(R.drawable.ic_message_money_128px).resize(110,110).centerCrop()
                    .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.iv_messagetype);
        }
        holder.tv_title.setText(messageInfo.getTitle());
        holder.tv_time.setText(TimeUtil.format(messageInfo.getWhenCreated()));
        return convertView;
    }

    public class ViewHolder {
        View v_left;
        ImageView iv_messagetype;
        TextView tv_title;
        TextView tv_time;
    }
}