package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalCollectionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectQuestionPresenter;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.view.fragment_interface.IPresonalCollectQuestionFragment;

import java.util.ArrayList;

/**
 * 个人中心我的问题的listView的fragment
 * 李秉龙
 */
public class PersonalCollectQuestionFragment  extends BaseFragment implements IPresonalCollectQuestionFragment{
    private ListView listView;

    private IPersonalCollectQuestionPresenter iPersonalCollectQuestionPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将Fragment对象与Presenter对象绑定
        iPersonalCollectQuestionPresenter = new PersonalCollectionPresenter(PersonalCollectQuestionFragment.this);

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        PersonalQuestionAdapter adapter = new PersonalQuestionAdapter(PersonalCollectQuestionFragment.this.getActivity(),getDate());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailQuestion.actionStart(PersonalCollectQuestionFragment.this.getActivity(),position);
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

        MyPersonalQuestion myPersonalQuestion3 = new MyPersonalQuestion();
        myPersonalQuestion3.setState(true);
        myPersonalQuestion3.setDate("1111.11.11");
        myPersonalQuestion3.setTitle("啦啦啦啦1");
        myPersonalQuestion3.setContext("人的身份和书法家的护是大哥好地方挂机的高房价的高房价身符格式的风格是否退回");

        MyPersonalQuestion myPersonalQuestion4 = new MyPersonalQuestion();
        myPersonalQuestion4.setState(false);
        myPersonalQuestion4.setDate("2222.22.22");
        myPersonalQuestion4.setTitle("啦啦啦啦2");

        MyPersonalQuestion myPersonalQuestion5 = new MyPersonalQuestion();
        myPersonalQuestion5.setState(true);
        myPersonalQuestion5.setDate("1111.11.11");
        myPersonalQuestion5.setTitle("啦啦啦啦1");
        myPersonalQuestion5.setContext("人的身份和书法家的护是大哥好地方挂机的高房价的高房价身符格式的风格是否退回");

        MyPersonalQuestion myPersonalQuestion6 = new MyPersonalQuestion();
        myPersonalQuestion6.setState(false);
        myPersonalQuestion6.setDate("2222.22.22");
        myPersonalQuestion6.setTitle("啦啦啦啦2");

        list.add(myPersonalQuestion1);
        list.add(myPersonalQuestion2);
        list.add(myPersonalQuestion3);
        list.add(myPersonalQuestion4);
        list.add(myPersonalQuestion5);
        list.add(myPersonalQuestion6);

        return list;
    }
//-------------------接口方法-------------
    @Override
    public void updateView() {

    }
//----------------接口方法结束---------------
}
