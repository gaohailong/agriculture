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

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.bean.MessageList;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息界面的adapter
 * @author 高海龙
 */
public class MessageAdapter extends BaseAdapter {
    private Context context;
    private List<MessageInfo> dates;
    ViewHolder holder;

    public MessageAdapter(Context context, List<MessageInfo> dates) {
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
            Log.e("asaas",context+"aa");
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.presonal_myquestion_items, null);
            holder = new ViewHolder();
            holder.textViewDate = (TextView) convertView.findViewById(R.id.tv_date);
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.tv_title);
            holder.textViewContent = (TextView) convertView.findViewById(R.id.tv_content);
            holder.imageViewAnswer = (ImageView) convertView.findViewById(R.id.iv_answer);
            holder.imageViewHead = (ImageView) convertView.findViewById(R.id.rv_head);
            holder.textViewNoAnswer = (TextView) convertView.findViewById(R.id.tv_no_answer);
            holder.linearLayoutAnswer = (LinearLayout) convertView.findViewById(R.id.ll_answer_ll);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MessageInfo messageInfo = dates.get(position);
        if(messageInfo.getContent()!=null){
            holder.textViewNoAnswer.setVisibility(View.GONE);
            holder.imageViewAnswer.setVisibility(View.VISIBLE);
        }else {
            holder.textViewNoAnswer.setVisibility(View.VISIBLE);
            holder.imageViewAnswer.setVisibility(View.GONE);
        }
        holder.textViewDate.setText(messageInfo.getDate());
        holder.textViewTitle.setText(messageInfo.getTitle());
        holder.textViewContent.setText(messageInfo.getContent());



        /*//对是否有回答，items显示的改变
        if (myPersonalQuestion.getState()) {
            holder.textViewContent.setText(myPersonalQuestion.getContext());
            holder.imageViewHead.setImageResource(R.mipmap.ic_launcher);
            holder.textViewNoAnswer.setVisibility(View.GONE);
        } else {
            holder.linearLayoutAnswer.setVisibility(View.GONE);
//                textViewContent.setVisibility(View.GONE);
            holder.imageViewAnswer.setVisibility(View.GONE);
//                imageViewHead.setVisibility(View.GONE);
        }*/
        return convertView;
    }

    public class ViewHolder {
        ImageView imageViewAnswer;
        ImageView imageViewHead;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewNoAnswer;
        LinearLayout linearLayoutAnswer;
    }
}