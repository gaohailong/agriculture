package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sxau.agriculture.adapter.MessageAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IGetMessageList;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.bean.MessageList;
import com.sxau.agriculture.presenter.fragment_presenter.MessagePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 消息的Fragment
 *
 * @author 高海龙
 */
public class MessageFragment extends BaseFragment implements IMessageFragment, SwipeRefreshLayout.OnRefreshListener {
    private ListView lv_message;
    private List<MessageInfo> messageInfos;
    private Context context = getActivity();
    private IMessagePresenter iMessagePresenter;
    private MessageAdapter messageAdapter;
    private SwipeRefreshLayout srl_refresh;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将MessageFragment与MessagePresenter绑定
        iMessagePresenter = new MessagePresenter(MessageFragment.this);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        lv_message = (ListView) view.findViewById(R.id.lv_message);

        srl_refresh = (SwipeRefreshLayout) view.findViewById(R.id.srl_refresh);
        srl_refresh.setColorSchemeColors(R.color.mainColor);

        messageInfos = new ArrayList<MessageInfo>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        handler.sendEmptyMessage(0);
    }

    public void initListView() {
        messageAdapter = new MessageAdapter(MessageFragment.this.getActivity(), messageInfos);
        lv_message.setAdapter(messageAdapter);
        Log.e("11111","1");
//        getData();
    }

    public void getData() {
        Call<MessageList> call = RetrofitUtil.getRetrofit().create(IGetMessageList.class).getResult();
        call.enqueue(new Callback<MessageList>() {
            @Override
            public void onResponse(Response<MessageList> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MessageList messageList = response.body();
                    if (messageList != null) {
                        messageInfos = messageList.getMessageInfo();
                        handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                        Log.e("11111","2");
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    private MyHandler handler = new MyHandler(MessageFragment.this);

    @Override
    public void onRefresh() {

    }

    private class MyHandler extends Handler {
        WeakReference<MessageFragment> weakReference;

        public MyHandler(MessageFragment activity) {
            weakReference = new WeakReference<MessageFragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MessageFragment activity = weakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        getData();
                        break;
                    case ConstantUtil.GET_NET_DATA:
//                        messageAdapter.notifyDataSetChanged();
                        initListView();
                        Log.e("11111","3");
                        break;
                    default:
                        break;
                }
            }
        }
    }


    //-------------------接口方法----------------
    @Override
    public void updateView() {

    }
    //-------------------接口方法结束--------------
}
