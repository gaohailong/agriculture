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
import com.sxau.agriculture.presenter.fragment_presenter.ExpertQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IExpertQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.fragment_interface.IExpertQuestionFragment;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * 指派给专家的问题fragment
 *
 * @author 李雅文
 */
public class ExpertQuestionFragment extends BaseFragment implements IExpertQuestionFragment {
    private ListView listView;
    private View emptyView;
    private RefreshLayout rl_refresh;
    private static MyHandler myHandler;

    private IExpertQuestionPresenter iExpertQuestionPresenter;
    private ArrayList<MyPersonalQuestion> mquestionslist;
    private PersonalQuestionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myHandler = new MyHandler(ExpertQuestionFragment.this);

        //将Fragment与Presenter绑定
        iExpertQuestionPresenter = new ExpertQuestionPresenter(ExpertQuestionFragment.this, ExpertQuestionFragment.this.getContext(), myHandler);

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

    private void initRefresh() {
        rl_refresh.setChildView(listView);
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iExpertQuestionPresenter.pullRefersh();
            }
        });
    }

    private void initListView() {
        //获取数据
        mquestionslist = iExpertQuestionPresenter.getDatas();
        if (mquestionslist.isEmpty()) {
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);

        } else {
            emptyView.setVisibility(View.GONE);

            adapter = new PersonalQuestionAdapter(ExpertQuestionFragment.this.getActivity(), mquestionslist);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (NetUtil.isNetAvailable(getActivity())) {
                        DetailQuestionActivity.actionStart(ExpertQuestionFragment.this.getActivity(), position);
                    } else {
                        Toast.makeText(getActivity(), "无网络连接,请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        if (iExpertQuestionPresenter.isNetAvailable()) {
            iExpertQuestionPresenter.doRequest();
        } else {
            showNoNetworking();
        }
    }

    public class MyHandler extends Handler {
        WeakReference<ExpertQuestionFragment> weakReference;

        public MyHandler(ExpertQuestionFragment fragment) {
            weakReference = new WeakReference<ExpertQuestionFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    mquestionslist = iExpertQuestionPresenter.getDatas();
                    updateView(mquestionslist);
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
        Toast.makeText(ExpertQuestionFragment.this.getActivity(), "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishPersonalQuestionPresenter() {

    }

    //提示没有网络
    @Override
    public void showNoNetworking() {
        Toast.makeText(ExpertQuestionFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(ArrayList<MyPersonalQuestion> myPersonalQuestions) {
        if (myPersonalQuestions.isEmpty()) {
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new PersonalQuestionAdapter(ExpertQuestionFragment.this.getActivity(), myPersonalQuestions);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (NetUtil.isNetAvailable(getActivity())){
                    DetailQuestionActivity.actionStart(ExpertQuestionFragment.this.getActivity(), mquestionslist.get(position).getId());
                    }else {
                        Toast.makeText(getActivity(),"无网络连接,请检查网络！",Toast.LENGTH_SHORT).show();
                    }
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
