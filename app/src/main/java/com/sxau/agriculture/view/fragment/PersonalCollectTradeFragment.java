package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalCollectTradesAdapter;
import com.sxau.agriculture.adapter.PersonalTradeInfoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalCollectTradePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectTradePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.fragment_interface.IPersonalCollectTradeFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * 个人中心我的交易的listView的fragment
 * @author 李秉龙
 */
public class PersonalCollectTradeFragment extends BaseFragment implements IPersonalCollectTradeFragment{
    private ListView listView;
    private IPersonalCollectTradePresenter iPersonalCollectTradePresenter;
    private ArrayList<MyPersonalCollectTrades> mCollectTradeList;
    private static Myhandler myhandler;
    private RefreshLayout rl_refresh;
    private View emptyView ;
    private PersonalCollectTradesAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myhandler = new Myhandler(PersonalCollectTradeFragment.this);

        //将Fragment对象与Presenter对象绑定
        iPersonalCollectTradePresenter = new PersonalCollectTradePresenter(PersonalCollectTradeFragment.this,  PersonalCollectTradeFragment.this.getContext(),myhandler);

        View TradeInfoView = inflater.inflate(R.layout.frament_personal_tradeinfo,null);
        emptyView = TradeInfoView.findViewById(R.id.emptyView);
        rl_refresh = (RefreshLayout) TradeInfoView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView  = (ListView) TradeInfoView.findViewById(R.id.lv_tradeInfo);
        mCollectTradeList = new ArrayList<MyPersonalCollectTrades>();
        return TradeInfoView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        initListView();
    }

    private void initListView() {
        mCollectTradeList= iPersonalCollectTradePresenter.getDatas();
        LogUtil.d("PersonalCollectionTradeF", "1、初始化View，获取数据");
        if (mCollectTradeList.isEmpty()){
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }else {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter =new PersonalCollectTradesAdapter(PersonalCollectTradeFragment.this.getActivity(),mCollectTradeList);
            LogUtil.d("PersonalCollectionTradeF", "2、有数据初始化View");
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TradeContentActivity.actionStart(PersonalCollectTradeFragment.this.getActivity());
            }
        });
        if (iPersonalCollectTradePresenter.isNetAvailable()){
            iPersonalCollectTradePresenter.doRequest();
            LogUtil.d("PersonalCollection", "3、发起请求，请求数据");
        }else {
            showNoNetworking();
        }
    }

    /**
     * 初始化下拉刷新
     */
    private void initRefresh() {
        rl_refresh.setChildView(listView);
        rl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iPersonalCollectTradePresenter.pullRefersh();
            }
        });
    }

    public  class Myhandler extends Handler{
        WeakReference<PersonalCollectTradeFragment> weakRefersence;
        public Myhandler(PersonalCollectTradeFragment fragment) {
            weakRefersence =new  WeakReference<PersonalCollectTradeFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.GET_NET_DATA:
                    LogUtil.d("PersonalCollectTradeF", "5、收到通知，数据已经更新，拿数据，更新页面，执行updateView方法");
                    mCollectTradeList =  iPersonalCollectTradePresenter.getDatas();
                    updateView(mCollectTradeList);
                    break;
                default:
                    break;
            }
        }
    }




    //---------------------接口方法-----------------
    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalCollectTradeFragment.this.getActivity(), "请求超时，请检查网络", Toast.LENGTH_LONG).show();
    }
    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalCollectTradeFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_LONG).show();
    }
    @Override
    public void updateView(ArrayList<MyPersonalCollectTrades> myPersonalCollectTrades) {
        LogUtil.d("PersonalCollectTradeF", "6、updateView方法执行");
        if (myPersonalCollectTrades.isEmpty()){
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            LogUtil.d("PersonalCollectTradeF", "7、仍然是空数据");
        }else {
            LogUtil.d("PersonalCollectTradeF", "8、成功拿到数据，更新页面");
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adapter = new PersonalCollectTradesAdapter(PersonalCollectTradeFragment.this.getActivity(),myPersonalCollectTrades);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TradeContentActivity.actionStart(PersonalCollectTradeFragment.this.getActivity());
                }
            });
        }

    }

    @Override
    public void closeRefresh() {
        rl_refresh.setRefreshing(false);
    }
//-------------------接口方法结束-----------------
}
