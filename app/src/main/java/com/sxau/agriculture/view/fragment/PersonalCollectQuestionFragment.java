package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalCollectQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalCollectionQuestionsPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;

import com.sxau.agriculture.view.fragment_interface.IPresonalCollectQuestionFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 个人中心我收藏的问题的listView的fragment
 * @author 李秉龙
 */
public class PersonalCollectQuestionFragment  extends BaseFragment implements IPresonalCollectQuestionFragment{
    private ListView listView;
    private ArrayList<MyPersonalCollectionQuestion> mquestionslist;
    private View emptyView;
    private RefreshLayout rl_refresh;
    private IPersonalCollectQuestionPresenter iPersonalCollectQuestionPresenter;
    private PersonalCollectQuestionAdapter adapter;
    private static MyHandler myHandler;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myHandler = new MyHandler(PersonalCollectQuestionFragment.this);
        //将Fragment对象与Presenter对象绑定
        iPersonalCollectQuestionPresenter = new PersonalCollectionQuestionsPresenter(PersonalCollectQuestionFragment.this,PersonalCollectQuestionFragment.this.getContext(),myHandler);

        context=PersonalCollectQuestionFragment.this.getActivity();
        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        rl_refresh = (RefreshLayout) myQuestionView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        mquestionslist = new ArrayList<MyPersonalCollectionQuestion>();
        emptyView = myQuestionView.findViewById(R.id.emptyView);
        return myQuestionView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        initListView();
        Log.d("cq","onviewCreat");

    }

    private void initRefresh() {
        rl_refresh.setChildView(listView);
        Log.d("pcqf:","下拉刷新");
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iPersonalCollectQuestionPresenter.pullRefersh();
            }
        });
    }

    private void initListView(){

        LogUtil.d("pcqf", "1、初始化View，获取数据");
        mquestionslist = iPersonalCollectQuestionPresenter.getDatas();
        if (mquestionslist.isEmpty()){
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);
        }else {
            LogUtil.d("pcqf", "2、有数据初始化View");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new PersonalCollectQuestionAdapter(PersonalCollectQuestionFragment.this.getActivity(),mquestionslist);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(context,DetailQuestionActivity.class);
                    intent.putExtra("ItemId",id);
                    startActivity(intent);
                }
            });
        }
        if (iPersonalCollectQuestionPresenter.isNetAvailable()) {
            LogUtil.d("pcqf", "3、发起请求，请求数据");
            iPersonalCollectQuestionPresenter.doRequest();
        } else {
            showNoNetworking();
        }


    }

    public class MyHandler extends Handler {
        WeakReference<PersonalCollectQuestionFragment> weakReference;

        public MyHandler(PersonalCollectQuestionFragment fragment) {
            weakReference = new WeakReference<PersonalCollectQuestionFragment>(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    mquestionslist = iPersonalCollectQuestionPresenter.getDatas();
                    Log.d("pcqf","拿到数据");
                    updateView(mquestionslist);
                    break;
                default:
                    break;
            }
        }
    }
//-------------------接口方法-------------
@Override
public void showRequestTimeout() {
    Toast.makeText(PersonalCollectQuestionFragment.this.getActivity(), "请求超时，请检查网络", Toast.LENGTH_LONG).show();
}

    @Override
    public void finishPersonalQuestionPresenter() {

    }

    //提示没有网络
    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalCollectQuestionFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateView(ArrayList<MyPersonalCollectionQuestion> myPersonalQuestions) {
        LogUtil.d("pcqf", "6、updateView方法执行");
        if (myPersonalQuestions.isEmpty()) {
            LogUtil.d("pcqf", "7、仍然是空数据");
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            LogUtil.d("pcqf", "8、成功拿到数据，更新页面");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new PersonalCollectQuestionAdapter(PersonalCollectQuestionFragment.this.getActivity(), myPersonalQuestions);
            listView.setAdapter(adapter);
            LogUtil.d("pcqf", "2");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(context,DetailQuestionActivity.class);
                    intent.putExtra("ItemId",id);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void closeRefresh() {
        rl_refresh.setRefreshing(false);
    }
//----------------接口方法结束---------------
}
