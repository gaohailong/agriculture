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

import java.util.List;

/**
 * 消息界面的adapter
 *
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.items_presonal_myquestion, null);
            holder = new ViewHolder();
            holder.v_left = (View) convertView.findViewById(R.id.v_left);
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
        //通过是否有内容来判断控件是否显示
        if (messageInfo.getContent() != null && !"".equals(messageInfo.getContent())) {
            holder.textViewNoAnswer.setVisibility(View.GONE);
            holder.imageViewAnswer.setVisibility(View.VISIBLE);
            holder.v_left.setBackgroundColor(Color.parseColor("#00b5ad"));
            holder.textViewContent.setText(messageInfo.getContent());
            Picasso.with(context).load(messageInfo.getImgUrl()).placeholder(R.mipmap.img_default_user_portrait_150px)
                    .error(R.mipmap.img_default_user_portrait_150px).into(holder.imageViewHead);
        } else {
            holder.linearLayoutAnswer.setVisibility(View.GONE);
            holder.textViewNoAnswer.setVisibility(View.VISIBLE);
            holder.imageViewAnswer.setVisibility(View.GONE);
        }
        holder.textViewDate.setText(messageInfo.getDate());
        holder.textViewTitle.setText(messageInfo.getTitle());
        return convertView;
    }

    public class ViewHolder {
        View v_left;
        ImageView imageViewAnswer;
        ImageView imageViewHead;
        TextView textViewDate;
        TextView textViewTitle;
        TextView textViewContent;
        TextView textViewNoAnswer;
        LinearLayout linearLayoutAnswer;
    }
}