package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sxau.agriculture.adapter.TradeListViewAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;

import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.view.activity.TradeContentActivity;

import com.sxau.agriculture.presenter.fragment_presenter.TradeListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.ITradeListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.ITradeListViewFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息ListView
 *
 * @author 田帅
 */


public class TradeDemandListViewFragment extends BaseFragment implements ITradeListViewFragment, AdapterView.OnItemClickListener, View.OnTouchListener {

    private View mview;
    private ListView lv_Info;
    private ImageView iv_collection;
    private View footerLayout;
    private TextView tv_more;

    private BaseAdapter adapter;

    private float startX, startY, offsetX, offsetY; //计算触摸偏移量
    private int currentPage;
    private RefreshLayout rl_refresh;
    private MyHandler handler;
    private boolean isLoadOver;
    private Context context;

//    private TradeData infoData;
    private ArrayList<TradeData> demandDatas = new ArrayList<TradeData>();

    private ITradeListViewPresenter iTradeListViewPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        handler = new MyHandler();
        context=TradeDemandListViewFragment.this.getActivity();
        iTradeListViewPresenter = new TradeListViewPresenter(TradeDemandListViewFragment.this, context, handler);

        mview = inflater.inflate(R.layout.fragment_trade_listview, container, false);
        lv_Info = (ListView) mview.findViewById(R.id.lv_info);
        iv_collection = (ImageView) mview.findViewById(R.id.iv_demand_collection);
        rl_refresh = (RefreshLayout) mview.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));

        lv_Info.setOnItemClickListener(this);

        adapter = new TradeListViewAdapter(TradeDemandListViewFragment.this.getActivity(), demandDatas);
        lv_Info.setAdapter(adapter);

        iTradeListViewPresenter.doRequest();

//        rl_refresh.setOnRefreshListener(this);

//        lv_Info.setOnTouchListener(this);

 /*       footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);
        lv_Info.addFooterView(footerLayout);
        rl_refresh.setChildView(lv_Info);*/
//        initInfoData(String.valueOf(currentPage), String.valueOf(ConstantUtil.ITEM_NUMBER), true);
//        isLoadOver = false;
//        demandDatas = iTradeListViewPresenter.getDemandDatas();

//        currentPage = 1;
      /*  tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });*/
        return mview;
    }

 /*   public void initInfoData(String page, String pageSize, final boolean isRefresh) {
        iTradeListViewPresenter.doRequest(page, pageSize, isRefresh);
    }
*/
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
     /*   Intent intent = new Intent();
        intent.putExtra("TradeId", demandDatas.get(position).getId());
        intent.setClass(TradeDemandListViewFragment.this.getActivity(), TradeContentActivity.class);
        startActivity(intent);*/
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    demandDatas = iTradeListViewPresenter.getDemandDatas();
                    updateView(demandDatas);
              /*      if (isLoadOver) {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
                    } else {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    }*/
                    break;
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
//                    initInfoData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                    rl_refresh.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
//                    initInfoData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                    rl_refresh.setLoading(false);
                    break;
                default:
                    break;
            }
        }
    }

    ;

    //------------------接口方法-------------------

    @Override
    public void updateView(ArrayList<TradeData> demandDatas) {
        if (demandDatas == null) {
//            lv_Info.setEmptyView(emptyView);
//            lv_Info.setVisibility(View.GONE);
//            currentPage = 1;
//            iTradeListViewPresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
        } else {
//            emptyView.setVisibility(View.GONE);
//            lv_Info.setVisibility(View.VISIBLE);
            Log.e("demandDatas",demandDatas.size()+"");
            adapter = new TradeListViewAdapter(context, demandDatas);
            lv_Info.setAdapter(adapter);
        }
    }

 /*   @Override
    public void changeItemView() {
    }

    *//**
     * 获取收藏的状态，是否已经收藏
     * 1代表已经收藏
     * 2代表没有收藏
     *//*
    @Override
    public int getCollectState() {
        return 0;
    }

    *//**
     * 点击加载判断
     *//*
    @Override
    public void isLoadOver(boolean isLoadover) {
        isLoadOver = isLoadover;
    }

    *//**
     * 数据加载失败
     *//*
    @Override
    public void onFailure() {
        tv_more.setText("数据加载失败");
        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_FAIL);
        if (currentPage > 1) {
            rl_refresh.setRefreshing(false);
            currentPage--;
        } else {
            rl_refresh.setRefreshing(false);
        }
    }*/

    /**
     * 实现滑动屏幕隐藏浮动按钮和显示按钮效果
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                offsetX = event.getX() - startX;
                offsetY = event.getY() - startY;

                if (offsetY < 0) {
                    AlphaAnimation aa = new AlphaAnimation(1.0f, 0f);
                    aa.setDuration(500);
                    new TradeFragment().btn_info_float.setAnimation(aa);
                    new TradeFragment().btn_info_float.setVisibility(View.INVISIBLE);
                }
                if (offsetY > 0) {
                    AlphaAnimation aa = new AlphaAnimation(0f, 1.0f);
                    aa.setDuration(500);
                    new TradeFragment().btn_info_float.setAnimation(aa);
                    new TradeFragment().btn_info_float.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        return false;
    }

  /*  *//**
     * 下拉刷新
     *//*
    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
    }*/
//----------------接口方法结束-------------------
}
