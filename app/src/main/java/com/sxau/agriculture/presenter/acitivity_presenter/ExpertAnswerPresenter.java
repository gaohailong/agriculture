package com.sxau.agriculture.presenter.acitivity_presenter;

import android.widget.Toast;

import com.google.gson.Gson;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IExpertAnswer;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IExpertAnswerPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 专家回答的fragment
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
        ACache mCache = ACache.get(AgricultureApplication.getContext());
        String userData = mCache.getAsString(ConstantUtil.CACHE_KEY);
        Gson gson = new Gson();
        User user = gson.fromJson(userData, User.class);
        authToken = user.getAuthToken();
        Call<String> doAnswer = RetrofitUtil.getRetrofit().create(IExpertAnswer.class).doAnswer(authToken, iExpertAnswerActivity.getId(), iExpertAnswerActivity.getAnswerContent());
        doAnswer.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Response<String> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    Toast.makeText(AgricultureApplication.getContext(),"回答成功!",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AgricultureApplication.getContext(),"回答失败!",Toast.LENGTH_SHORT).show();
            }
        });
    }
//-------------------接口方法结束------------------
}
