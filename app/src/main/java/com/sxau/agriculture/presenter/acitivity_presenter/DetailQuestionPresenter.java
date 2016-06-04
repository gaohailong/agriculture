package com.sxau.agriculture.presenter.acitivity_presenter;

import android.os.Handler;
import android.widget.Toast;

import com.sxau.agriculture.api.IDetailQuestion;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gaohailong on 2016/6/4.
 */
public class DetailQuestionPresenter implements IDetailQuestionPresenter {
    private Handler handler;
    private IDetailQuestionActivity iDetailQuestionActivity;

    public DetailQuestionPresenter(IDetailQuestionActivity iDetailQuestionActivity, Handler handler) {
        this.iDetailQuestionActivity=iDetailQuestionActivity;
    }

    @Override
    public void getDetailData(String id) {
        Call<DetailQuestionData> detailQuestionDataCall = RetrofitUtil.getRetrofit().create(IDetailQuestion.class).getDetailQuestionData(id);
        detailQuestionDataCall.enqueue(new Callback<DetailQuestionData>() {
            @Override
            public void onResponse(Response<DetailQuestionData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    DetailQuestionData detailQuestionData =response.body();
//                    Toast.makeText(DetailQuestionActivity.this,detailQuestionData.getTitle(),Toast.LENGTH_SHORT).show();
                    LogUtil.d("dd111111",detailQuestionData.getTitle());
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
