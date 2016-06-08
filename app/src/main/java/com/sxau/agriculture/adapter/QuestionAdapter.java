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
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.utils.TimeUtil;

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
            holder.iv_fav = (ImageView) convertView.findViewById(R.id.iv_fav);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);
            holder.v_left = convertView.findViewById(R.id.v_left);
            holder.tv_ntdmix= (TextView) convertView.findViewById(R.id.tv_ntdmix);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionData questionData = questionDatas.get(position);
        holder.tv_title.setText(questionData.getTitle());
        holder.tv_ntdmix.setText(questionData.getUser().getName()+"提问于"+ TimeUtil.format(questionData.getWhenCreated()));
        if(questionData.getTitle()!=null && !questionData.getQuestionAuditState().equals("WAIT_AUDITED")
                && !questionData.getQuestionResolveState().equals("WAIT_RESOLVE")){
        holder.tv_answer.setText(questionData.getAnswers().toString());
        holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }else {
            holder.tv_answer.setVisibility(View.GONE);
        }

        holder.iv_fav.setOnClickListener(this);
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
                    holder.iv_fav.setImageResource(R.drawable.collection_fill);
                    favIndex=1;
                }else {
                    holder.iv_fav.setImageResource(R.drawable.collection);
                    favIndex=0;
                }
                break;


        }

    }

    private class ViewHolder{
        private ImageView iv_fav;

        private TextView tv_ntdmix;
        private TextView tv_title;
        private TextView tv_answer;
        private View v_left;

    }
}
