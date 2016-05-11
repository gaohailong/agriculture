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
 * 问答页面adapter
 * @author 李秉龙
 */
public class QuestionAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private Question question[];
    private int favIndex = 0;//判断是否收藏0：没有收藏；1：以收藏
    private int quickIndex = 0;//判断是否催0：没有催；1：以催

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
            holder.rv_head = (ImageView) convertView.findViewById(R.id.rv_head);
            holder.iv_fav = (ImageView) convertView.findViewById(R.id.iv_fav);
            holder.iv_quick = (ImageView) convertView.findViewById(R.id.iv_quick);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.ll_answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            holder.ll_fav_background  = (LinearLayout) convertView.findViewById(R.id.ll_fav_background);
            holder.v_left = convertView.findViewById(R.id.v_left);
            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Question questionDate = question[position];
        holder.rv_head.setImageResource(questionDate.getHead());
        holder.tv_name.setText(questionDate.getName());
        if(questionDate.isState()){
        holder.tv_content.setText(questionDate.getContent());
        holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }else {
            holder.ll_answer.setVisibility(View.GONE);
        }

        holder.iv_fav.setOnClickListener(this);
        holder.ll_fav_background.setOnClickListener(this);
       // holder.ll_SubjectSkip.setOnClickListener(this);
        holder.iv_quick.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = new ViewHolder();
        holder.iv_fav = (ImageView) v.findViewById(R.id.iv_fav);
        holder.iv_quick = (ImageView) v.findViewById(R.id.iv_quick);
        switch (v.getId()){
            case R.id.iv_fav:
                if (favIndex==0){
                    holder.iv_fav.setImageResource(R.drawable.ic_praise_48px);
                    favIndex=1;
                }else {
                    holder.iv_fav.setImageResource(R.drawable.ic_no_praise_48px);
                    favIndex=0;
                }
                break;
            case R.id.iv_quick:
                if (quickIndex==0){
                    holder.iv_quick.setImageResource(R.mipmap.ic_quick_false_48px);

                    quickIndex=1;
                }else {
                    holder.iv_quick.setImageResource(R.mipmap.ic_quick_true_48px);
                    quickIndex=0;
                }
                break;
        }

    }

    private class ViewHolder{
        private boolean state;
        private ImageView rv_head;
        private ImageView iv_fav;
        private ImageView iv_quick;
        private TextView tv_name;
        private TextView tv_title;
        private TextView tv_content;
        private LinearLayout ll_answer;
        private LinearLayout ll_fav_background;//收藏的linearlayout
        private View v_left;

    }
}
