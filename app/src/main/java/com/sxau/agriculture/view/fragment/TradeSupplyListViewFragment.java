package com.sxau.agriculture.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
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

import com.sxau.agriculture.adapter.TradeDemandAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ITrade;
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

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 信息专区ListView
 *
 * @author 田帅
 */


public class TradeSupplyListViewFragment extends BaseFragment implements ITradeListViewFragment, AdapterView.OnItemClickListener, View.OnTouchListener, RefreshLayout.OnRefreshListener {
    /**
     * 控件
     */
    private View mview;
    private ListView lv_Info;
    private ImageView iv_collection;
    private BaseAdapter adapter;
    /**
     * 浮动按钮
     */
    private float startX, startY, offsetX, offsetY; //计算触摸偏移量
    /**
     * 网络请求
     */
    private int currentPage;
    private String tradeType = "SUPPLY";
    /**
     * 下拉刷新上拉加载
     */
    private View footerLayout;
    private TextView tv_more;
    private RefreshLayout rl_refresh;
    private Handler handler;
    private boolean isLoadOver;
    /**
     * 实体类集合
     */
//    private List<TradeData> infoDatas = new ArrayList<TradeData>();
    private TradeData infoData;
    private List<TradeData> supplyDatas = new ArrayList<TradeData>();
    /**
     * 接口
     */
    private ITradeListViewPresenter iTradeListViewPresenter;

    /**
     * 这个不知道干嘛用的
     */
    public TradeSupplyListViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        /**
         * 绑定视图
         * */
        mview = inflater.inflate(R.layout.fragment_trade_listview, container, false);
        /**
         * 初始化控件
         * */
        lv_Info = (ListView) mview.findViewById(R.id.lv_info);
        iv_collection = (ImageView) mview.findViewById(R.id.iv_demand_collection);

        isLoadOver = false;
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    /**
                     * 下拉刷新
                     * */
                    case ConstantUtil.PULL_REFRESH:
                        currentPage = 1;
                        initInfoData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                        rl_refresh.setRefreshing(false);
                        adapter.notifyDataSetChanged();
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                        break;
                    /**
                     * 得到数据
                     * */
                    case ConstantUtil.GET_NET_DATA:
                        updateView();
                        if (isLoadOver) {
                            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
                        } else {
                            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                        }
                        break;
                    /**
                     * 点击加载
                     * */
                    case ConstantUtil.UP_LOAD:
                        currentPage++;
                        initInfoData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                        rl_refresh.setLoading(false);
                        break;
                    default:
                        break;
                }
            }
        };
        /**
         * 将InfoLvFragment与InfolvPresenter绑定
         * */
        iTradeListViewPresenter = new TradeListViewPresenter(TradeSupplyListViewFragment.this, TradeSupplyListViewFragment.this.getContext(), handler);
        /**
         * 请求数据
         * */
        currentPage = 1;
        initInfoData(String.valueOf(currentPage), String.valueOf(ConstantUtil.ITEM_NUMBER), true);
        supplyDatas = iTradeListViewPresenter.getSupplyDatas();
        /**
         * 配置适配器
         * */
        adapter = new TradeDemandAdapter(TradeSupplyListViewFragment.this.getActivity(), supplyDatas);
        lv_Info.setAdapter(adapter);
        /**
         * ListviewItem点击事件与浮动按钮动画效果
         * */
        lv_Info.setOnItemClickListener(this);
        lv_Info.setOnTouchListener(this);
        /**
         * 下拉刷新与加载
         * */
        rl_refresh = (RefreshLayout) mview.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        rl_refresh.setOnRefreshListener(this);
        footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);
        lv_Info.addFooterView(footerLayout);
        rl_refresh.setChildView(lv_Info);
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
        return mview;
    }
    /**
     * 初始化交易信息
     */
    public void initInfoData(String page, String pageSize, final boolean isRefresh) {
        iTradeListViewPresenter.doRequest(page, pageSize, isRefresh);
    }
    /**
     * Item事件
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra("TradeId", supplyDatas.get(position).getId());
        intent.setClass(TradeSupplyListViewFragment.this.getActivity(), TradeContentActivity.class);
        startActivity(intent);
    }
    //------------------接口方法-------------------
    @Override
    public void updateView() {
        /**
         * 从p层得到供应数据
         * */
        supplyDatas = iTradeListViewPresenter.getSupplyDatas();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void changeItemView() {
    }
    /**
     * 获取收藏的状态，是否已经收藏
     * 1代表已经收藏
     * 2代表没有收藏
     */
    @Override
    public int getCollectState() {
        return 0;
    }
    /**
     * 点击加载判断
     * */
    @Override
    public void isLoadOver(boolean isLoadover) {
        isLoadOver = isLoadover;
    }
    /**
     * 数据加载失败
     * */
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
    }
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
    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
    }
//----------------接口方法结束-------------------
}
