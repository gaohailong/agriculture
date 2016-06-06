package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IPersonalCollectQuestion;
import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment.PersonalCollectQuestionFragment;
import com.sxau.agriculture.view.fragment_interface.IPresonalCollectQuestionFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCollectionPresenter implements IPersonalCollectQuestionPresenter {
    private IPresonalCollectQuestionFragment iPresonalCollectQuestionFragment;
    private ArrayList<MyPersonalCollectionQuestion> myCQuestionsList;

    private DbUtils dbUtils;
    private Context context;
    private Handler handler;
    private MyPersonalCollectionQuestion myPersonalQuestion;
    public PersonalCollectionPresenter(IPresonalCollectQuestionFragment iPresonalCollectQuestionFragment, Context context,PersonalCollectQuestionFragment.MyHandler handler) {
        this.iPresonalCollectQuestionFragment = iPresonalCollectQuestionFragment;
        this.context = context;
        dbUtils = DbUtils.create(context);
        this.handler = handler;
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
     * @return ArrayList<MyPersonalQuestion>
     */
    @Override
    public ArrayList<MyPersonalCollectionQuestion> getDatas() {

        myCQuestionsList = new ArrayList<MyPersonalCollectionQuestion>();
        myPersonalQuestion = new MyPersonalCollectionQuestion();
        try {
            List<MyPersonalCollectionQuestion> questionslist = new ArrayList<MyPersonalCollectionQuestion>();
            dbUtils.createTableIfNotExist(MyPersonalCollectionQuestion.class);
            questionslist = dbUtils.findAll(MyPersonalCollectionQuestion.class);
            if (!questionslist.isEmpty()){
                for (int i = 0; i < questionslist.size();i++){
                    myPersonalQuestion = questionslist.get(i);
                    myCQuestionsList.add(myPersonalQuestion);
                }
            }else {
                return myCQuestionsList;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return myCQuestionsList;
    }

    @Override
    public boolean isNetAvailable() {
        Log.d("pcqp" ,"isNetavailable");
        return NetUtil.isNetAvailable(AgricultureApplication.getContext());
    }

    @Override
    public void doRequest() {
        Log.d("pcqp","doRequest");
        Call<ArrayList<MyPersonalCollectionQuestion>> call = RetrofitUtil.getRetrofit().create(IPersonalCollectQuestion.class).getMessage();
        call.enqueue(new Callback<ArrayList<MyPersonalCollectionQuestion>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalCollectionQuestion>> response, Retrofit retrofit) {
                Log.d("pcqp:issuceess",response.isSuccess()+"");
                if (response.isSuccess()){
                    myCQuestionsList = response.body();
                    Log.d("pcqp","code"+response.code()+"body:"+response.body().toString());
                    Log.d("pcqp",myCQuestionsList.get(1).getUser().toString());
                    try {
                        dbUtils.deleteAll(MyPersonalCollectionQuestion.class);
                        dbUtils.saveAll(myCQuestionsList);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iPresonalCollectQuestionFragment.closeRefresh();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                iPresonalCollectQuestionFragment.showRequestTimeout();
                iPresonalCollectQuestionFragment.closeRefresh();
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
            iPresonalCollectQuestionFragment.showNoNetworking();
            iPresonalCollectQuestionFragment.closeRefresh();
        }
    }

    @Override
    public void pushRefersh() {

    }
//-----------------接口方法结束------------------
}
