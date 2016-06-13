package com.sxau.agriculture.view.fragment;


import android.graphics.Color;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * 个人中心问题的listView的fragment
 *
 * @author 李雅文
 */
public class PersonalQuestionFragment extends BaseFragment implements IPersonalQuestionFragment {
    private ListView listView;
    private View emptyView;
    private RefreshLayout rl_refresh;
    private static MyHandler myHandler;

    private IPersonalQuestionPresenter iPersonalQuestionPresenter;
    private ArrayList<MyPersonalQuestion> mquestionslist;
    private PersonalQuestionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myHandler = new MyHandler(PersonalQuestionFragment.this);

        //将Fragment与Presenter绑定
        iPersonalQuestionPresenter = new PersonalQuestionPresenter(PersonalQuestionFragment.this, PersonalQuestionFragment.this.getContext(), myHandler);

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
            rl_refresh = (RefreshLayout) myQuestionView.findViewById(R.id.srl_refresh);
            rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        mquestionslist = new ArrayList<MyPersonalQuestion>();

        emptyView = myQuestionView.findViewById(R.id.emptyView);

        return myQuestionView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        initListView();
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
        LogUtil.d("PersonalQuestion", "1、初始化View，获取数据");
        if (mquestionslist.isEmpty()) {
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);

        } else {
            emptyView.setVisibility(View.GONE);

            adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(), mquestionslist);
            listView.setAdapter(adapter);
            LogUtil.d("PersonalQuestion", "2、有数据初始化View");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailQuestionActivity.actionStart(PersonalQuestionFragment.this.getActivity(), position);
                }
            });
        }
        if (iPersonalQuestionPresenter.isNetAvailable()) {
            iPersonalQuestionPresenter.doRequest();
            LogUtil.d("PersonalQuestion", "3、发起请求，请求数据");
        } else {
            showNoNetworking();
        }
    }

    public class MyHandler extends Handler {
        WeakReference<PersonalQuestionFragment> weakReference;

        public MyHandler(PersonalQuestionFragment fragment) {
            weakReference = new WeakReference<PersonalQuestionFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    LogUtil.d("PersonalQuestion", "5、收到通知，数据已经更新，拿数据，更新页面，执行updateView方法");
                    mquestionslist = iPersonalQuestionPresenter.getDatas();
                    updateView(mquestionslist);
                    LogUtil.d("PersonalQuestionF", "insert");
                    break;
                default:
                    break;
            }
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
        LogUtil.d("PersonalQuestion", "6、updateView方法执行");
        if (myPersonalQuestions.isEmpty()) {
            LogUtil.d("PersonalQuestin", "7、仍然是空数据");
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);
        } else {
            LogUtil.d("PersonalQuestin", "8、成功拿到数据，更新页面");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(), myPersonalQuestions);
            listView.setAdapter(adapter);
            LogUtil.d("PersonalQuestionF", "2");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DetailQuestionActivity.actionStart(PersonalQuestionFragment.this.getActivity(), mquestionslist.get(position).getId());
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
