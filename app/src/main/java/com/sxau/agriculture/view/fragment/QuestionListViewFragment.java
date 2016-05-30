package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IQuestionList;
import com.sxau.agriculture.bean.Question;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.AskQuestion;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.QuestionListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IQuestionListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IQuestionListViewFragment;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问答页面的listView的fragment
 * @author 李秉龙
 */
public class QuestionListViewFragment extends BaseFragment implements IQuestionListViewFragment,AdapterView.OnItemClickListener,View.OnClickListener{
    private View mView;
    private ListView lvQuestionList;
    private Context context;
    private QuestionAdapter adapter;
    private MyHandler myHandler;
    private ArrayList<QuestionData> questionDatas;


    private IQuestionListViewPresenter iQuestionListViewPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将QuestionLvFragment与QuestionLvPresenter绑定
        iQuestionListViewPresenter = new QuestionListViewPresenter(QuestionListViewFragment.this);

        mView = inflater.inflate(R.layout.fragment_question_list, container, false);
        lvQuestionList = (ListView) mView.findViewById(R.id.lv_question);
        questionDatas=new ArrayList<QuestionData>();
        myHandler=new MyHandler();


        context=QuestionListViewFragment.this.getActivity();

        lvQuestionList.setOnItemClickListener(this);


        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDate();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    //button的点击事件
    @Override
    public void onClick(View v) {

    }


    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.INIT_DATA:
                    getQuestionData();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    initDate();
                    break;
                default:
                    break;
            }
        }
    }

    //网络请求方法
    public void getQuestionData(){
        Call<ArrayList<QuestionData>> call=RetrofitUtil.getRetrofit().create(IQuestionList.class).getQuestionList();
        call.enqueue(new Callback<ArrayList<QuestionData>>() {
            @Override
            public void onResponse(Response<ArrayList<QuestionData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    questionDatas = response.body();
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }

    /**
     *给listview填充数据
     */
    private void initDate() {

        adapter = new QuestionAdapter(context, questionDatas);
        lvQuestionList.setAdapter(adapter);

    }

    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailQuestion.actionStart(context, position);
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
