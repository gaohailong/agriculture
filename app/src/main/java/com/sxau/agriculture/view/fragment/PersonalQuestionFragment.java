package com.sxau.agriculture.view.fragment;


import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/10.
 */
public class PersonalQuestionFragment extends BaseFragment implements IPersonalQuestionFragment{
    private ListView listView;

    private IPersonalQuestionPresenter iPersonalQuestionPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将Fragment与Presenter绑定
        iPersonalQuestionPresenter = new PersonalQuestionPresenter(PersonalQuestionFragment.this);

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        PersonalQuestionAdapter adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(),getDate());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailQuestion.actionStart(PersonalQuestionFragment.this.getActivity(), position);
            }
        });


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
//-----------------接口方法----------------
    @Override
    public void updateView() {

    }
//----------------接口方法结束------------------
}
