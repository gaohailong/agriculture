package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.ITrade;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.presenter.fragment_presenter_interface.ITradeListViewPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.view.fragment_interface.ITradeListViewFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class TradeListViewPresenter implements ITradeListViewPresenter {

    private ITradeListViewFragment iInfoListViewFragment;
    private Context context;
    private Handler handler;
    private ArrayList<TradeData> tradeDatasList = new ArrayList<TradeData>();
    /**
     * 缓存
     */
    private ArrayList<TradeData> supplyCacheDatas = new ArrayList<TradeData>();
    private ArrayList<TradeData> demandCacheDatas = new ArrayList<TradeData>();

    private ArrayList<TradeData> supplyDatas = new ArrayList<>();
    private TradeData tradeData;
    private String tradeTypeSupply = "SUPPLY";
    private ACache mCache;

    public TradeListViewPresenter(ITradeListViewFragment iInfoListViewFragment, Context context, Handler handler) {
        this.iInfoListViewFragment = iInfoListViewFragment;
        this.context = context;
        this.handler = handler;
        this.mCache = ACache.get(context);
    }


    //---------------接口方法-------------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public void setCollectState() {

    }

    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }

    /**
     * 网络请求数据
     */
    @Override
    public void doRequest(String page, String pageSize, final boolean isRefresh) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ConstantUtil.BASE_URL)
                .build();
        Call<ArrayList<TradeData>> call = retrofit.create(ITrade.class).getInfoTrade(page, pageSize);
        call.enqueue(new Callback<ArrayList<TradeData>>() {
            @Override
            public void onResponse(Response<ArrayList<TradeData>> response, Retrofit retrofit) {
                Log.e("responseCode", response.code()+"") ;
                if (response.isSuccess()) {
                    ArrayList<TradeData> responseDatas = response.body();
                    /**
                     * 下拉刷新
                     * */
                    if (isRefresh) {
                        tradeDatasList.clear();
                        supplyCacheDatas.clear();
                        supplyDatas.clear();
                        tradeDatasList.addAll(responseDatas);
                        iInfoListViewFragment.isLoadOver(false);
                    } else {
                        tradeDatasList.clear();
                        tradeDatasList.addAll(responseDatas);
                    }
                    /**
                     * 供应需求分类
                     * */
                    for (int i = 0; i < tradeDatasList.size(); i++) {
                        tradeData = tradeDatasList.get(i);
                        if (tradeTypeSupply.equals(tradeData.getTradeType())) {
                            supplyCacheDatas.add(tradeData);
                        } else {
                            demandCacheDatas.add(tradeData);
                        }
                    }
                    //清空缓存
                    mCache.remove(ConstantUtil.CACHE_TRADESUPPLY_KEY);
                    mCache.remove(ConstantUtil.CACHE_TRADEDEMAND_KEY);
                    //给缓存加数据
                    mCache.put(ConstantUtil.CACHE_TRADESUPPLY_KEY, supplyCacheDatas);
                    mCache.put(ConstantUtil.CACHE_TRADEDEMAND_KEY, demandCacheDatas);
                    Log.d("TradeSupplyListView", "4、请求成功，通知主线程重新拿数据，更新界面");
                    Log.d("TradeSupplyListView",supplyCacheDatas.size()+"");
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    if (responseDatas.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        iInfoListViewFragment.isLoadOver(true);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                iInfoListViewFragment.onFailure();
            }
        });
    }

    @Override
    public ArrayList<TradeData> getSupplyDatas() {
        supplyDatas = new ArrayList<TradeData>();

        if (mCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY) != null) {
            supplyDatas = (ArrayList<TradeData>) mCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY);
            return supplyDatas;
        } else {
            //缓存为空。直接返回 内容 为空 的mQuestionList
            return supplyDatas;
        }
    }

    @Override
    public ArrayList<TradeData> getDemandDatas() {
        return demandCacheDatas;
    }

    @Override
    public boolean isNetAvailable() {
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }
//-------------------接口方法结束------------------
}
