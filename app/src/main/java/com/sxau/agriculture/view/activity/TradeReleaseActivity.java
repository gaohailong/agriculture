package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ITradeRelease;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.acitivity_presenter.TradeReleasePresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.ITradeReleasePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.ITradeReleaseActivity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Info发布供求界面
 *
 * @author 田帅.
 */
public class TradeReleaseActivity extends BaseActivity implements View.OnClickListener,ITradeReleaseActivity{
    private ImageView ivPhoto;
    private List<String> photoPath;
    private Spinner spTradeType;
    private EditText etTradeTitle;
    private EditText etTradeContent;
    private RadioGroup rgTradeCategory;
    private Button btnTradeRelease;
    /**
     * 交易类型(供应、需求)、交易标题、交易分类、交易内容、图片
     * */
    private int tradeCategoryId;
    private String tradeTitle;
    private String tradeType;
    private String tradeContent;
    private String tradeImage;

    public static final int REQUEST_CODE = 1000;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path = new ArrayList<>();
    private ITradeReleasePresenter iTradeReleasePresenter;
    private Handler mhandler;

    private ArrayList<String> spinData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_release);
/**
 * 初始化控件
 * */
        initView();
        ivPhoto.setOnClickListener(this);
        btnTradeRelease.setOnClickListener(this);
        spTradeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tradeType=spinData.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mhandler=new MyHandler();
        iTradeReleasePresenter=new TradeReleasePresenter(mhandler);
        spinData=new ArrayList<>();
        startNet();
    }
/**
 *  初始化控件
 * */
    public void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);
        etTradeTitle= (EditText) findViewById(R.id.et_trade_title);
        etTradeContent= (EditText) findViewById(R.id.et_trade_content);
        spTradeType = (Spinner) findViewById(R.id.sp_trade_cotegory);
        rgTradeCategory= (RadioGroup) findViewById(R.id.rg_trade_category);
        btnTradeRelease= (Button) findViewById(R.id.btn_trade_release);

        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter = new SelectPhotoAdapter(this, path);
        recycler.setAdapter(selectPhotoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_info_release_photo:
            //发布供求界面弹出选择照片
            showPhotoDialog();
            break;
            case R.id.btn_trade_release:
                /**
                 *得到交易的类型
                 * */
                tradeCategoryId=rgTradeCategory.getId();
                /**
                 * 得到交易的标题
                 * */
                tradeTitle=etTradeTitle.getText().toString();
                /**
                 *得到交易的内容
                 * */
                tradeContent=etTradeContent.getText().toString();
                /**
                 *得到交易的分类
                 * */

                /**
                 *得到图片地址
                 * */
                tradeImage=" ";
                Map map = new HashMap();
                map.put("categoryId",tradeCategoryId);
                map.put("title",tradeTitle);
                map.put("tradeType",tradeType);
                map.put("content",tradeContent);
                map.put("image", tradeImage);

                ACache mCache = ACache.get(TradeReleaseActivity.this);
                User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
                String authToken = user.getAuthToken();
                /**
                 * 发送到服务器
                 * */
                Log.d("release","点击点击");
                Call<JsonObject> call= RetrofitUtil.getRetrofit().create(ITradeRelease.class).postTrade(map,authToken);
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                        Log.d("release",response.code()+"");
                        if (response.isSuccess()){
                            Toast.makeText(TradeReleaseActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                        }
finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                break;
            default:
                break;
        }
    }

    public void showPhotoDialog() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(
                new GlideLoaderUtil())
                .steepToolBarColor(getResources().getColor(R.color.mainColor))
                .titleBgColor(getResources().getColor(R.color.mainColor))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .crop()
                .mutiSelectMaxSize(9)
                .pathList(path)
                .filePath("/ImageSelector/Pictures")
                .showCamera()
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(TradeReleaseActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                //TODO 将网络上传写到这
                Log.i("ImagePathList", path);
            }
            path.clear();
            path.addAll(pathList);
            selectPhotoAdapter.notifyDataSetChanged();
        }
    }
    public void startNet(){
        initSpin();
        mhandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }
    /**
     * 初始化下拉菜单
     */
    public void initSpin(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinData);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTradeType.setAdapter(arrayAdapter);
    }
    @Override
    public void updateView() {

    }
    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.INIT_DATA:
                        iTradeReleasePresenter.doRequest();
                    break;
                case ConstantUtil.GET_NET_DATA:
                        spinData=iTradeReleasePresenter.getCategorieinfo();
                    initSpin();
                    break;
                default:
                    break;
            }
        }
    }

}


