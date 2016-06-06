package com.sxau.agriculture.presenter.acitivity_presenter;

import android.os.Handler;


import com.google.gson.Gson;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalCenter;
import com.sxau.agriculture.bean.MyPersonalCenter;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCenterPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.PersonalCenterActivity;
import com.sxau.agriculture.view.activity_interface.IPersonalCenterActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by Administrator on 2016/6/4.
 */
public class PersonalCenterPresenter implements IPersonalCenterPresenter {
    private IPersonalCenterActivity iPersonalCenter;
    private ACache mCache;
    private String authToken;
    private ArrayList<MyPersonalCenter> myPersonalCentersList;

    public PersonalCenterPresenter(PersonalCenterActivity activity,IPersonalCenterActivity iPersonalCenterActivity) {
        this.iPersonalCenter = iPersonalCenterActivity;
        mCache = ACache.get(activity);
    }


    //接口方法开始
    @Override
    public ArrayList<MyPersonalCenter> getDates() {
        myPersonalCentersList = new ArrayList<MyPersonalCenter>();
        MyPersonalCenter myPersonalCenter = new MyPersonalCenter();
        List<MyPersonalCenter> userDates = new ArrayList<MyPersonalCenter>();
        userDates = (List<MyPersonalCenter>) mCache.getAsObject(ConstantUtil.CACHE_PERSONALUSER_KEY);

        if (mCache.getAsObject(ConstantUtil.CACHE_PERSONALUSER_KEY)!=null){
            myPersonalCenter = userDates.get(0);
            myPersonalCentersList.add(myPersonalCenter);
        }
        return myPersonalCentersList;
    }

    @Override
    public boolean isNetAvailable() {
      return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    @Override
    public void doRequest() {

    }
    //接口方法结束
}
