package com.sxau.agriculture.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.internal.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.QuestionBean;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/4/13.
 */
public class QuestionListViewFragment extends BaseFragment {
    private  View mView;
    private ListView lvQuestionList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_question_list,container, false);
        lvQuestionList = (ListView) mView.findViewById(R.id.lv_question);


        BaseAdapter adapter = new QuestionAdapter(QuestionListViewFragment.this.getActivity(),initDate());
        lvQuestionList.setAdapter(adapter);
        return mView;
    }
    private QuestionBean[] initDate(){
        QuestionBean[] questionDate = new QuestionBean[2];

        questionDate[0] = new QuestionBean();
        questionDate[0].setState(true);
        questionDate[0].setHead(R.drawable.touxiang);
        questionDate[0].setName("帅子1");
        questionDate[0].setTitle("lalala");
        questionDate[0].setContent("的可能非公开多久");

        questionDate[1] = new QuestionBean();
        questionDate[1].setState(false);
        questionDate[1].setHead(R.drawable.touxiang);
        questionDate[1].setName("帅子2");
        questionDate[1].setTitle("lalala2");

//        BaseAdapter adapter=new InfoDemandAdapter(InfoListViewFragment.this.getActivity(),infoDatas);
//        lv_Info.setAdapter(adapter);


        return questionDate;
    }
}
