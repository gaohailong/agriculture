package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.MessageAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.presenter.fragment_presenter.MessagePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.activity.TradeContentActivity;
import com.sxau.agriculture.view.activity.WebViewTwoActivity;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * 消息的Fragment
 *
 * @author 高海龙
 */
public class MessageFragment extends BaseFragment implements IMessageFragment, AdapterView.OnItemClickListener {
    private ListView lv_message;
    private ArrayList<MessageInfo> messageInfos;
    private RefreshLayout srl_refresh;
    private View footerLayout;
    private TextView tv_more;

    private Context context;
    private IMessagePresenter iMessagePresenter;
    private MessageAdapter messageAdapter;

    private int currentPage = 1;
    private MyHandler handler;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            context = MessageFragment.this.getActivity();
            //将MessageFragment与MessagePresenter绑定
            handler = new MyHandler(MessageFragment.this);
            messageInfos = new ArrayList<MessageInfo>();
            iMessagePresenter = new MessagePresenter(MessageFragment.this, handler, context);
            context = MessageFragment.this.getActivity();
            view = inflater.inflate(R.layout.fragment_message, container, false);
            lv_message = (ListView) view.findViewById(R.id.lv_message);

            srl_refresh = (RefreshLayout) view.findViewById(R.id.srl_refresh);
            srl_refresh.setColorSchemeColors(R.color.mainColor);

            footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
            tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);

            initListView();
            initRefresh();
            if (NetUtil.isNetAvailable(context)) {
                handler.sendEmptyMessage(ConstantUtil.INIT_DATA);
                RefreshBottomTextUtil.setTextMore(tv_more,ConstantUtil.LOADINDG);
                srl_refresh.setLoading(false);
            } else {
                Toast.makeText(context, "没有网络连接", Toast.LENGTH_SHORT).show();
                srl_refresh.setLoading(false);
            }
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }

        return view;
    }

    //初始化listView
    public void initListView() {
        messageAdapter = new MessageAdapter(context, messageInfos);
        lv_message.setAdapter(messageAdapter);
        lv_message.addFooterView(footerLayout);
        srl_refresh.setChildView(lv_message);
    }

    //初始化下拉刷新
    public void initRefresh() {

        srl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
            }
        });

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
        lv_message.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (messageInfos.size() > 0) {
            Intent intentStart = new Intent();
            try {
                int itemId = messageInfos.get(position).getRelationId();
                iMessagePresenter.changeRead(itemId);
                Log.e("itemIdGet","itemId=="+itemId);
                String type = messageInfos.get(position).getMessageType();
                switch (type) {
                    case ConstantUtil.QUESTION://问答(已成功)
                        intentStart.setClass(context, DetailQuestionActivity.class);
                        intentStart.putExtra("indexPosition", itemId);
                        break;
                    case ConstantUtil.TRADE://交易(已成功)
                        intentStart.setClass(context, TradeContentActivity.class);
                        intentStart.putExtra("TradeId", itemId);
                        break;
                    case ConstantUtil.ARTICLE://文章(未试验)
                        intentStart.setClass(context, WebViewTwoActivity.class);
                        intentStart.putExtra("article", itemId);
                        break;
                    case ConstantUtil.RELATION://关系
                        break;
                    case ConstantUtil.SYSTEM://系统
                        break;
                    case ConstantUtil.WECHAT://微信
                        intentStart.setClass(context, DetailQuestionActivity.class);
                        intentStart.putExtra("indexPosition", itemId);
                        break;
                    case ConstantUtil.NOTICE://公告
                        break;
                    default:
                        break;
                    //Todo 发送网络请求去改变是否已读
                }
                context.startActivity(intentStart);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

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
                        iMessagePresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                        break;
                    case ConstantUtil.GET_NET_DATA:
                        ArrayList<MessageInfo> messageInfoData = iMessagePresenter.getDatas();
                        updateView(messageInfoData);
                        break;
                    case ConstantUtil.PULL_REFRESH:
                        currentPage = 1;
                        iMessagePresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                        srl_refresh.setRefreshing(false);
                        break;
                    case ConstantUtil.UP_LOAD:
                        currentPage++;
                        iMessagePresenter.doRequest(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                        srl_refresh.setLoading(false);
                        break;
                    case ConstantUtil.LOAD_FAIL:
                        if (currentPage > 1) {
                            srl_refresh.setRefreshing(false);
                            currentPage--;
                        } else {
                            srl_refresh.setRefreshing(false);
                        }
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_FAIL);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //-------------------接口方法----------------
    @Override
    public void updateView(ArrayList<MessageInfo> messageInfosData) {
        messageInfos.clear();
        messageInfos.addAll(messageInfosData);
        messageAdapter.notifyDataSetChanged();
        if (iMessagePresenter.isLoadOver()) {
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
        }
    }
    //-------------------接口方法结束--------------

}