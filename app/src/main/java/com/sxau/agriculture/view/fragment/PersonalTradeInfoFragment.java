package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sxau.agriculture.adapter.PersonalTradeInfoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.view.activity.InfoContentActivity;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * 个人中心交易的listView的fragment
 * 李秉龙
 */
public class PersonalTradeInfoFragment extends BaseFragment {
    private ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View TradeInfoView = inflater.inflate(R.layout.frament_personal_tradeinfo,null);
        listView  = (ListView) TradeInfoView.findViewById(R.id.lv_tradeInfo);

       PersonalTradeInfoAdapter adapter = new PersonalTradeInfoAdapter(PersonalTradeInfoFragment.this.getActivity(),getDate());
       listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoContentActivity.actionStart(PersonalTradeInfoFragment.this.getActivity());
            }
        });
        return TradeInfoView;
    }
    private ArrayList<MyPersonalTrade> getDate() {
        ArrayList<MyPersonalTrade> list = new ArrayList<MyPersonalTrade>();

        MyPersonalTrade myPersonalTrade1 = new MyPersonalTrade();
        myPersonalTrade1.setRv_InfoHead(R.drawable.ic_launc);
        myPersonalTrade1.setTv_TradeName("田帅");
        myPersonalTrade1.setTv_TradeAddress("家里蹲");
        myPersonalTrade1.setTv_TradeDate("1111,11,11");
        myPersonalTrade1.setTv_TradeTitle("买西瓜");
        myPersonalTrade1.setTv_TradeContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

        MyPersonalTrade myPersonalTrade2 = new MyPersonalTrade();
        myPersonalTrade2.setRv_InfoHead(R.drawable.ic_launc);
        myPersonalTrade2.setTv_TradeName("田帅");
        myPersonalTrade2.setTv_TradeAddress("家里蹲");
        myPersonalTrade2.setTv_TradeDate("1111,11,11");
        myPersonalTrade2.setTv_TradeTitle("买西瓜");
        myPersonalTrade2.setTv_TradeContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

        MyPersonalTrade myPersonalTrade3 = new MyPersonalTrade();
        myPersonalTrade3.setRv_InfoHead(R.drawable.ic_launc);
        myPersonalTrade3.setTv_TradeName("田帅");
        myPersonalTrade3.setTv_TradeAddress("家里蹲");
        myPersonalTrade3.setTv_TradeDate("1111,11,11");
        myPersonalTrade3.setTv_TradeTitle("买西瓜");
        myPersonalTrade3.setTv_TradeContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

        MyPersonalTrade myPersonalTrade4 = new MyPersonalTrade();
        myPersonalTrade4.setRv_InfoHead(R.drawable.ic_launc);
        myPersonalTrade4.setTv_TradeName("田帅");
        myPersonalTrade4.setTv_TradeAddress("家里蹲");
        myPersonalTrade4.setTv_TradeDate("1111,11,11");
        myPersonalTrade4.setTv_TradeTitle("买西瓜");
        myPersonalTrade4.setTv_TradeContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

        MyPersonalTrade myPersonalTrade5 = new MyPersonalTrade();
        myPersonalTrade5.setRv_InfoHead(R.drawable.ic_launc);
        myPersonalTrade5.setTv_TradeName("田帅");
        myPersonalTrade5.setTv_TradeAddress("家里蹲");
        myPersonalTrade5.setTv_TradeDate("1111,11,11");
        myPersonalTrade5.setTv_TradeTitle("买西瓜");
        myPersonalTrade5.setTv_TradeContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");

        list.add(myPersonalTrade1);
        list.add(myPersonalTrade2);
        list.add(myPersonalTrade3);
        list.add(myPersonalTrade4);
        list.add(myPersonalTrade5);
        return list;
    }

}
