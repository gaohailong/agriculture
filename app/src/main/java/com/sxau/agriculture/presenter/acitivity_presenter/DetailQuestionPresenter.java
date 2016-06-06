package com.sxau.agriculture.presenter.acitivity_presenter;

import android.widget.Toast;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IDetailQuestion;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gaohailong on 2016/6/4.
 */
public class DetailQuestionPresenter implements IDetailQuestionPresenter {
    private DetailQuestionActivity.MyHandler handler;
    private IDetailQuestionActivity iDetailQuestionActivity;
    private DetailQuestionData detailQuestionData;

    public DetailQuestionPresenter(IDetailQuestionActivity iDetailQuestionActivity, DetailQuestionActivity.MyHandler handler) {
        this.iDetailQuestionActivity = iDetailQuestionActivity;
        this.handler = handler;
    }

    @Override
    public void getDetailData(String id) {
        Call<DetailQuestionData> detailQuestionDataCall = RetrofitUtil.getRetrofit().create(IDetailQuestion.class).getDetailQuestionData(id);
        detailQuestionDataCall.enqueue(new Callback<DetailQuestionData>() {
            @Override
            public void onResponse(Response<DetailQuestionData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    detailQuestionData = response.body();
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
}
