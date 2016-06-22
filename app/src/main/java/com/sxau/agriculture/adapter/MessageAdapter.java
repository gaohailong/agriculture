package com.sxau.agriculture.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.activity.WebViewTwoActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面的adapter
 *
 * @author 崔志泽
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MessageInfo> dates;
    ViewHolder holder;

    public MessageAdapter(Context context, ArrayList<MessageInfo> dates) {
        Log.e("messageInfo6", dates.size() + "");
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
            holder.iv_messagetype = (ImageView) convertView.findViewById(R.id.iv_messagetype);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MessageInfo messageInfo = dates.get(position);
        Log.e("messageInfo6", messageInfo.toString());
        String type = messageInfo.getMessageType();
        switch (type) {
            case ConstantUtil.QUESTION://问答
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_question_128px);
                break;
            case ConstantUtil.TRADE://交易
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_money_128px);
                break;
            case ConstantUtil.ARTICLE://文章
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_question_128px);//TODO 没有图片
                break;
            case ConstantUtil.RELATION://关系
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_system_128px);//TODO 没有图片
                break;
            case ConstantUtil.SYSTEM://系统
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_system_128px);
                break;
            case ConstantUtil.WECHAT://微信
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_wechat_128px);
                break;
            case ConstantUtil.NOTICE://公告
                holder.iv_messagetype.setImageResource(R.drawable.ic_message_system_128px);//TODO 没有图片
                break;
            default:
                break;
        }
        holder.tv_title.setText(messageInfo.getTitle());
        holder.tv_time.setText(TimeUtil.format(messageInfo.getWhenCreated()));
        if (messageInfo.isMarkRead()) {
            holder.v_left.setBackgroundColor(Color.parseColor("#00b5ad"));
        } else {
            holder.v_left.setBackgroundColor(Color.parseColor("#FF6446"));
        }
        return convertView;
    }

    public class ViewHolder {
        View v_left;
        ImageView iv_messagetype;
        TextView tv_title;
        TextView tv_time;
    }
}