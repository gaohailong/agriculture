package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectTradePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment.PersonalCollectTradeFragment;
import com.sxau.agriculture.view.fragment_interface.IPersonalCollectTradeFragment;

import java.util.ArrayList;
import java.util.List;


import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCollectTradePresenter implements IPersonalCollectTradePresenter {

    private IPersonalCollectTradeFragment iPersonalCollectTradeFragment;
    private ArrayList<MyPersonalCollectTrades> myCollectTradeslsit;
    private Context context;
    private Handler handler;
    private MyPersonalCollectTrades myPersonalCollectTrades;
    private ACache mCache;
    private String authToken;

    public PersonalCollectTradePresenter(IPersonalCollectTradeFragment iPersonalCollectTradeFragment, Context context, PersonalCollectTradeFragment.Myhandler myhandler) {
        this.iPersonalCollectTradeFragment = iPersonalCollectTradeFragment;
        this.context = context;
        this.handler = myhandler;
        //this.mCache = ACache.get(context);
    }

    //-----------------接口方法-----------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public ArrayList<MyPersonalCollectTrades> getDatas() {
        myCollectTradeslsit = new ArrayList<MyPersonalCollectTrades>();
        myPersonalCollectTrades = new MyPersonalCollectTrades();
        mCache = ACache.get(context);
        List<MyPersonalCollectTrades> tradesList = new ArrayList<MyPersonalCollectTrades>();
        tradesList = (List<MyPersonalCollectTrades>) mCache.getAsObject(ConstantUtil.CACHE_PERSONALCOLLECTTRADE_KEY);

        if (mCache.getAsObject(ConstantUtil.CACHE_PERSONALCOLLECTTRADE_KEY) != null) {
            for (int i = 0; i < tradesList.size(); i++) {
                myPersonalCollectTrades = tradesList.get(i);
                myCollectTradeslsit.add(myPersonalCollectTrades);
            }
        }

        return myCollectTradeslsit;
    }

    @Override
    public boolean isNetAvailable() {
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    @Override
    public void doRequest() {
        Gson userGson = new Gson();
        User user = new User();

        String userData = new String();
        userData = mCache.getAsString(ConstantUtil.CACHE_KEY);
        user = userGson.fromJson(userData, User.class);
        authToken = user.getAuthToken();
        Call<ArrayList<MyPersonalCollectTrades>> call = RetrofitUtil.getRetrofit().create(IPersonalCollectTrades.class).getMessage(authToken);
        call.enqueue(new Callback<ArrayList<MyPersonalCollectTrades>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalCollectTrades>> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    LogUtil.d("pctp", "请求返回Code：" + response.code() + "  请求返回Body：" + response.body() + "  请求返回Message：" + response.message());
                    myCollectTradeslsit = response.body();

                    //加入缓存中,先清空缓存
                    mCache.remove(ConstantUtil.CACHE_PERSONALCOLLECTTRADE_KEY);
                    mCache.put(ConstantUtil.CACHE_PERSONALCOLLECTTRADE_KEY, myCollectTradeslsit);

                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iPersonalCollectTradeFragment.closeRefresh();
                    LogUtil.d("PersonalCollectTradeP", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面");
                }

            }

            @Override
            public void onFailure(Throwable t) {
                LogUtil.d("PersonalCollectTradeP", "请求失败");
                iPersonalCollectTradeFragment.showNoNetworking();
                iPersonalCollectTradeFragment.closeRefresh();
            }
        });
    }

    @Override
    public void pullRefersh() {
        if (isNetAvailable()) {
            doRequest();
        } else {
            iPersonalCollectTradeFragment.showRequestTimeout();
            iPersonalCollectTradeFragment.closeRefresh();
        }
    }

    @Override
    public void pushRefersh() {

    }
//------------------接口方法结束--------------
}
