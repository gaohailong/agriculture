package com.sxau.agriculture.view.fragment;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 个人中心问题的listView的fragment
 *
 * @author 李秉龙
 */
public class PersonalQuestionFragment extends BaseFragment implements IPersonalQuestionFragment {
    private ListView listView;
    private TextView tv_nothing;

    private IPersonalQuestionPresenter iPersonalQuestionPresenter;
    private ArrayList<MyPersonalQuestion> mquestionslist;
    private PersonalQuestionAdapter adapter;
    private MyHandler myHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将Fragment与Presenter绑定
        iPersonalQuestionPresenter = new PersonalQuestionPresenter(PersonalQuestionFragment.this);

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        tv_nothing = (TextView) myQuestionView.findViewById(R.id.tv_nothing);
        mquestionslist = new ArrayList<MyPersonalQuestion>();
        myHandler = new MyHandler();
        return myQuestionView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    break;
                case ConstantUtil.GET_NET_DATA:
                    initListView();
                    break;
            }
        }
    }


    private void initListView() {
        //获取数据
        //测试一下，先请求网络数据，再更新页面

        mquestionslist = iPersonalQuestionPresenter.getDatas();
        if (mquestionslist.isEmpty()) {
            listView.setVisibility(View.GONE);
            tv_nothing.setVisibility(View.VISIBLE);
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
        iPersonalQuestionPresenter.doRequest();
    }

    //-----------------接口方法----------------
    //网络请求超时，请检查网络
    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalQuestionFragment.this.getActivity(), "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishPersonalQuestionPresenter() {

    }

    //提示没有网络
    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalQuestionFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(ArrayList<MyPersonalQuestion> myPersonalQuestions) {
        if (mquestionslist.isEmpty()) {
            listView.setVisibility(View.GONE);
            tv_nothing.setVisibility(View.VISIBLE);
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
//----------------接口方法结束------------------
}
