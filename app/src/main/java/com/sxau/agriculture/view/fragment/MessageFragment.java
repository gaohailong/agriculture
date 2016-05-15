package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 消息的Fragment
 * 问题：
 * 1.传入数据的改变
 * 2、如果没有数据了提示什么
 * 3.textView改变了以后还要往回变（文字的改变）
 * 4.文字缓存使用的是先缓存，载从缓存中去读
 * @author 高海龙
 */
public class MessageFragment extends BaseFragment implements IMessageFragment {
    private ListView lv_message;
    private List<MessageInfo> messageInfos;
    private RefreshLayout srl_refresh;
    private View footerLayout;
    private TextView tv_more;

    private Context context = getActivity();
    private IMessagePresenter iMessagePresenter;
    private MessageAdapter messageAdapter;

    private int currentPage = 1;
    private MyHandler handler = new MyHandler(MessageFragment.this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将MessageFragment与MessagePresenter绑定
        iMessagePresenter = new MessagePresenter(MessageFragment.this);

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        lv_message = (ListView) view.findViewById(R.id.lv_message);

        srl_refresh = (RefreshLayout) view.findViewById(R.id.srl_refresh);
        srl_refresh.setColorSchemeColors(R.color.mainColor);

        footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);

        messageInfos = new ArrayList<MessageInfo>();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        initRefresh();
        handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    //初始化listView
    public void initListView() {
        messageAdapter = new MessageAdapter(MessageFragment.this.getActivity(), messageInfos);
        lv_message.setAdapter(messageAdapter);
    }

    //初始化下拉刷新
    public void initRefresh() {
        lv_message.addFooterView(footerLayout);
        srl_refresh.setChildView(lv_message);

        //下拉刷新
        srl_refresh.setOnLoadListener(new RefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                // simulateLoadingData();获取数据
                handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
            }
        });

        //上拉加载
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // simulateLoadingData();获取数据
                currentPage++;
                handler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
    }

    //获取List网络数据
    public void getListData(String name, String currPage, String getNumber, final boolean isRefresh) {
        Call<MessageList> call1 = RetrofitUtil.getRetrofit().create(IGetMessageList.class).getMessage(name, currPage, getNumber);
        call1.enqueue(new Callback<MessageList>() {
            @Override
            public void onResponse(Response<MessageList> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MessageList messageList = response.body();
                    if (isRefresh) {
                        //下拉刷新
                        messageInfos.clear();
                        if (messageList != null) {
                            messageInfos = messageList.getMessageInfo();
                            if (messageInfos.size()>0) {
                                handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                            }else {
                                Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        //上拉加载
                        if (messageList != null) {
                            messageInfos = messageList.getMessageInfo();
                            if (messageInfos.size() > 0) {
                                messageInfos.addAll(messageInfos);
                                handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                            } else {
                                tv_more.setText("没有更多数据了");
                            }
                        }
                    }
                } else {
                    Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
                    tv_more.setText("数据加载失败");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (currentPage > 0) {
                    currentPage--;
                    Toast.makeText(context, "获取数据失败", Toast.LENGTH_SHORT).show();
                    tv_more.setText("数据加载失败");
                }
            }
        });
    }

    //handler定义
    private class MyHandler extends Handler {
        WeakReference<MessageFragment> weakReference;

        public MyHandler(MessageFragment fragment) {
            weakReference = new WeakReference<MessageFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MessageFragment fragment = weakReference.get();
            if (fragment != null) {
                switch (msg.what) {
                    case ConstantUtil.INIT_DATA:
                        //网络获取第一次
                        getListData("name", String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                        initListView();
                        break;
                    case ConstantUtil.GET_NET_DATA:
                        //获取网络数据,只是为了改变list数据，并不做任何操作
                        messageAdapter.notifyDataSetChanged();
                        break;
                    case ConstantUtil.PULL_REFRESH:
                        //下拉刷新
                        currentPage = 1;
                        getListData("name", String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                        srl_refresh.setRefreshing(false);
                        messageAdapter.notifyDataSetChanged();
                        tv_more.setVisibility(View.VISIBLE);
                        break;
                    case ConstantUtil.UP_LOAD:
                        //上拉加载
                        currentPage++;
                        fragment.tv_more.setText("正在加载中...");
                        getListData("name", String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                        srl_refresh.setLoading(false);
                        messageAdapter.notifyDataSetChanged();
                        tv_more.setVisibility(View.VISIBLE);
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

 /*   Call<MessageList> call = RetrofitUtil.getRetrofit().create(IGetMessageList.class).getResult();
        call.enqueue(new Callback<MessageList>() {
            @Override
            public void onResponse(Response<MessageList> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    MessageList messageList = response.body();
                    if (messageList != null) {
                        messageInfos = messageList.getMessageInfo();
                        handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });*/
