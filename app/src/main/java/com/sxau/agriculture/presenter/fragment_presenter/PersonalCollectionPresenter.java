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
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
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
    private ACache mCache;
    public PersonalCollectionPresenter(IPresonalCollectQuestionFragment iPresonalCollectQuestionFragment, Context context,PersonalCollectQuestionFragment.MyHandler mhandler) {
        this.iPresonalCollectQuestionFragment = iPresonalCollectQuestionFragment;
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
     * @return ArrayList<MyPersonalQuestion>
     */
    @Override
    public ArrayList<MyPersonalCollectionQuestion> getDatas() {
       Log.d("pcqq:","getDates");
        myCQuestionsList = new ArrayList<MyPersonalCollectionQuestion>();
        myPersonalQuestion = new MyPersonalCollectionQuestion();
        List<MyPersonalCollectionQuestion> cQuestionsList = new ArrayList<MyPersonalCollectionQuestion>();
        cQuestionsList = (List<MyPersonalCollectionQuestion>) mCache.getAsObject(ConstantUtil.CACHE_PERSONALCOLLECTQUESTIION_KEY);
//        Log.d("pcqq:",cQuestionsList.get(1).getUser().getName());
        if (mCache.getAsObject(ConstantUtil.CACHE_PERSONALCOLLECTQUESTIION_KEY) != null) {
            for (int i = 0; i < cQuestionsList.size(); i++) {
                myPersonalQuestion = cQuestionsList.get(i);
                myCQuestionsList.add(myPersonalQuestion);
            }
        }
//        try {
//            List<MyPersonalCollectionQuestion> questionslist = new ArrayList<MyPersonalCollectionQuestion>();
//            dbUtils.createTableIfNotExist(MyPersonalCollectionQuestion.class);
//            questionslist = dbUtils.findAll(MyPersonalCollectionQuestion.class);
//            if (!questionslist.isEmpty()){
//                for (int i = 0; i < questionslist.size();i++){
//                    myPersonalQuestion = questionslist.get(i);
//                    myCQuestionsList.add(myPersonalQuestion);
//                    Log.d("pcqp:缓存数据：",myPersonalQuestion.getUser().getName());
//                }
//            }else {
//                return myCQuestionsList;
//            }
//        } catch (DbException e) {
//            e.printStackTrace();
//        }
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
                    Log.d("pcqp",myCQuestionsList.get(1).getUser().getName());
//                    try {
//                        dbUtils.deleteAll(MyPersonalCollectionQuestion.class);
//                        dbUtils.saveAll(myCQuestionsList);
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }

                    //加入缓存中,先清空缓存

                    mCache.remove(ConstantUtil.CACHE_PERSONALCOLLECTQUESTIION_KEY);
                    Log.d("pcqp",ConstantUtil.CACHE_PERSONALCOLLECTQUESTIION_KEY);
                    mCache.put(ConstantUtil.CACHE_PERSONALCOLLECTQUESTIION_KEY, myCQuestionsList);

                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    iPresonalCollectQuestionFragment.closeRefresh();
                    LogUtil.d("pcqp", "4、请求成功，已经保存好数据，通知主线程重新拿数据，更新页面");
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
            Log.d("pcqp:nopullrefersh" ,"doreq");
            iPresonalCollectQuestionFragment.showNoNetworking();
            iPresonalCollectQuestionFragment.closeRefresh();
        }
    }

    @Override
    public void pushRefersh() {

    }
//-----------------接口方法结束------------------
}
