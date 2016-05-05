package com.sxau.agriculture.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.MessageAdapter;
import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.GetMessageList;
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
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 消息的Fragment
 *
 * @author 高海龙
 */
public class MessageFragment extends BaseFragment implements IMessageFragment {
    private ListView lv_message;
    private List<MessageInfo> messageInfos;
    private Context context = getActivity();
    private IMessagePresenter iMessagePresenter;
    private MessageAdapter messageAdapter;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将MessageFragment与MessagePresenter绑定
        iMessagePresenter = new MessagePresenter(MessageFragment.this);
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        lv_message = (ListView) view.findViewById(R.id.lv_message);
        messageInfos = new ArrayList<MessageInfo>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        statues();
//        initView();
        handler.sendEmptyMessage(0);
    }

    public void statues() {
        Call<MessageList> call = RetrofitUtil.test().create(GetMessageList.class).getResult();
        call.enqueue(new Callback<MessageList>() {
            @Override
            public void onResponse(Response<MessageList> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MessageList messageList = response.body();
                    if (messageList != null) {
                        messageInfos = messageList.getMessageInfo();
                        handler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    private MyHandler handler = new MyHandler(MessageFragment.this);

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
                    case 1:
                        messageAdapter = new MessageAdapter(MessageFragment.this.getActivity(), messageInfos);
                        lv_message.setAdapter(messageAdapter);
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
