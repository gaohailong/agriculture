package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IExpert;
import com.sxau.agriculture.api.IPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IExpertQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.fragment.ExpertQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment_interface.IExpertQuestionFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 指派给专家的问题 Presenter
 * Created by Yawen_Li on 2016/4/22.
 */
public class ExpertQuestionPresenter implements IExpertQuestionPresenter {

    private IExpertQuestionFragment iExpertQuestionFragment;
    private ArrayList<MyPersonalQuestion> mQuestionsList;

    private Context context;
    private MyPersonalQuestion myPersonalQuestion;
    private Handler handler;
    private String authToken;
    private String userId;
    private ACache mCache;


    public ExpertQuestionPresenter(IExpertQuestionFragment iExpertQuestionFragment, Context context, ExpertQuestionFragment.MyHandler handler) {
        this.iExpertQuestionFragment = iExpertQuestionFragment;
        this.context = context;
        this.mCache = ACache.get(context);
        this.handler = handler;
    }

    //------------------接口方法--------------------

    /**
     * 获取个人问题数据
     * 返回缓存好的数据
     * 当缓存内容为空时，返回空数据
     *
     * @return ArrayList<MyPersonalQuestion>
     */
    @Override
    public ArrayList<MyPersonalQuestion> getDatas() {
        mQuestionsList = new ArrayList<MyPersonalQuestion>();
        myPersonalQuestion = new MyPersonalQuestion();

        List<MyPersonalQuestion> questionList = new ArrayList<MyPersonalQuestion>();
        questionList = (List<MyPersonalQuestion>) mCache.getAsObject(ConstantUtil.CACHE_EXPERTQUESTION_KEY);
        //缓存不为空时将数据填充返回
//        LogUtil.d("PersonalQusetionP:getDates:questionList",questionList.get(0).getTitle());
        if (mCache.getAsObject(ConstantUtil.CACHE_EXPERTQUESTION_KEY) != null) {
            for (int i = 0; i < questionList.size(); i++) {
                myPersonalQuestion = questionList.get(i);
                mQuestionsList.add(myPersonalQuestion);
            }
        } else {
            //缓存为空。直接返回 内容 为空 的mQuestionList
            return mQuestionsList;
        }
        return mQuestionsList;
    }

    @Override
    public boolean isNetAvailable() {
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    /**
     * 请求网络数据
     * 将请求回的数据存放在缓存中
     */
    @Override
    public void doRequest() {
        authToken = UserInfoUtil.findAuthToken();
        userId = UserInfoUtil.getUserId();
        LogUtil.d("PersonalQuestionP:authToken:", authToken + "");
        Call<ArrayList<MyPersonalQuestion>> call = RetrofitUtil.getRetrofit().create(IExpert.class).getExpertQuestions(userId);
        call.enqueue(new Callback<ArrayList<MyPersonalQuestion>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalQuestion>> response, Retrofit retrofit) {
                LogUtil.d("PersonalQuestionP", "请求返回Code：" + response.code() + "  请求返回Body：" + response.body() + "  请求返回Message：" + response.message());
//                LogUtil.d("PersonalQuestionP", "请求返回Code：" + response.code() + "  请求返回Body：" + response.body() + "  请求返回Message：" + response.message());
//                  Toast.makeText(AgricultureApplication.getContext(), response.code(),Toast.LENGTH_SHORT).show();
                if (response.isSuccess()) {
                    mQuestionsList = response.body();
                    //保存到缓存中
                    //清空缓存先
                    mCache.remove(ConstantUtil.CACHE_EXPERTQUESTION_KEY);
                    mCache.put(ConstantUtil.CACHE_EXPERTQUESTION_KEY, mQuestionsList);

                    //请求成功之后做的操作
                    //通知主线程重新加载数据
                    LogUtil.d("PersonalQeustion", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面");
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iExpertQuestionFragment.closeRefresh();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求网络出错
                iExpertQuestionFragment.showRequestTimeout();
                LogUtil.d("PersonalQuestionP", "网络请求出错");
                //关闭掉refresh控件
                iExpertQuestionFragment.closeRefresh();
            }
        });
    }


    @Override
    public void pullRefersh() {
        /**
         * 有网刷新
         * 没网不刷新
         */
        if (isNetAvailable()) {
            doRequest();
        } else {
            iExpertQuestionFragment.showNoNetworking();
            iExpertQuestionFragment.closeRefresh();
        }
    }
//---------------------接口方法结束--------------------
}
