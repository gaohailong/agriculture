package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;

import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;

import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalTrades;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalTradeInfoPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment.PersonalTradeInfoFragment;
import com.sxau.agriculture.view.fragment_interface.IPersonalTradeInfoFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalTradeInfoPresenter implements IPersonalTradeInfoPresenter {

    private IPersonalTradeInfoFragment iPersonalTradeInfoFragment;
    private ArrayList<MyPersonalTrade> myTradesList;
    private DbUtils dbUtils;
    private Context context;
    private MyPersonalTrade myPersonalTrade;
    private Handler handler;
    private String authToken;

    public PersonalTradeInfoPresenter(IPersonalTradeInfoFragment iPersonalTradeInfoFragment, Context context, PersonalTradeInfoFragment.MyHandler myHandler) {
        this.iPersonalTradeInfoFragment = iPersonalTradeInfoFragment;
        this.context = context;
        dbUtils = DbUtils.create(context);
        this.handler = handler;
    }

    //----------------接口方法---------------------
    /**
     * 获取个人交易数据
     * 返回缓存好的数据
     * 当缓存内容为空时，返回空数据
     *
     * @return ArrayList<MyPersonalQuestion>
     */
    @Override
    public ArrayList<MyPersonalTrade> getDate() {
        myTradesList = new ArrayList<MyPersonalTrade>();
        myPersonalTrade = new MyPersonalTrade();


        try {
            List<MyPersonalTrade> tradeList = new ArrayList<MyPersonalTrade>();
            dbUtils.createTableIfNotExist(MyPersonalTrade.class);
            tradeList = dbUtils.findAll(MyPersonalTrade.class);
            if (!tradeList.isEmpty()){
                for (int i = 0;i<tradeList.size();i++){
                    myPersonalTrade = tradeList.get(i);
                    myTradesList.add(myPersonalTrade);
                }
            }else {
                return myTradesList;
            }


        } catch (DbException e) {
            e.printStackTrace();
        }
        return myTradesList;
    }

    @Override
    public boolean isNetAvailable() {
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    @Override
    public void doRequest() {
        Gson userGson = new Gson();
        User user = new User();
        ACache aCache = ACache.get(AgricultureApplication.getContext());
        String userData = new String();
        userData = aCache.getAsString(ConstantUtil.CACHE_KEY);
        user = userGson.fromJson(userData, User.class);
        authToken = user.getAuthToken();

        Call<ArrayList<MyPersonalTrade>> call=RetrofitUtil.getRetrofit().create(IPersonalTrades.class).getMessage(authToken);
        call.enqueue(new Callback<ArrayList<MyPersonalTrade>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalTrade>> response, Retrofit retrofit) {
                LogUtil.d("persontrade", "请求返回Code："+response.code() + "  请求返回Body："+response.body()+"  请求返回Message："+response.message());
                if (response.isSuccess()) {
                    myTradesList = response.body();
                    //保存到缓存中
                    try {
                        //清空缓存先
                        dbUtils.deleteAll(MyPersonalQuestion.class);
                        dbUtils.saveAll(myTradesList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    //请求成功之后做的操作
//                    iPersonalQuestionFragment.updateView(getDatas());
                    //通知主线程重新加载数据
                    LogUtil.d("PersonalTrade", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面");
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iPersonalTradeInfoFragment.closeRefresh();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                iPersonalTradeInfoFragment.showRequestTimeout();
                iPersonalTradeInfoFragment.closeRefresh();

            }
        });

    }

    @Override
    public void pullRefersh() {
        if (isNetAvailable()) {
            doRequest();
        } else {
            iPersonalTradeInfoFragment.showNoNetworking();
            iPersonalTradeInfoFragment.closeRefresh();
        }
    }


//-----------------接口方法结束-----------------
}
