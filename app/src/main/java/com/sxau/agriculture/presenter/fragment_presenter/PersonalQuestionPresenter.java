package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalQuestionPresenter implements IPersonalQuestionPresenter {

    private IPersonalQuestionFragment iPersonalQuestionFragment;
    private ArrayList<MyPersonalQuestion> mQuestionsList;

    private Context context;
    private MyPersonalQuestion myPersonalQuestion;
    private Handler handler;
    private String authToken;
    private ACache mCache;


    public PersonalQuestionPresenter(IPersonalQuestionFragment iPersonalQuestionFragment, Context context, PersonalQuestionFragment.MyHandler handler) {
        this.iPersonalQuestionFragment = iPersonalQuestionFragment;
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
        questionList = (List<MyPersonalQuestion>) mCache.getAsObject(ConstantUtil.CACHE_PERSONALQUESTION_KEY);
        //缓存不为空时将数据填充返回
//        LogUtil.d("PersonalQusetionP:getDates:questionList",questionList.get(0).getTitle());
        if (mCache.getAsObject(ConstantUtil.CACHE_PERSONALQUESTION_KEY) != null) {
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
        //获取缓存中的authToken，添加到请求header中
        Gson userGson = new Gson();
        User user = new User();

        String userData = new String();
        userData = mCache.getAsString(ConstantUtil.CACHE_KEY);
        user = userGson.fromJson(userData, User.class);
        authToken = user.getAuthToken();
        LogUtil.d("PersonalQuestionP:authToken:",authToken+"");
        Call<ArrayList<MyPersonalQuestion>> call = RetrofitUtil.getRetrofit().create(IPersonalQuestion.class).getMessage(authToken);
        call.enqueue(new Callback<ArrayList<MyPersonalQuestion>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalQuestion>> response, Retrofit retrofit) {
                LogUtil.d("PersonalQuestionP", "请求返回Code：" + response.code() + "  请求返回Body：" + response.body() + "  请求返回Message：" + response.message());
                if (response.isSuccess()) {
                    mQuestionsList = response.body();
                    //保存到缓存中
                    //清空缓存先
                    mCache.remove(ConstantUtil.CACHE_PERSONALQUESTION_KEY);
                    mCache.put(ConstantUtil.CACHE_PERSONALQUESTION_KEY, mQuestionsList);

                    //请求成功之后做的操作
                    //通知主线程重新加载数据
                    LogUtil.d("PersonalQeustion", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面"+mQuestionsList.get(0).getTitle());
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iPersonalQuestionFragment.closeRefresh();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求网络出错
                iPersonalQuestionFragment.showRequestTimeout();
                LogUtil.d("PersonalQuestionP", "网络请求出错");
                //关闭掉refresh控件
                iPersonalQuestionFragment.closeRefresh();
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
            iPersonalQuestionFragment.showNoNetworking();
            iPersonalQuestionFragment.closeRefresh();
        }
    }
//---------------------接口方法结束--------------------
}
