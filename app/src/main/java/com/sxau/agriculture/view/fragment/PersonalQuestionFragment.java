package com.sxau.agriculture.view.fragment;


import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.util.ArrayList;


/**
 * 个人中心问题的listView的fragment
 *
 * @author 李秉龙
 *
 * 目前存在的问题是当请求到空的数据时，这里空空的  页面无法显示。还有待解决
 */
public class PersonalQuestionFragment extends BaseFragment implements IPersonalQuestionFragment {
    private ListView listView;
    private TextView emptyView;
    private RefreshLayout rl_refresh;

    private IPersonalQuestionPresenter iPersonalQuestionPresenter;
    private ArrayList<MyPersonalQuestion> mquestionslist;
    private PersonalQuestionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将Fragment与Presenter绑定
        iPersonalQuestionPresenter = new PersonalQuestionPresenter(PersonalQuestionFragment.this, PersonalQuestionFragment.this.getContext());

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        rl_refresh = (RefreshLayout) myQuestionView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        mquestionslist = new ArrayList<MyPersonalQuestion>();

        emptyView = new TextView(PersonalQuestionFragment.this.getActivity());
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        emptyView.setText("这里空空的");
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) listView.getParent()).addView(emptyView);
        return myQuestionView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        initRefresh();
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
        rl_refresh.setChildView(listView);
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iPersonalQuestionPresenter.pullRefersh();
            }
        });
    }

    /**
     * 在初始化页面时，应该先读取缓存数据，将页面显示出来
     * 然后再去请求新的数据进行显示。
     */
    private void initListView() {
        //获取数据
        mquestionslist = iPersonalQuestionPresenter.getDatas();
        if (mquestionslist.isEmpty()) {
            listView.setEmptyView(emptyView);
        } else {
            adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(), mquestionslist);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailQuestion.actionStart(PersonalQuestionFragment.this.getActivity(), position);
                }
            });
        }
        if (iPersonalQuestionPresenter.isNetAvailable()) {
            iPersonalQuestionPresenter.doRequest();
        }else{
            showNoNetworking();
        }
    }


    //-----------------接口方法----------------
    //网络请求超时，请检查网络
    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalQuestionFragment.this.getActivity(), "请求超时，请检查网络", Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishPersonalQuestionPresenter() {

    }

    //提示没有网络
    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalQuestionFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateView(ArrayList<MyPersonalQuestion> myPersonalQuestions) {
        if (mquestionslist.isEmpty()) {
            listView.setEmptyView(emptyView);
        } else {
            adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(), mquestionslist);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailQuestion.actionStart(PersonalQuestionFragment.this.getActivity(), position);
                }
            });
        }
    }

    @Override
    public void closeRefresh() {
        rl_refresh.setRefreshing(false);
    }
//----------------接口方法结束------------------
}
