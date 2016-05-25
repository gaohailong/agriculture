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

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.Question;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.view.activity.DetailQuestion;

import java.util.ArrayList;

/**
 * 问答页面adapter
 * @author 李秉龙
 */
public class QuestionAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private ArrayList<QuestionData> questionDatas;
    private int favIndex = 0;//判断是否收藏0：没有收藏；1：以收藏


    public QuestionAdapter(Context context, ArrayList<QuestionData> questionDatas) {
        this.context = context;
        this.questionDatas = questionDatas;
    }

    @Override
    public int getCount() {
        return questionDatas.size();
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
            convertView=LayoutInflater.from(context).inflate(R.layout.item_question_list, null);
            holder = new ViewHolder();
            holder.rv_head = (ImageView) convertView.findViewById(R.id.rv_head);
            holder.iv_fav = (ImageView) convertView.findViewById(R.id.iv_fav);
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
        QuestionData questionData = questionDatas.get(position);
        Picasso.with(context).load(R.mipmap.img_default_user_portrait_150px).resize(150,150).centerCrop().placeholder(R.mipmap.img_default_user_portrait_150px)
                .error(R.mipmap.img_default_user_portrait_150px).into(holder.rv_head);
        holder.tv_name.setText(questionData.getUser().getName());
        holder.tv_title.setText(questionData.getTitle());
        if(questionData.getTitle()!=null && !questionData.getQuestionAuditState().equals("WAIT_AUDITED")){
        holder.tv_content.setText(questionData.getContent());
        holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }else {
            holder.ll_answer.setVisibility(View.GONE);
        }

        holder.iv_fav.setOnClickListener(this);
        holder.ll_fav_background.setOnClickListener(this);
       // holder.ll_SubjectSkip.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = new ViewHolder();
        holder.iv_fav = (ImageView) v.findViewById(R.id.iv_fav);
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


        }

    }

    private class ViewHolder{
        private ImageView rv_head;
        private ImageView iv_fav;

        private TextView tv_name;
        private TextView tv_title;
        private TextView tv_content;
        private LinearLayout ll_answer;
        private LinearLayout ll_fav_background;//收藏的linearlayout
        private View v_left;

    }
}
