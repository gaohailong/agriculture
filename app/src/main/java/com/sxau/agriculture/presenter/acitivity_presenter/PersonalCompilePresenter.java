package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.sxau.agriculture.api.IUserData;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCompilePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IPersonalCompileActivity;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCompilePresenter implements IPersonalCompilePresenter {

    private IPersonalCompileActivity iPersonalCompileActivity;
    private String authToken;
    private ACache mCache;
    private Handler handler;

    public PersonalCompilePresenter(IPersonalCompileActivity iPersonalCompileActivity, Context context, Handler handler) {
        this.iPersonalCompileActivity = iPersonalCompileActivity;
        this.mCache = ACache.get(context);
        this.handler = handler;
    }

    //-----------------------接口方法---------------------
    @Override
    public void doUpdate() {

    }

    @Override
    public void setInformation() {

    }

    @Override
    public User getData() {
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        return user;
    }

    /**
     * 获取用户信息数据
     */
    @Override
    public void requestUserData() {
        //获取缓存中的authToken，添加到请求header中
        User user = new User();
        user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        authToken = user.getAuthToken();
        LogUtil.d("PersonalCompileP",authToken);
        Call<User> call = RetrofitUtil.getRetrofit().create(IUserData.class).getUserData(authToken);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    User user = response.body();
                    //因为请求下来的数据是不包含token的，所以需要手动添加进去，保存后不丢失
                    user.setAuthToken(authToken);
                    LogUtil.d("PersonalCompileP","username:"+user.getName()+"  phone:"+user.getPhone()+"  address:"+user.getAddress()+"  token:"+user.getAuthToken());
                    //存储到缓存中，一定包含用户名和用户的电话
                    mCache.put(ConstantUtil.CACHE_KEY,user);
                    //通知主线程更新UI数据
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }else {
                    //请求失败
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求失败
            }
        });
    }

    @Override
    public Object getInformation() {
        return null;
    }
//------------------接口方法结束---------------------
}
