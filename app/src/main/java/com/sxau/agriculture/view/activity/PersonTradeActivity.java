package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IPersonTradeContent;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.TimeUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class PersonTradeActivity extends AppCompatActivity {
    //控件定义部分
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_info;
    private TextView tv_attentionNum;
    private TextView tv_location;
    private TextView tv_timeStart;
    private TextView tv_timeEnd;
    private TextView tv_phone;
    //实体类对象
    private TradeData tradeData;
    //点击的交易的id
    private int tradeContentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_trade);

        initText();

        getTradeId();

        getTradeContent();
    }

    //实例化控件
    public void initText(){
        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_info= (TextView) findViewById(R.id.tv_info);
        tv_attentionNum= (TextView) findViewById(R.id.tv_attentionNum);
        tv_location= (TextView) findViewById(R.id.tv_location);
        tv_timeStart= (TextView) findViewById(R.id.tv_timeStart);
        tv_timeEnd= (TextView) findViewById(R.id.tv_timeEnd);
        tv_phone= (TextView) findViewById(R.id.tv_phone);
    }

    public static void actionStart(Context context,int id){
        Intent intent=new Intent(context,PersonTradeActivity.class);
        intent.putExtra("tradeId",id);
        context.startActivity(intent);
    }

    public void getTradeId(){
        Intent intent=getIntent();
        tradeContentId=intent.getIntExtra("tradeId",0);
    }

    public void getTradeContent(){
        Call<TradeData> call= RetrofitUtil.getRetrofit().create(IPersonTradeContent.class).getTradeContent(tradeContentId);
        call.enqueue(new Callback<TradeData>() {
            @Override
            public void onResponse(Response<TradeData> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    tradeData=response.body();
                    getTradeData();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void getTradeData(){
        tv_name.setText(tradeData.getUser().getName());
        tv_title.setText(tradeData.getTitle());
        tv_info.setText(tradeData.getDescription());
        tv_attentionNum.setText(tradeData.getLikeCount()+"");
        tv_location.setText(tradeData.getUser().getAddress());
        tv_timeStart.setText(TimeUtil.format(tradeData.getWhenCreated()));
        tv_timeEnd.setText(TimeUtil.format(tradeData.getWhenUpdated()));
        tv_phone.setText(tradeData.getUser().getPhone());
    }
}
