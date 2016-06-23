package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Intent;
import android.os.Handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IImprovePersonalInfoPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.activity.ImprovePersonalInfoActivity;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.activity_interface.IImprovePersonalInfoActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author Yawen_Li on 2016/6/1.
 */
public class ImprovePersonalInfoPresenter implements IImprovePersonalInfoPresenter {


    private IImprovePersonalInfoActivity iImprovePersonalInfoActivity;
    private String address;
    private String realName;
    private String industry;
    private String scale;
    private String authToken;
    private Handler handler;

    public ImprovePersonalInfoPresenter(IImprovePersonalInfoActivity iImprovePersonalInfoActivity ,ImprovePersonalInfoActivity.MyHandler myHandler) {
        this.iImprovePersonalInfoActivity = iImprovePersonalInfoActivity;
        this.handler = myHandler;
    }


//-------------------接口方法开始------------------

    //发送个人信息
    @Override
    public void submitPersonalData() {
        Map map = new HashMap();
        map.put("address", address);
        map.put("realName", realName);
        map.put("industry", industry);
        map.put("scale", scale);

        authToken = UserInfoUtil.findAuthToken();

        LogUtil.d("ImprovrPersonalInfoP", "authToken：" + authToken);
        Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).submitPersonalData(map, authToken);
        call.enqueue(new retrofit.Callback<JsonObject>() {

            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    //请求成功
                    LogUtil.d("ImprovePersonalInfoP", "请求成功，请求返回body：" + response.body() + "  请求返回code：" + response.code() + "  请求返回Message：" + response.message());
                    //给出提示信息
                    iImprovePersonalInfoActivity.showProgress(false);
                    iImprovePersonalInfoActivity.showSuccess();
                    handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
                } else {
                    //请求失败
                    LogUtil.d("ImprovePersonalInfoP", "请求失败，请求返回body：" + response.body() + "  请求返回code：" + response.code() + "  请求返回Message：" + response.message());
                    //给出提示信息
                    iImprovePersonalInfoActivity.showProgress(false);
                    iImprovePersonalInfoActivity.showFailed();
                    handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求失败，给出提示信息
                iImprovePersonalInfoActivity.showProgress(false);
                iImprovePersonalInfoActivity.showFailed();
                handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
            }
        });

    }

    //初始化数据
    @Override
    public void initData() {
        address = iImprovePersonalInfoActivity.getAddress();
        realName = iImprovePersonalInfoActivity.getRealName();
        industry = iImprovePersonalInfoActivity.getIndustry();
        scale = iImprovePersonalInfoActivity.getScale();
    }

    /**
     * 判断地址是否有效
     *
     * @return
     */
    @Override
    public boolean isAddressAvailable() {
        if (address.isEmpty()) {
            return false;
        } else
            return true;
    }

    /**
     * 判断真实姓名是否有效
     *
     * @return
     */
    @Override
    public boolean isRealNameAvailable() {
        if (realName.isEmpty()) {
            return false;
        } else
            return true;
    }
//---------------------接口方法结束-------------------
}
