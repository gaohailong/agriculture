package com.sxau.agriculture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.QuestionBean;

/**
 * Created by Administrator on 2016/4/13.
 */
public class QuestionAdapter extends BaseAdapter{
    private Context context;
    private QuestionBean questionBean[];

    public QuestionAdapter(Context context, QuestionBean[] questionBean) {
        this.context = context;
        this.questionBean = questionBean;
    }

    @Override
    public int getCount() {
        return questionBean.length;
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_question_list,null);
            holder = new ViewHolder();
            holder.head = (ImageView) convertView.findViewById(R.id.rv_head);
            holder.fav = (ImageView) convertView.findViewById(R.id.iv_fav);
            holder.quick = (ImageView) convertView.findViewById(R.id.iv_quick);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionBean questionDate = questionBean[position];
        holder.head.setImageResource(questionDate.getHead());
        holder.name.setText(questionDate.getName());
        if(questionDate.isState()){
        holder.content.setText(questionDate.getContent());
        }else {
            holder.answer.setVisibility(View.GONE);
        }

        return convertView;
    }
    private class ViewHolder{
        private boolean state;
        private ImageView head,fav,quick;
        private TextView name,title,content;
        private LinearLayout answer;
    }
}
