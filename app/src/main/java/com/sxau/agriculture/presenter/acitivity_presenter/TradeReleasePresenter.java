package com.sxau.agriculture.presenter.acitivity_presenter;

import android.os.Handler;

import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.presenter.activity_presenter_interface.ITradeReleasePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Administrator on 2016/6/4.
 * @author Yawen_li
 */
public class TradeReleasePresenter implements ITradeReleasePresenter{
    private ArrayList<CategorieData> responsecategorieDatas=new ArrayList<>();
    private Handler mhandler;
    private String cate;
//   private ArrayList<CategorieData> spinData=new ArrayList<>();

    public TradeReleasePresenter(Handler mhandler) {
        this.mhandler = mhandler;
    }

    @Override
    public void doRequest() {
        Call<ArrayList<CategorieData>> call= RetrofitUtil.getRetrofit().create(ICategoriesData.class).getCategoriesForTrade();
        call.enqueue(new Callback<ArrayList<CategorieData>>() {
            @Override
            public void onResponse(Response<ArrayList<CategorieData>> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    responsecategorieDatas=response.body();
                   /* for (int i=0;i<responsecategorieDatas.size();i++){
                        cate=responsecategorieDatas.get(i).getName();
                        spinData.add(cate);
                    }*/
                    mhandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public ArrayList<CategorieData> getCategorieinfo() {
        return responsecategorieDatas;
    }

}
