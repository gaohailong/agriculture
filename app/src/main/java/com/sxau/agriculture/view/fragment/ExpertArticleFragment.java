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

import com.sxau.agriculture.adapter.ExpertArticlesAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.presenter.fragment_presenter.ExpertArticlePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IExpertArticlePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.view.activity.WebViewActivity;
import com.sxau.agriculture.view.fragment_interface.IExpertArticleFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 专家个人中心——我的文章fragment
 *
 * @author Yawen_Li
 */
public class ExpertArticleFragment extends BaseFragment implements IExpertArticleFragment{
    private ListView listView;
    private ArrayList<HomeArticle> expertArticleList;
    private View emptyView;
    private RefreshLayout rl_refresh;
    private IExpertArticlePresenter iExpertArticlePresenter;
    private ExpertArticlesAdapter adapter;
    private static MyHandler myHandler;
    private Context context;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myHandler = new MyHandler(ExpertArticleFragment.this);
        //将Fragment对象与Presenter对象绑定
        iExpertArticlePresenter = new ExpertArticlePresenter(ExpertArticleFragment.this,ExpertArticleFragment.this.getContext(),myHandler);

        context=ExpertArticleFragment.this.getActivity();
        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        rl_refresh = (RefreshLayout) myQuestionView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        expertArticleList = new ArrayList<HomeArticle>();
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
                iExpertArticlePresenter.pullRefersh();
            }
        });
    }

    private void initListView(){

        LogUtil.d("pcqf", "1、初始化View，获取数据");
        expertArticleList = iExpertArticlePresenter.getDatas();
        if (expertArticleList.isEmpty()){
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);
        }else {
            LogUtil.d("pcqf", "2、有数据初始化View");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new ExpertArticlesAdapter(expertArticleList,ExpertArticleFragment.this.getActivity());
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   if (NetUtil.isNetAvailable(getActivity())){
                       Intent intent = new Intent();
                       Bundle bundle = new Bundle();
                       bundle.putSerializable("ArticleData", expertArticleList.get(position));
                       intent.putExtras(bundle);
                       intent.setClass(context, WebViewActivity.class);
                       startActivity(intent);
                   }else {
                       Toast.makeText(getContext(),"无网络连接,请检查网络！",Toast.LENGTH_SHORT).show();
                   }

                }
            });
        }
        if (iExpertArticlePresenter.isNetAvailable()) {
            LogUtil.d("pcqf", "3、发起请求，请求数据");
            iExpertArticlePresenter.doRequest();
        } else {
            showNoNetworking();
        }


    }

    public class MyHandler extends Handler {
        WeakReference<ExpertArticleFragment> weakReference;

        public MyHandler(ExpertArticleFragment fragment) {
            weakReference = new WeakReference<ExpertArticleFragment>(fragment);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    expertArticleList = iExpertArticlePresenter.getDatas();
                    Log.d("pcqf","拿到数据");
                    updateView(expertArticleList);
                    break;
                default:
                    break;
            }
        }
    }
//-------------------接口方法-------------
@Override
public void showRequestTimeout() {
    Toast.makeText(ExpertArticleFragment.this.getContext(), "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
}

    @Override
    public void finishPersonalQuestionPresenter() {

    }

    //提示没有网络
    @Override
    public void showNoNetworking() {
        Toast.makeText(ExpertArticleFragment.this.getContext(), "没有网络连接，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(final ArrayList<HomeArticle> expertArticles) {
        LogUtil.d("pcqf", "6、updateView方法执行");
        if (expertArticles.isEmpty()) {
            LogUtil.d("pcqf", "7、仍然是空数据");
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        } else {
            LogUtil.d("pcqf", "8、成功拿到数据，更新页面");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new ExpertArticlesAdapter(expertArticles,ExpertArticleFragment.this.getActivity());
            listView.setAdapter(adapter);
            LogUtil.d("pcqf", "2");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(NetUtil.isNetAvailable(getActivity())){
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ArticleData", expertArticleList.get(position));
                        intent.putExtras(bundle);
                        intent.setClass(context, WebViewActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getContext(),"无网络连接,请检查网络！",Toast.LENGTH_SHORT).show();
                    }
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
