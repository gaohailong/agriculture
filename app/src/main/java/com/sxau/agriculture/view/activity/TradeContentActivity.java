package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ITradeContent;
import com.sxau.agriculture.api.ITradeFav;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.utils.TitleBarTwo;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 具体问题、内容的详情activity
 * Created by Yawen_Li on 2016/4/13.
 */
public class TradeContentActivity extends BaseActivity implements View.OnClickListener {
    //控件定义部分
    private TextView tv_name;
    private TextView tv_title;
    private TextView tv_info;
    private ImageView iv_collection;
    private TextView tv_attentionNum;
    private TextView tv_location;
    private TextView tv_timeStart;
    private TextView tv_timeEnd;
    private TextView tv_phone;
    //实体类对象
    private TradeData tradeData;
    //点击的交易的id
    private int tradeContentId;
    //控制收藏的变量
    private boolean needCollect;
    private boolean collection;
    private TitleBarTwo topBarUtil;
    private NineGridImageView nineGridImageView;
    private NineGridImageViewAdapter<String> mAdapter;
    private List<String> imgDatas;
    private Context context;
    private String authToken;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_content);

        context = TradeContentActivity.this;
        authToken = UserInfoUtil.findAuthToken();
        handler = new MyHandler(TradeContentActivity.this);
        initView();
        initTopBar();
        getTradeId();
        initNineGridView();
        if (needCollect) {
            iv_collection.setVisibility(View.VISIBLE);
        } else {
            iv_collection.setVisibility(View.GONE);
        }
        getTradeContent();
    }

    public void initNineGridView() {
        mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String t) {
                Picasso.with(context).load(t).placeholder(R.mipmap.ic_loading).into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
            }
        };
        nineGridImageView.setAdapter(mAdapter);
    }

    public void initView() {
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_info = (TextView) findViewById(R.id.tv_info);
        tv_attentionNum = (TextView) findViewById(R.id.tv_attentionNum);
        tv_location = (TextView) findViewById(R.id.tv_location);
        iv_collection = (ImageView) findViewById(R.id.iv_trade_content_collection);
        tv_timeStart = (TextView) findViewById(R.id.tv_timeStart);
        tv_timeEnd = (TextView) findViewById(R.id.tv_timeEnd);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        topBarUtil = (TitleBarTwo) findViewById(R.id.topBar_detail);
        nineGridImageView = (NineGridImageView) findViewById(R.id.mNineGridImageView);

        iv_collection.setOnClickListener(this);
    }

    private void initTopBar() {
        topBarUtil.setBackgroundColor(Color.parseColor("#00b5ad"));
        topBarUtil.setLeftImageResource(R.mipmap.ic_back_left);
        topBarUtil.setLeftTextColor(Color.WHITE);
        topBarUtil.setDividerColor(Color.GRAY);
        topBarUtil.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBarUtil.setTitle("交易详情");
        topBarUtil.setTitleColor(Color.WHITE);
    }

    public static void actionStart(Context context, int id, boolean needCollect) {
        Intent intent = new Intent(context, TradeContentActivity.class);
        intent.putExtra("TradeId", id);
        intent.putExtra("needCollect", needCollect);
        context.startActivity(intent);
    }

    public void getTradeId() {
        Intent intent = getIntent();
        tradeContentId = intent.getIntExtra("TradeId", 0);
        needCollect = intent.getBooleanExtra("needCollect", true);
    }

    public class MyHandler extends Handler {
        WeakReference<TradeContentActivity> weakReference;

        public MyHandler(TradeContentActivity activity) {
            weakReference = new WeakReference<TradeContentActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    setTradeData();
                    if (tradeData.getImages() != null){
                        imgDatas = StringUtil.changeStringToList(tradeData.getImages());
                        imgDatas = StringUtil.changeToWholeUrlList(imgDatas);
                        Log.d("DetailQA","imgDatas："+tradeData.getImages().toString());
                        for (int i=0;i<imgDatas.size();i++){
                            Log.d("DetailQA","imgDatas："+imgDatas.get(i)+" 位置："+i);
                        }
                        //设置九宫格数据源
                        nineGridImageView.setImagesData(imgDatas);
                    }
                    break;
                case ConstantUtil.CHANGE_COLLECTION_STATE:
//                    changeCollectionIC();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 请求数据
     */
    public void getTradeContent() {
        Call<TradeData> call = RetrofitUtil.getRetrofit().create(ITradeContent.class).getTrade(authToken, tradeContentId);
        call.enqueue(new Callback<TradeData>() {
            @Override
            public void onResponse(Response<TradeData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    tradeData = response.body();
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
//                    setTradeData();
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
        tv_attentionNum.setText("关注人数：" + tradeData.getLikeCount() + "");
        tv_location.setText(tradeData.getUser().getAddress());
        tv_timeStart.setText(TimeUtil.format(tradeData.getWhenCreated()));
        tv_timeEnd.setText("至" + TimeUtil.format(tradeData.getWhenUpdated()));
        tv_phone.setText("联系电话：" + tradeData.getUser().getPhone());
        collection = tradeData.isFav();
        Log.e("Trade", "isFav:" + collection);
        if (tradeData.isFav()) {
            iv_collection.setImageResource(R.drawable.collection_fill);
        } else {
            iv_collection.setImageResource(R.drawable.collection);
        }
    }

    @Override
    public void onClick(View v) {
        if (collection) {
            //执行取消收藏操作
            collection = false;
            if (NetUtil.isNetAvailable(context)) {
                Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeFav.class).doUnCollectTrade(authToken, tradeData.getId());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            //取消收藏执行成功
                            Log.d("TradeListViewAdapter", "取消收藏" + tradeData.getId());
                            Log.d("TradeAdapter", "code:" + response.code());
                            //修改图标
                            iv_collection.setImageResource(R.drawable.collection);
                        } else {
                            //取消收藏执行失败
                            Log.e("TradeListViewAdapter", "code:" + response.code() + " body:" + response.body() + "  message:" + response.message());
                            showServerError();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        //取消收藏执行失败
                        Log.e("TradeListViewAdapter", "执行失败");
                        showRequestTimeout();
                    }
                });
            } else {
                showNetUnavailable();
            }
        } else {
            //执行收藏操作
            collection = true;
            //设置缓存收藏状态变化
            if (NetUtil.isNetAvailable(context)) {
                Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeFav.class).doCollectTrade(authToken, tradeData.getId());
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                        if (response.isSuccess()) {
                            //收藏执行成功
                            Log.d("TradeListViewAdapter", "收藏成功" + tradeData.getId());
                            Log.d("TradeAdapter", "code:" + response.code());

                            iv_collection.setImageResource(R.drawable.collection_fill);

                        } else {
                            //收藏执行失败
                            Log.e("TradeListViewAdapter", "code:" + response.code() + " body:" + response.body() + "  message:" + response.message());
                            showServerError();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        //收藏执行失败
                        Log.e("TradeListViewAdapter", "执行失败");
                        showRequestTimeout();
                    }
                });
            } else {
                showNetUnavailable();
            }
        }
    }

    public void showRequestTimeout() {
        Toast.makeText(context, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
    }

    public void showNetUnavailable() {
        Toast.makeText(context, "网络不可用，请检查网络", Toast.LENGTH_LONG).show();
    }

    public void showServerError() {
        Toast.makeText(context, "服务器提出了一个问题", Toast.LENGTH_LONG).show();
    }
}
