package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IExpert;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IExpertArticlePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.fragment.ExpertArticleFragment;
import com.sxau.agriculture.view.fragment_interface.IExpertArticleFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class ExpertArticlePresenter implements IExpertArticlePresenter{
    private IExpertArticleFragment iExpertArticleFragment;
    private ArrayList<HomeArticle> expertArticleList;
    private String authToken;
    private String userId;
    private Context context;
    private Handler handler;
    private HomeArticle expertArticle;
    private ACache mCache;
    public ExpertArticlePresenter(IExpertArticleFragment iExpertArticleFragment, Context context, ExpertArticleFragment.MyHandler mhandler) {
        this.iExpertArticleFragment = iExpertArticleFragment;
        this.context = context;
        this.handler = mhandler;
        this.mCache = ACache.get(context);
    }



    //------------------接口方法-----------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }
    /**
     * 获取个人收藏的问题数据
     * 返回缓存好的数据
     * 当缓存内容为空时，返回空数据
     *
     * @return ArrayList<expertArticle>
     */
    @Override
    public ArrayList<HomeArticle> getDatas() {
        authToken = UserInfoUtil.findAuthToken();
        userId = UserInfoUtil.getUserId();
        LogUtil.d("PersonalQuestionP:authToken:", authToken + "");
        expertArticleList = new ArrayList<HomeArticle>();
        expertArticle = new HomeArticle();
        List<HomeArticle> cQuestionList = new ArrayList<HomeArticle>();
        cQuestionList = (List<HomeArticle>) mCache.getAsObject(ConstantUtil.CACHE_EXPERTARTICLE_KEY);

        if (mCache.getAsObject(ConstantUtil.CACHE_EXPERTARTICLE_KEY) != null) {
            for (int i = 0; i < cQuestionList.size(); i++) {
                expertArticle = cQuestionList.get(i);
                expertArticleList.add(expertArticle);
            }
        }
        return expertArticleList;
    }

    @Override
    public boolean isNetAvailable() {
        Log.d("pcqp", "isNetavailable");
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    @Override
    public void doRequest() {
        authToken = UserInfoUtil.findAuthToken();
        userId = UserInfoUtil.getUserId();
        Log.d("collectionQuestion",authToken);
        Call<ArrayList<HomeArticle>> call = RetrofitUtil.getRetrofit().create(IExpert.class).getExpertArticles(userId);
        call.enqueue(new Callback<ArrayList<HomeArticle>>() {
            @Override
            public void onResponse(Response<ArrayList<HomeArticle>> response, Retrofit retrofit) {
                Log.d("pcqp:issuceess",response.isSuccess()+"");
                if (response.isSuccess()){
                    expertArticleList = response.body();
                    Log.d("pcqp","code"+response.code()+"body:"+response.body().toString());
//                    Log.d("pcqp",expertArticleList.get(1).getUser().getName());
//                    try {
//                        dbUtils.deleteAll(MyPersonalCollectionQuestion.class);
//                        dbUtils.saveAll(expertArticleList);
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }

                    //加入缓存中,先清空缓存

                    //加入缓存中,先清空缓存
                    mCache.remove(ConstantUtil.CACHE_EXPERTARTICLE_KEY);
                    mCache.put(ConstantUtil.CACHE_EXPERTARTICLE_KEY, expertArticleList);

                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iExpertArticleFragment.closeRefresh();
                    LogUtil.d("pcqp", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                iExpertArticleFragment.showRequestTimeout();
                iExpertArticleFragment.closeRefresh();
            }
        });
    }

    @Override
    public void pullRefersh() {
        boolean b = isNetAvailable();
        Log.d("isnetavalabe",b+"");
        if (isNetAvailable()) {
            Log.d("pcqp" ,"doreq");
            doRequest();
        } else {
            Log.d("pcqp:nopullrefersh" ,"doreq");
            iExpertArticleFragment.showNoNetworking();
            iExpertArticleFragment.closeRefresh();
        }
    }

    @Override
    public void pushRefersh() {

    }
//-----------------接口方法结束------------------
}
