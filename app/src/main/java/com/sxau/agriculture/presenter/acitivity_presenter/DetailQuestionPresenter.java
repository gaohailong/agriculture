package com.sxau.agriculture.presenter.acitivity_presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IDetailQuestion;
import com.sxau.agriculture.api.IQuestionFav;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问题详情的p
 * @author 高海龙
 */
public class DetailQuestionPresenter implements IDetailQuestionPresenter {
    private DetailQuestionActivity.MyHandler handler;
    private IDetailQuestionActivity iDetailQuestionActivity;
    private DetailQuestionData detailQuestionData;
    private String authToken;

    public DetailQuestionPresenter(IDetailQuestionActivity iDetailQuestionActivity, DetailQuestionActivity.MyHandler handler) {
        this.iDetailQuestionActivity = iDetailQuestionActivity;
        this.handler = handler;
        authToken = UserInfoUtil.findAuthToken();
    }

    //---------------------接口方法开始-----------------------
    @Override
    public void getDetailData(String id) {
        final Call<DetailQuestionData> detailQuestionDataCall = RetrofitUtil.getRetrofit().create(IDetailQuestion.class).getDetailQuestionData(id);
        detailQuestionDataCall.enqueue(new Callback<DetailQuestionData>() {
            @Override
            public void onResponse(Response<DetailQuestionData> response, Retrofit retrofit) {
                Log.e("DetailQP","code:"+response.code());
                if (response.isSuccess()) {
                    detailQuestionData = response.body();
                    Log.e("DetailQP","mediaId:"+detailQuestionData.getMediaId());
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(AgricultureApplication.getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public DetailQuestionData getData() {
        return detailQuestionData;
    }

    @Override
    public void doCollection(int id) {
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IQuestionFav.class).doCollectQuestion(authToken,id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    //收藏执行成功
                    Log.e("DetailQP", "code:" + response.code() + " body:" + response.body() + "  message:" + response.message());
                    handler.sendEmptyMessage(ConstantUtil.CHANGE_TO_COLLECTION_STATE);
                }else {
                    //收藏执行失败
                    iDetailQuestionActivity.showFailed();
                    Log.e("DetailQP", "code:" + response.code() + " body:" + response.body() + "  message:" + response.message());

                }
            }

            @Override
            public void onFailure(Throwable t) {
                //收藏失败
                iDetailQuestionActivity.showServiceError();
            }
        });
    }

    @Override
    public void doUnCollection(int id) {
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IQuestionFav.class).doUnCollectQuestion(authToken, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    //取消收藏执行成功
                    handler.sendEmptyMessage(ConstantUtil.CHANGE_TO_NOCOLLECTION_STATE);
                }else {
                    //取消收藏执行失败
                    iDetailQuestionActivity.showFailed();
                    Log.e("DetailQP", "code:" + response.code() + " body:" + response.body() + "  message:" + response.message());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //取消收藏失败
                iDetailQuestionActivity.showServiceError();
            }
        });
    }
    //---------------------------接口方法结束---------------------------
}
