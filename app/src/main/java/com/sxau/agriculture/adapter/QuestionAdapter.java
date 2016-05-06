package com.sxau.agriculture.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.Question;
import com.sxau.agriculture.view.activity.DetailQuestion;

/**
 * 问答页面适配器
 * 李秉龙
 */
public class QuestionAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private Question question[];
    private int favIndex = 0;//判断是否收藏0：没有收藏；1：以收藏

    public QuestionAdapter(Context context, Question[] question) {
        this.context = context;
        this.question = question;
    }

    @Override
    public int getCount() {
        return question.length;
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
            holder.fav = (ImageView) convertView.findViewById(R.id.ib_fav);
            holder.quick = (ImageView) convertView.findViewById(R.id.iv_quick);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            holder.ll_FavBackground  = (LinearLayout) convertView.findViewById(R.id.ll_fav_background);
            holder.v_left = convertView.findViewById(R.id.v_left);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Question questionDate = question[position];
        holder.head.setImageResource(questionDate.getHead());
        holder.name.setText(questionDate.getName());
        if(questionDate.isState()){
        holder.content.setText(questionDate.getContent());
        holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }else {
            holder.answer.setVisibility(View.GONE);
        }

        holder.fav.setOnClickListener(this);
        holder.ll_FavBackground.setOnClickListener(this);
       // holder.ll_SubjectSkip.setOnClickListener(this);
        holder.quick.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = new ViewHolder();
        holder.fav = (ImageView) v.findViewById(R.id.ib_fav);
        holder.quick = (ImageView) v.findViewById(R.id.ib_quick);
        switch (v.getId()){
            case R.id.ib_fav:
                if (favIndex==0){
                    holder.fav.setImageResource(R.drawable.ic_praise_48px);
                    favIndex=1;
                }else {
                    holder.fav.setImageResource(R.drawable.ic_no_praise_48px);
                    favIndex=0;
                }
                break;
            case R.id.iv_quick:
               Toast.makeText(context,"sss",Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private class ViewHolder{
        private boolean state;
        private ImageView head;
        private ImageView fav;
        private ImageView quick;
        private TextView name;
        private TextView title;
        private TextView content;
        private LinearLayout answer;
        private LinearLayout ll_FavBackground;//收藏的linearlayout
        private View v_left;

    }
}
