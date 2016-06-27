package com.sxau.agriculture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;

/**
 * 问答页面adapter
 *
 * @author
 */
public class QuestionAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QuestionData> questionDatas;
    private boolean favIndex;//判断是否收藏


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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_question_list, null);
            holder = new ViewHolder();
            holder.iv_fav = (ImageView) convertView.findViewById(R.id.iv_fav);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holder.v_left = convertView.findViewById(R.id.v_left);
            holder.tv_ntdmix = (TextView) convertView.findViewById(R.id.tv_ntdmix);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        QuestionData questionData = questionDatas.get(position);
        holder.tv_title.setText(questionData.getTitle());
        holder.tv_content.setText(questionData.getContent());
        holder.tv_ntdmix.setText(questionData.getUser().getName() + " 提问于 " + TimeUtil.format(questionData.getWhenCreated()));
        favIndex = questionData.isFav();
        LogUtil.e("QuestionAdapter","position:"+questionData.getId()+"isFav:"+favIndex);
        if (questionData.getTitle() != null && !questionData.getQuestionAuditState().equals("WAIT_AUDITED")
                && !questionData.getQuestionResolveState().equals("WAIT_RESOLVE")) {
            holder.v_left.setBackgroundColor(Color.parseColor("#009688"));
        }
        if (favIndex) {
            holder.iv_fav.setImageResource(R.drawable.collection_fill);
        } else {
            holder.iv_fav.setImageResource(R.drawable.collection);
        }

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_fav;
        private TextView tv_ntdmix;
        private TextView tv_title;
        private TextView tv_content;
        private View v_left;

    }
}
