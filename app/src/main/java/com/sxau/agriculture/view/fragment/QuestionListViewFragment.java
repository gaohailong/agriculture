package com.sxau.agriculture.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.Question;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.QuestionListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IQuestionListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IQuestionListViewFragment;

/**
<<<<<<< HEAD
 * 问答页面的listView的fragment
 * 李秉龙
=======
 * Created by Administrator on 2016/4/13.
 *
>>>>>>> 191c6b3d66869a39634014e8597fb7d643371ba8
 */
public class QuestionListViewFragment extends BaseFragment implements IQuestionListViewFragment{
    private View mView;
    private ListView lvQuestionList;

    private IQuestionListViewPresenter iQuestionListViewPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将QuestionLvFragment与QuestionLvPresenter绑定
        iQuestionListViewPresenter = new QuestionListViewPresenter(QuestionListViewFragment.this);


        mView = inflater.inflate(R.layout.fragment_question_list, container, false);
        lvQuestionList = (ListView) mView.findViewById(R.id.lv_question);

        BaseAdapter adapter = new QuestionAdapter(QuestionListViewFragment.this.getActivity(), initDate());
        lvQuestionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailQuestion.actionStart(getContext(),position);
//                Intent intent = new Intent();
//                intent.setClass(QuestionListViewFragment.this.getActivity(), DetailQuestion.class);
//                intent.putExtra("indexPosition",position);
//                startActivity(intent);
            }
        });
        lvQuestionList.setAdapter(adapter);
        return mView;
    }

    /**
     * 数据源
     *
     * @return question类型的 questionDate对象
     */
    private Question[] initDate() {
        Question[] questionDate = new Question[2];

        questionDate[0] = new Question();
        questionDate[0].setState(true);
        questionDate[0].setHead(R.mipmap.img_default_user_portrait_150px);
        questionDate[0].setName("帅子1");
        questionDate[0].setTitle("lalala");
        questionDate[0].setContent("的可能非公开多久");

        questionDate[1] = new Question();
        questionDate[1].setState(false);
        questionDate[1].setHead(R.mipmap.img_default_user_portrait_150px);
        questionDate[1].setName("帅子2");
        questionDate[1].setTitle("lalala2");

        return questionDate;
    }

//------------------接口方法----------------
    @Override
    public void updateView() {

    }

    @Override
    public void changeItemView() {

    }

    /**
     * 获取催一下的状态
     * 1 为已经催
     * 0 为没有催
     * @return
     */
    @Override
    public int getUrgeState() {
        return 0;
    }

    /**
     * 获取收藏状态（赞一下）
     * 1 为已经收藏
     * 0 为没有收藏
     * @return
     */
    @Override
    public int getFavState() {
        return 0;
    }
//--------------接口方法结束---------------
}
