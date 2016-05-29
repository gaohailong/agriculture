package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
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

    private DbUtils dbUtils;
    private Context context;
    private MyPersonalQuestion myPersonalQuestion;


    public PersonalQuestionPresenter(IPersonalQuestionFragment iPersonalQuestionFragment, Context context) {
        this.iPersonalQuestionFragment = iPersonalQuestionFragment;
        this.context = context;
        dbUtils = DbUtils.create(context);
    }

    //------------------接口方法--------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

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
        try {
            List<MyPersonalQuestion> questionList = dbUtils.findAll(MyPersonalQuestion.class);
            //缓存不为空时将数据填充返回
            if (!questionList.isEmpty()) {
                for (int i = 0; i < questionList.size(); i++) {
                    myPersonalQuestion = questionList.get(i);
                    mQuestionsList.add(myPersonalQuestion);
                }
            } else {
                //缓存为空。直接返回 内容 为空 的mQuestionList
                return mQuestionsList;
            }
        } catch (DbException e) {
            e.printStackTrace();
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
        Call<ArrayList<MyPersonalQuestion>> call = RetrofitUtil.getRetrofit().create(IPersonalQuestion.class).getMessage();
        call.enqueue(new Callback<ArrayList<MyPersonalQuestion>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalQuestion>> response, Retrofit retrofit) {
                LogUtil.d("PersonalQuestionP", response.code() + "");
                if (response.isSuccess()) {
                    mQuestionsList = response.body();
                    //保存到缓存中
                    try {
                        //清空缓存先
                        dbUtils.deleteAll(MyPersonalQuestion.class);
                        dbUtils.saveAll(mQuestionsList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
//                    //验证是否缓存成功
//                    try {
//                        List<MyPersonalQuestion> myPersonalQuestionList;
//                        myPersonalQuestionList = dbUtils.findAll(MyPersonalQuestion.class);
//                        String str2 = myPersonalQuestionList.get(0).getTitle();
//                        LogUtil.d("PersonalQuestionP2", str2 + "");
//
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
                    //请求成功之后做的操作
                    iPersonalQuestionFragment.updateView(getDatas());
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
        }else {
            iPersonalQuestionFragment.showNoNetworking();
            iPersonalQuestionFragment.closeRefresh();
        }
    }
//---------------------接口方法结束--------------------
}
