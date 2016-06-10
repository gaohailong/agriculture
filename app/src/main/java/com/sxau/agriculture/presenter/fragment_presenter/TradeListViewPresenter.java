package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sxau.agriculture.api.ITrade;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.presenter.fragment_presenter_interface.ITradeListViewPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment_interface.ITradeListViewFragment;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 交易的listview
 *问题;缓存有问题
 * @author 高海龙
 */
public class TradeListViewPresenter implements ITradeListViewPresenter {
    private Context context;
    private Handler myHandler;
    private ITradeListViewFragment iInfoListViewFragment;
    private ArrayList<TradeData> tradeDatasList = new ArrayList<TradeData>();//获取所有的数据
    private ArrayList<TradeData> demandDatasList = new ArrayList<TradeData>();//获取需求的数据
    private ArrayList<TradeData> supplyDatasList = new ArrayList<TradeData>();//获取供应的数据
    private ACache aCache;

    public TradeListViewPresenter(ITradeListViewFragment iInfoListViewFragment, Context context, Handler myHandler) {
        this.iInfoListViewFragment = iInfoListViewFragment;
        this.context = context;
        this.myHandler = myHandler;
        aCache = ACache.get(context);
    }

    @Override
    public void doRequest(String page, String pageSize, final boolean isRefresh) {
        Call<ArrayList<TradeData>> call = RetrofitUtil.getRetrofit().create(ITrade.class).getInfoTrade(page, pageSize);
        call.enqueue(new Callback<ArrayList<TradeData>>() {
            @Override
            public void onResponse(Response<ArrayList<TradeData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<TradeData> tradeDatas = response.body();
                    if (isRefresh) {
                        tradeDatasList.clear();
                        demandDatasList.clear();
                        supplyDatasList.clear();
                        tradeDatasList.addAll(tradeDatas);
                    } else {
                        tradeDatasList.addAll(tradeDatas);
                    }
                    aCache.remove(ConstantUtil.CACHE_TRADESUPPLY_KEY);
                    aCache.remove(ConstantUtil.CACHE_TRADEDEMAND_KEY);
                    for (int i = 0; i < tradeDatas.size(); i++) {
                        if (tradeDatas.get(i).getTradeType().equals("SUPPLY")) {
                            supplyDatasList.add(tradeDatas.get(i));
                        } else if (tradeDatas.get(i).getTradeType().equals("DEMAND")) {
                            demandDatasList.add(tradeDatas.get(i));
                        }
                    }
                    int s = supplyDatasList.size();
                    int s2 = demandDatasList.size();
                    Log.e("supplyDatasList", s + "");
                    Log.e("demandDatasList", s2 + "");
                    aCache.put(ConstantUtil.CACHE_TRADESUPPLY_KEY, supplyDatasList);
                    aCache.put(ConstantUtil.CACHE_TRADEDEMAND_KEY, demandDatasList);
                    if (tradeDatas.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        iInfoListViewFragment.isLoadOver(true);
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                myHandler.sendEmptyMessage(ConstantUtil.LOAD_FAIL);
            }
        });


      /*  Call<ArrayList<TradeData>> call = retrofit.create(ITrade.class).getInfoTrade(page, pageSize);
        call.enqueue(new Callback<ArrayList<TradeData>>() {
            @Override
            public void onResponse(Response<ArrayList<TradeData>> response, Retrofit retrofit) {
                Log.e("responseCode", response.code() + "");
                if (response.isSuccess()) { *//*
                    ArrayList<TradeData> responseDatas = response.body();
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
                    Log.d("TradeSupplyListView", supplyCacheDatas.size() + "");
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    if (responseDatas.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        iInfoListViewFragment.isLoadOver(true);
                    }*//*
                }
            }

            @Override
            public void onFailure(Throwable t) {
//                iInfoListViewFragment.onFailure();
            }
        });*/
    }

    @Override
    public ArrayList<TradeData> getDemandDatas() {
        ArrayList<TradeData> supplyDatas = new ArrayList<TradeData>();
        if (aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY) != null) {
            supplyDatas = (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADEDEMAND_KEY);
            return supplyDatas;
        } else {
            //缓存为空。直接返回 内容 为空 的mQuestionList
            return supplyDatas;
        }

//        return (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADEDEMAND_KEY);
    }

    @Override
    public ArrayList<TradeData> getSupplyDatas() {
        ArrayList<TradeData> supplyDatas = new ArrayList<TradeData>();
        if (aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY) != null) {
            supplyDatas = (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY);
            return supplyDatas;
        } else {
            //缓存为空。直接返回 内容 为空 的mQuestionList
            return supplyDatas;
        }
//        return (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY);
    }

/*   private Context context;
    private Handler handler;
    private ArrayList<TradeData> tradeDatasList = new ArrayList<TradeData>();
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

    *//**
     * 网络请求数据
     *//*
    @Override
    public void doRequest(String page, String pageSize, final boolean isRefresh) {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ConstantUtil.BASE_URL)
                .build();
        Call<ArrayList<TradeData>> call = retrofit.create(ITrade.class).getInfoTrade(page, pageSize);
        call.enqueue(new Callback<ArrayList<TradeData>>() {
            @Override
            public void onResponse(Response<ArrayList<TradeData>> response, Retrofit retrofit) {
                Log.e("responseCode", response.code() + "");
                if (response.isSuccess()) {
                    ArrayList<TradeData> responseDatas = response.body();
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
                    Log.d("TradeSupplyListView", supplyCacheDatas.size() + "");
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
    }*/
//-------------------接口方法结束------------------
}
