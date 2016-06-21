package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.sxau.agriculture.api.IGetMessageList;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.bean.MessageList;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author 高海龙
 */
public class MessagePresenter implements IMessagePresenter {
    private IMessageFragment iMessageFragment;
    private Handler handler;
    private String authToken;
    private ArrayList<MessageInfo> messageInfos;
    private ACache mCache;
    private Context context;

    public MessagePresenter(IMessageFragment iMessageFragment, Handler handler, Context context) {
        this.iMessageFragment = iMessageFragment;
        this.context = context;
        this.handler = handler;
        this.mCache = ACache.get(context);
    }

    //-----------------接口方法-----------------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }


    @Override
    public void pullRefersh() {
        if (NetUtil.isNetAvailable(context)) {
            doRequest();
        } else {
//            iMessageFragment.showNoNetWorking();
        }

    }

    @Override
    public void pushRefersh() {
        handler.sendEmptyMessage(ConstantUtil.UP_LOAD);

    }

    @Override
    public void doRequest() {
        authToken = UserInfoUtil.findAuthToken();
        Call<ArrayList<MessageInfo>> call = RetrofitUtil.getRetrofit().create(IGetMessageList.class).getMessage(authToken);
        call.enqueue(new Callback<ArrayList<MessageInfo>>() {
            @Override
            public void onResponse(Response<ArrayList<MessageInfo>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    messageInfos = response.body();
                    mCache.remove(ConstantUtil.CACHE_MESSAGE_KEY);
                    mCache.put(ConstantUtil.CACHE_MESSAGE_KEY, messageInfos);
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
//                iMessageFragment.showRequestTimeout();
            }
        });
    }

    @Override
    public ArrayList<MessageInfo> getDatas() {
        ArrayList<MessageInfo> messageInfoGet = new ArrayList<MessageInfo>();
        if (mCache.getAsObject(ConstantUtil.CACHE_MESSAGE_KEY) != null) {
            messageInfoGet = (ArrayList<MessageInfo>) mCache.getAsObject(ConstantUtil.CACHE_MESSAGE_KEY);
        }
        return messageInfoGet;
    }

//------------------接口方法结束-------------------------
}
