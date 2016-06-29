package com.sxau.agriculture.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.PersonalTradeInfoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalTradeInfoPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalTradeInfoPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.fragment_interface.IPersonalTradeInfoFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 个人中心交易的listView的fragment
 *
 * @author 李秉龙
 */
public class PersonalTradeInfoFragment extends BaseFragment implements IPersonalTradeInfoFragment {
    private ListView listView;
    private RefreshLayout rl_refresh;
    private IPersonalTradeInfoPresenter iPersonalTradeInfoPresenter;
    private PersonalTradeInfoAdapter adapter;
    private ArrayList<MyPersonalTrade> myTradesList;
    private View emptyView;
    private static MyHandler myHandler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myHandler = new MyHandler(PersonalTradeInfoFragment.this);
        //将Fragment与Presenter绑定
        iPersonalTradeInfoPresenter = new PersonalTradeInfoPresenter(PersonalTradeInfoFragment.this, PersonalTradeInfoFragment.this.getContext(), myHandler);

        View tradeInfoView = inflater.inflate(R.layout.frament_personal_tradeinfo, null);
        rl_refresh = (RefreshLayout) tradeInfoView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        listView = (ListView) tradeInfoView.findViewById(R.id.lv_tradeInfo);
        myTradesList = new ArrayList<MyPersonalTrade>();

        emptyView = tradeInfoView.findViewById(R.id.emptyView);

        return tradeInfoView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        initListView();
    }

    private void initListView() {
        LogUtil.d("PersonalTrade", "1、初始化View，获取数据");
        myTradesList = iPersonalTradeInfoPresenter.getDate();
        if (myTradesList.isEmpty()) {

            listView.setVisibility(View.GONE);
            listView.setEmptyView(emptyView);

        } else {
            emptyView.setVisibility(View.GONE);
            adapter = new PersonalTradeInfoAdapter(PersonalTradeInfoFragment.this.getActivity(), myTradesList);
            listView.setAdapter(adapter);
            LogUtil.d("PersonalTrade", "2、有数据初始化View");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (NetUtil.isNetAvailable(getActivity())) {
                        TradeContentActivity.actionStart(PersonalTradeInfoFragment.this.getActivity(), myTradesList.get(position).getId(), false);
                    } else {
                        Toast.makeText(getActivity(), "无网络连接,请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        if (iPersonalTradeInfoPresenter.isNetAvailable()) {
            iPersonalTradeInfoPresenter.doRequest();
            LogUtil.d("PersonalTrade", "3、发起请求，请求数据");
        } else {
            showNoNetworking();
        }

    }

    private void initRefresh() {
        rl_refresh.setChildView(listView);
        rl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                iPersonalTradeInfoPresenter.pullRefersh();
            }
        });
    }

    public class MyHandler extends Handler {

        WeakReference<PersonalTradeInfoFragment> weakReference;

        public MyHandler(PersonalTradeInfoFragment fragment) {
            weakReference = new WeakReference<PersonalTradeInfoFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    LogUtil.d("PersonalTrade", "5、收到通知，数据已经更新，拿数据，更新页面，执行updateView方法");
                    myTradesList = iPersonalTradeInfoPresenter.getDate();
                    updateView(myTradesList);
                    break;
                default:
                    break;
            }
        }
    }

    //----------------------接口方法---------------------
    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalTradeInfoFragment.this.getContext(), "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishPersonalQuestionPresenter() {
    }

    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalTradeInfoFragment.this.getActivity(), "没有网络连接，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateView(ArrayList<MyPersonalTrade> myPersonalTrades) {
        if (myPersonalTrades.isEmpty()) {
            listView.setEmptyView(emptyView);
            listView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adapter = new PersonalTradeInfoAdapter(PersonalTradeInfoFragment.this.getActivity(), myPersonalTrades);
            listView.setAdapter(adapter);
            LogUtil.d("PersonalTrade", "2");

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (NetUtil.isNetAvailable(getActivity())) {
                        TradeContentActivity.actionStart(PersonalTradeInfoFragment.this.getActivity(), myTradesList.get(position).getId(), false);
                    } else {
                        Toast.makeText(getActivity(), "无网络连接,请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void closeRefresh() {
        rl_refresh.setRefreshing(false);
    }


//---------------------接口方法结束----------------------
}
