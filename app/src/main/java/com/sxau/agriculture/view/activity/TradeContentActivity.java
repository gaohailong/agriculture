package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ITradeContent;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.utils.ConstantUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 具体问题、内容的详情activity
 * Created by Yawen_Li on 2016/4/13.
 */
public class TradeContentActivity extends BaseActivity {
    /**
     * 控件定义
     */
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_info;
    private ImageView iv_collection;
    /**
     * 实体类对象
     */
    private TradeData tradeData;
    /**
     * 交易数据的id
     */
    private int tradeContentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_content);

        initView();
        Toast.makeText(TradeContentActivity.this, "click", Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        tradeContentId = intent.getIntExtra("TradeId", 0);
        Toast.makeText(this, "我是" + tradeContentId, Toast.LENGTH_SHORT).show();
        getTradeContent();
    }

    /**
     * 实例化控件
     */
    public void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_info= (TextView) findViewById(R.id.tv_info);
        iv_collection= (ImageView) findViewById(R.id.iv_trade_content_collection);
    }

    /**
     *
     * */
    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TradeContentActivity.class);
        context.startActivity(intent);
    }

    /**
     * 请求数据
     */
    public void getTradeContent() {
        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ConstantUtil.BASE_URL).build();
        Call<TradeData> call = retrofit.create(ITradeContent.class).getTrade(tradeContentId);
        call.enqueue(new Callback<TradeData>() {
            @Override
            public void onResponse(Response<TradeData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    tradeData = response.body();
                    setTradeData();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    /**
     * 给控件赋值
     */
    public void setTradeData() {
        tv_name.setText(tradeData.getUser().getName());
        tv_title.setText(tradeData.getTitle());
        tv_info.setText(tradeData.getDescription());
            if (tradeData.isFav()){
                iv_collection.setImageResource(R.drawable.ic_praise_48px);
            }else{
                iv_collection.setImageResource(R.drawable.ic_no_praise_48px);
            }
    }
}
