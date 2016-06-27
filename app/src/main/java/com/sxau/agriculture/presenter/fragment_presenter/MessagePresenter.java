package com.sxau.agriculture.presenter.fragment_presenter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.gson.JsonObject;
import com.sxau.agriculture.api.IGetMessageList;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

import org.json.JSONObject;

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
    private boolean isLoadOver;

    public MessagePresenter(IMessageFragment iMessageFragment, Handler handler, Context context) {
        this.iMessageFragment = iMessageFragment;
        this.context = context;
        this.handler = handler;
        this.mCache = ACache.get(context);
        messageInfos = new ArrayList<MessageInfo>();
    }

    //-----------------接口方法-----------------------------

    @Override
    public void doRequest(String page, String pageSize, final boolean isRefresh) {
        authToken = UserInfoUtil.findAuthToken();
        Call<ArrayList<MessageInfo>> call = RetrofitUtil.getRetrofit().create(IGetMessageList.class).getMessage(authToken, page, pageSize);
        call.enqueue(new Callback<ArrayList<MessageInfo>>() {
            @Override
            public void onResponse(Response<ArrayList<MessageInfo>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<MessageInfo> messageInfoGet = response.body();
                    if (isRefresh) {
                        messageInfos.clear();
                        messageInfos.addAll(messageInfoGet);
                    } else {
                        messageInfos.addAll(messageInfoGet);
                    }
                    mCache.remove(ConstantUtil.CACHE_MESSAGE_KEY);
                    mCache.put(ConstantUtil.CACHE_MESSAGE_KEY, messageInfos);
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    if (messageInfos.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        isLoadOver = true;
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                handler.sendEmptyMessage(ConstantUtil.LOAD_FAIL);
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

    @Override
    public boolean isLoadOver() {
        return isLoadOver;
    }

    @Override
    public void changeRead(final int id, final int postion) {
        authToken = UserInfoUtil.findAuthToken();
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IGetMessageList.class).changeReadeStatus(authToken, id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                int s = response.code();
                ArrayList<MessageInfo> messageInfoData1 = getDatas();
                messageInfoData1.get(postion).setMarkRead(true);
                mCache.remove(ConstantUtil.CACHE_MESSAGE_KEY);
                mCache.put(ConstantUtil.CACHE_MESSAGE_KEY, messageInfoData1);
                handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }
//------------------接口方法结束-------------------------
}
