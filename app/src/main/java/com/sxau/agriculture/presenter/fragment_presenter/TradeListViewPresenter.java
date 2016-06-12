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
 * 问题;缓存有问题
 *
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
    }

    @Override
    public ArrayList<TradeData> getDemandDatas() {
        ArrayList<TradeData> supplyDatas = new ArrayList<TradeData>();
        if (aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY) != null) {
            supplyDatas = (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADEDEMAND_KEY);
            return supplyDatas;
        } else {
            return supplyDatas;
        }
    }

    @Override
    public ArrayList<TradeData> getSupplyDatas() {
        ArrayList<TradeData> supplyDatas = new ArrayList<TradeData>();
        if (aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY) != null) {
            supplyDatas = (ArrayList<TradeData>) aCache.getAsObject(ConstantUtil.CACHE_TRADESUPPLY_KEY);
            return supplyDatas;
        } else {
            return supplyDatas;
        }
    }


    //---------------接口方法-------------------------
//-------------------接口方法结束------------------
}
