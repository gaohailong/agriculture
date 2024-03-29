package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.TradeListViewAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.presenter.fragment_presenter.TradeListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.ITradeListViewPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.fragment_interface.ITradeListViewFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 信息供应专区ListView
 * 问题：1.每次都在不停的new adapter
 *
 * @author 田帅
 */

public class TradeSupplyListViewFragment extends BaseFragment implements ITradeListViewFragment, AdapterView.OnItemClickListener, View.OnTouchListener {

    private View mView;
    private ListView lv_Info;
    private ImageView iv_collection;
    private TradeListViewAdapter tradeListViewAdapter;
    private View emptyView;
    private float startX, startY, offsetX, offsetY; //计算触摸偏移量
    private View footerLayout;
    private TextView tv_more;
    private RefreshLayout rl_refresh;
    private boolean isLoadOver;
    private ArrayList<TradeData> supplyDatas;
    private ITradeListViewPresenter iTradeListViewPresenter;
    private MyHandler handler;
    private Context context;
    private int currentPage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {

            context = TradeSupplyListViewFragment.this.getActivity();
            handler = new MyHandler(TradeSupplyListViewFragment.this);
            iTradeListViewPresenter = new TradeListViewPresenter(TradeSupplyListViewFragment.this, context, handler);
            currentPage = 1;
            supplyDatas = new ArrayList<TradeData>();

            mView = inflater.inflate(R.layout.fragment_trade_listview, container, false);
            lv_Info = (ListView) mView.findViewById(R.id.lv_info);
            rl_refresh = (RefreshLayout) mView.findViewById(R.id.srl_refresh);
            rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
            footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
            tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);
//        iv_collection = (ImageView) mView.findViewById(R.id.iv_demand_collection);
            emptyView = mView.findViewById(R.id.emptyView);
            lv_Info.setOnItemClickListener(this);
            lv_Info.setOnTouchListener(this);

            initRefresh();
            initListView();
            handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOADINDG);
        }
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    public void initRefresh() {
        lv_Info.addFooterView(footerLayout);
        rl_refresh.setChildView(lv_Info);
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
            }
        });

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
    }

    public void initListView() {
        tradeListViewAdapter = new TradeListViewAdapter(context, supplyDatas);
        lv_Info.setAdapter(tradeListViewAdapter);
    }

    public class MyHandler extends Handler {
        WeakReference<TradeSupplyListViewFragment> tradeSupplyListViewFragment;

        public MyHandler(TradeSupplyListViewFragment fragment) {
            tradeSupplyListViewFragment = new WeakReference<TradeSupplyListViewFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    currentPage = 1;
                    if (NetUtil.isNetAvailable(context)) {
                        iTradeListViewPresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true, "SUPPLY");
                    } else {
                        Toast.makeText(context, "没有网络连接", Toast.LENGTH_SHORT).show();
                        supplyDatas = iTradeListViewPresenter.getSupplyDatas();
                    }
                    break;
                case ConstantUtil.GET_NET_DATA:
                    updateView(iTradeListViewPresenter.getSupplyDatas());
                    break;
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
                    iTradeListViewPresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true, "SUPPLY");
                    rl_refresh.setRefreshing(false);
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    iTradeListViewPresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false, "SUPPLY");
                    rl_refresh.setLoading(false);
                    break;
                case ConstantUtil.LOAD_FAIL:
                    if (currentPage > 1) {
                        rl_refresh.setRefreshing(false);
                        currentPage--;
                    } else {
                        rl_refresh.setRefreshing(false);
                    }
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_FAIL);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void updateView(ArrayList<TradeData> supplyDataGet) {
        supplyDatas.clear();
        supplyDatas.addAll(supplyDataGet);
        if (supplyDatas.isEmpty()) {
            lv_Info.setEmptyView(emptyView);
            lv_Info.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            lv_Info.setVisibility(View.VISIBLE);
            tradeListViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void isLoadOver(boolean isLoadover) {
        if (isLoadover) {
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
        } else {
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (supplyDatas.size() > 0) {
            try {
                Intent intent = new Intent();
                intent.putExtra("TradeId", supplyDatas.get(position).getId());
                intent.setClass(TradeSupplyListViewFragment.this.getActivity(), TradeContentActivity.class);
                startActivity(intent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    //提示没有网络
    //------------------接口方法-------------------

  /*  @Override
    public void changeItemView() {
    }
    */

    /**
     * 获取收藏的状态，是否已经收藏
     * 1代表已经收藏
     * 2代表没有收藏
     *//*
    @Override
    public int getCollectState() {
        return 0;
    }
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
//----------------接口方法结束-------------------
}
