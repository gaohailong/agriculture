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
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * 个人中心问题adapter
 *
 * @author 李秉龙
 */
public class PersonalQuestionAdapter extends BaseAdapter {
    private Context context;
    ArrayList<MyPersonalQuestion> dates;
    ViewHolder holder;

    public PersonalQuestionAdapter(Context context, ArrayList<MyPersonalQuestion> dates) {
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
            convertView = inflater.inflate(R.layout.item_message_list, null);
            holder = new ViewHolder();
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

//            holder.rv_head = (ImageView) convertView.findViewById(R.id.rv_head);
            holder.tv_is_question = (TextView) convertView.findViewById(R.id.tv_is_question);
            holder.ll_answer = (LinearLayout) convertView.findViewById(R.id.ll_answer);
            holder.v_left = convertView.findViewById(R.id.v_left);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MyPersonalQuestion myPersonalQuestion = dates.get(position);
        holder.tv_date.setText(TimeUtil.format(myPersonalQuestion.getWhenCreated()));
        holder.tv_title.setText(myPersonalQuestion.getTitle());
        //对是否有回答，items显示的改变
//        if (myPersonalQuestion.getQuestionAuditState().equals("WAIT_AUDITED")) {
//            holder.ll_answer.setVisibility(View.GONE);
//            holder.tv_is_question.setText(R.string.no_daudited);
//            holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
//        } else if (myPersonalQuestion.getQuestionResolveState().equals("WAIT_RESOLVE")) {
//            holder.ll_answer.setVisibility(View.GONE);
//            holder.tv_is_question.setText(R.string.no_question);
//            holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
//        } else if(myPersonalQuestion.getQuestionResolveState().equals("RESOLVE")) {
//            holder.ll_answer.setVisibility(View.VISIBLE);
//            holder.tv_content.setText(myPersonalQuestion.getAnswer());
////            Picasso.with(context).load(myPersonalQuestion.getUser().getAvatar()).resize(150, 150).centerCrop().placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.rv_head);
//            holder.tv_is_question.setText(R.string.is_question);
//            holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
//        }
        Log.e("PersonalQA","AudioState"+myPersonalQuestion.getQuestionAuditState()+"  ResolveState:"+myPersonalQuestion.getQuestionResolveState());
        if (myPersonalQuestion.getQuestionAuditState().equals("WAIT_AUDITED")){
            //待审核
            holder.ll_answer.setVisibility(View.GONE);
            holder.tv_is_question.setText(R.string.no_daudited);
            holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }else {
            if (myPersonalQuestion.getQuestionAuditState().equals("FAILED")){
                //未通过
                holder.ll_answer.setVisibility(View.GONE);
                holder.tv_is_question.setText(R.string.failed_daudited);
                holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
            }else {
                //已通过的问题
                if (myPersonalQuestion.getQuestionResolveState().equals("RESOLVED")){
                    //已经回答
                    holder.ll_answer.setVisibility(View.VISIBLE);
                    holder.tv_content.setText(myPersonalQuestion.getAnswer());
                    holder.tv_is_question.setText(R.string.is_question);
                    holder.v_left.setBackgroundColor(Color.parseColor("#ff6446"));
                }else {
                    //未回答
                    holder.ll_answer.setVisibility(View.GONE);
                    holder.tv_is_question.setText(R.string.no_question);
                    holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
                }
            }
        }


        return convertView;
    }

    public class ViewHolder {

        ImageView rv_head;
        TextView tv_date;
        TextView tv_title;
        TextView tv_content;
        TextView tv_is_question;
        LinearLayout ll_answer;
        View v_left;

    }
}