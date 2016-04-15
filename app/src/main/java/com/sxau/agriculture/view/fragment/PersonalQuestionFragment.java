package com.sxau.agriculture.view.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/10.
 */
public class PersonalQuestionFragment extends BaseFragment {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        MyAdapter adapter = new MyAdapter(getDate());
        listView.setAdapter(adapter);


        return myQuestionView;
    }

    /**
     * 数据源
     *
     * @return ArrayList类型 list对象
     */
    private ArrayList<MyPersonalQuestion> getDate() {
        ArrayList<MyPersonalQuestion> list = new ArrayList<MyPersonalQuestion>();
        MyPersonalQuestion myPersonalQuestion1 = new MyPersonalQuestion();
        myPersonalQuestion1.setState(true);
        myPersonalQuestion1.setDate("1111.11.11");
        myPersonalQuestion1.setTitle("啦啦啦啦1");
        myPersonalQuestion1.setContext("人的身份和书法家的护是大哥好地方挂机的高房价的高房价身符格式的风格是否退回");

        MyPersonalQuestion myPersonalQuestion2 = new MyPersonalQuestion();
        myPersonalQuestion2.setState(false);
        myPersonalQuestion2.setDate("2222.22.22");
        myPersonalQuestion2.setTitle("啦啦啦啦2");

        list.add(myPersonalQuestion1);
        list.add(myPersonalQuestion2);

        return list;
    }

    /**
     * 自定义adapter
     */
    public class MyAdapter extends BaseAdapter {
        ArrayList<MyPersonalQuestion> list = new ArrayList<MyPersonalQuestion>();

        public MyAdapter(ArrayList<MyPersonalQuestion> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
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

                LayoutInflater inflater = LayoutInflater.from(getActivity());
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


            holder.textViewDate.setText(list.get(position).getDate());
            holder.textViewTitle.setText(list.get(position).getTitle());

            //对是否有回答，items显示的改变
            if (list.get(position).getState()) {
                holder.textViewContent.setText(list.get(position).getContext());
                holder.imageViewHead.setImageResource(R.mipmap.ic_launcher);
                holder.textViewNoAnswer.setVisibility(View.GONE);
            } else {
                holder.linearLayoutAnswer.setVisibility(View.GONE);
//                textViewContent.setVisibility(View.GONE);
                holder.imageViewAnswer.setVisibility(View.GONE);
//                imageViewHead.setVisibility(View.GONE);
            }
            return convertView;
        }
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
