package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;

import com.sxau.agriculture.api.ITrade;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.presenter.fragment_presenter_interface.ITradeListViewPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
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
    private ArrayList<TradeData> supplyDatas = new ArrayList<TradeData>();
    private ArrayList<TradeData> demandDatas = new ArrayList<TradeData>();
    private TradeData tradeData;
    private String tradeTypeSupply = "SUPPLY";

    public TradeListViewPresenter(ITradeListViewFragment iInfoListViewFragment, Context context, Handler handler) {
        this.iInfoListViewFragment = iInfoListViewFragment;
        this.context = context;
        this.handler = handler;
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
                if (response.isSuccess()) {
                    ArrayList<TradeData> responseDatas = response.body();
                    if (isRefresh) {
                        tradeDatasList.clear();
                        supplyDatas.clear();
                        tradeDatasList.addAll(responseDatas);
                        iInfoListViewFragment.isLoadOver(false);
                    } else {
                        supplyDatas.clear();
                        tradeDatasList.addAll(responseDatas);
                    }
                    /**
                     * 供应需求分类
                     * */
                    for (int i = 0; i < tradeDatasList.size(); i++) {
                        tradeData = tradeDatasList.get(i);
                        if (tradeTypeSupply.equals(tradeData.getTradeType())) {
                            supplyDatas.add(tradeData);
                        } else {
                            demandDatas.add(tradeData);
                        }
                    }
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

        return supplyDatas;
    }
//-------------------接口方法结束------------------
}
