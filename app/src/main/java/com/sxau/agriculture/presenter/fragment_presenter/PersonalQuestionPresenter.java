package com.sxau.agriculture.presenter.fragment_presenter;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
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

    private static String CACHE_KEY = "Cache_PersonalQuestion";        //缓存文件的名字
    private ACache mCache = ACache.get(AgricultureApplication.getContext());
    private Gson gson;
    private MyPersonalQuestion myPersonalQuestion;


    public PersonalQuestionPresenter(IPersonalQuestionFragment iPersonalQuestionFragment) {
        this.iPersonalQuestionFragment = iPersonalQuestionFragment;
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

        gson = new Gson();
        JSONArray arr = new JSONArray();
        //拿到缓存中的数据，可以是空，也可以有数据
        String catchMessage = mCache.getAsString(CACHE_KEY);
        if ("Empty".equals(catchMessage)) {

            return mQuestionsList;
        } else {
            arr = mCache.getAsJSONArray(CACHE_KEY);
            for (int i = 0; i < arr.length(); i++) {
                String item = null;
                try {
                    item = (String) arr.get(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                myPersonalQuestion = gson.fromJson(item, MyPersonalQuestion.class);
                mQuestionsList.add(myPersonalQuestion);
            }
            return mQuestionsList;
        }
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
                    //////////////////数据的没有放入缓存中,待解决！！！！！！！！
                    mCache.put(CACHE_KEY,mQuestionsList);
                    LogUtil.d("PersonalQuestionP", mQuestionsList + "");
                    String str = mCache.getAsString(CACHE_KEY);
                    LogUtil.d("PersonalQuestionP2", str + "");
//                    iPersonalQuestionFragment.updateView(getDatas());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求网络出错
                iPersonalQuestionFragment.showRequestTimeout();
                LogUtil.d("PersonalQuestionP","网络请求出错");
            }
        });
    }


    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }
//---------------------接口方法结束--------------------
}
