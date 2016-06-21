package com.sxau.agriculture.presenter.acitivity_presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IExpertAnswer;
import com.sxau.agriculture.presenter.activity_presenter_interface.IExpertAnswerPresenter;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 专家回答的fragment
 * 问题：服务器问题500
 *
 * @author 高海龙
 */
public class ExpertAnswerPresenter implements IExpertAnswerPresenter {
    private IExpertAnswerActivity iExpertAnswerActivity;
    private String authToken;

    public ExpertAnswerPresenter(IExpertAnswerActivity iExpertAnswerActivity) {
        this.iExpertAnswerActivity = iExpertAnswerActivity;
    }

    //-----------------接口方法---------------------
    @Override
    public void submitAnswer() {
        authToken = UserInfoUtil.findAuthToken();
        Map map = new HashMap();
        map.put("questionId", iExpertAnswerActivity.getId());
        map.put("content", iExpertAnswerActivity.getAnswerContent());
        Log.e("authToken", authToken);
        Call<JsonObject> doAnswer = RetrofitUtil.getRetrofit().create(IExpertAnswer.class).doAnswer(authToken, map);
        doAnswer.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                Log.e("responsecode", response.code() + "");//TODO 服务器问题500
                if (response.isSuccess()) {
                    Toast.makeText(AgricultureApplication.getContext(), "回答成功!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AgricultureApplication.getContext(), "回答失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//-------------------接口方法结束------------------
}
