package com.sxau.agriculture.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ITradeRelease;
import com.sxau.agriculture.api.IUploadToken;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.presenter.acitivity_presenter.TradeReleasePresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.ITradeReleasePresenter;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.view.activity_interface.ITradeReleaseActivity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * @author 田帅.
 */
public class TradeReleaseActivity extends BaseActivity implements View.OnClickListener, ITradeReleaseActivity {
    private ImageView ivPhoto;
    private List<String> photoPath;
    private Spinner spTradeType;
    private EditText etTradeTitle;
    private EditText etTradeContent;
    private RadioGroup rgTradeCategory;
    private Button btnTradeRelease;
    private TitleBarTwo topBarUtil;
    private ProgressDialog pdLoginwait;

    private String uploadFilePath;
    private UploadManager uploadManager;
    private TradeReleaseActivity context;
    private String uploadToken;
    private String domain;
    private ArrayList<String> imageUriList = new ArrayList<String>();

    private int tradeCategoryId;
    private String tradeTitle;
    private String tradeContent;
    private String tradeImage;

    public static final int REQUEST_CODE = 1000;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path = new ArrayList<>();
    private ITradeReleasePresenter iTradeReleasePresenter;
    private Handler mhandler;
    private String authToken;
    private ArrayList<CategorieData> spinData;
    private ArrayList<String> spinGetData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_release);
        initView();
        ivPhoto.setOnClickListener(this);
        btnTradeRelease.setOnClickListener(this);
        spTradeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tradeCategoryId = spinData.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mhandler = new MyHandler();
        iTradeReleasePresenter = new TradeReleasePresenter(mhandler);
        spinData = new ArrayList<>();
        spinGetData = new ArrayList<>();
        context = TradeReleaseActivity.this;
        startNet();
    }

    public ArrayList<String> getSpinner() {
        String data = null;
        for (int i = 0; i < spinData.size(); i++) {
            data = spinData.get(i).getName();
            spinGetData.add(data);
        }
        return spinGetData;
    }

    public void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);
        etTradeTitle = (EditText) findViewById(R.id.et_trade_title);
        etTradeContent = (EditText) findViewById(R.id.et_trade_content);
        spTradeType = (Spinner) findViewById(R.id.sp_trade_cotegory);
        rgTradeCategory = (RadioGroup) findViewById(R.id.rg_trade_category);
        btnTradeRelease = (Button) findViewById(R.id.btn_trade_release);

        topBarUtil = (TitleBarTwo) findViewById(R.id.top_trade);
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
        topBarUtil.setTitle("发布交易");
        topBarUtil.setTitleColor(Color.WHITE);

        pdLoginwait = new ProgressDialog(TradeReleaseActivity.this);
        pdLoginwait.setMessage("提交中...");
        pdLoginwait.setCanceledOnTouchOutside(false);
        pdLoginwait.setCancelable(true);

        authToken = UserInfoUtil.findAuthToken();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter = new SelectPhotoAdapter(this, path);
        recycler.setAdapter(selectPhotoAdapter);

        getUploadToken();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_info_release_photo:
                //发布供求界面弹出选择照片
                showPhotoDialog();
                break;
            case R.id.btn_trade_release:
                if (isDataAvailable()) {
                    //执行上传数据操作
                    showProgress(true);
                    Log.e("categoryId", "执行方法");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1500);
                                //提交网络请求发送问题
                                if (path.size() > 0) {
                                    doupdata();
                                } else {
                                    submitData();
                                    Log.e("categoryId", "进行网络数据的提交");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                break;
            default:
                break;
        }
    }

    //显示提交进度圈
    public void showProgress(boolean flag) {
        if (flag) {
            pdLoginwait.show();
        } else {
            pdLoginwait.cancel();
        }
    }

    public void submitData() {
        String tradeTypeCatagory = null;
        int getId = rgTradeCategory.getCheckedRadioButtonId();
        switch (getId) {
            case R.id.rb_supply:
                tradeTypeCatagory = "SUPPLY";
                break;
            case R.id.rb_demand:
                tradeTypeCatagory = "DEMAND";
                break;
        }

        if (imageUriList != null && imageUriList.size() > 0) {
            tradeImage = StringUtil.changeListToString(imageUriList);
        } else {
            tradeImage = "";
        }
        Map map = new HashMap();
        map.put("categoryId", tradeCategoryId);
        Log.e("categoryId1", tradeCategoryId + "");
        map.put("title", tradeTitle);
        Log.e("categoryId2", tradeTitle + "");
        map.put("tradeType", tradeTypeCatagory);
        Log.e("categoryId3", tradeTypeCatagory + "");
        map.put("content", tradeContent);
        Log.e("categoryId4", tradeContent + "");
        map.put("image", tradeImage);
        Log.e("categoryId5", tradeImage + "");

        finish();
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeRelease.class).postTrade(map, authToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                Log.d("release", response.code() + "");
                if (response.isSuccess()) {
                    showProgress(false);
                    Toast.makeText(TradeReleaseActivity.this, "提交成功", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Throwable t) {
                showProgress(false);
                Toast.makeText(TradeReleaseActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
            }
        });
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

    public boolean isDataAvailable() {
        boolean flag = false;
        tradeTitle = etTradeTitle.getText().toString();
        tradeContent = etTradeContent.getText().toString();

        if (tradeTitle.equals("")) {
            Toast.makeText(TradeReleaseActivity.this, "请输入标题", Toast.LENGTH_SHORT).show();
            flag = false;
        } else if (tradeContent.equals("")) {
            Toast.makeText(TradeReleaseActivity.this, "请输入内容", Toast.LENGTH_SHORT).show();
            flag = false;
        } else {
            flag = true;
        }

        return flag;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            path.clear();
            path.addAll(pathList);
            selectPhotoAdapter.notifyDataSetChanged();
        }
    }

    public void startNet() {
        initSpin();
        mhandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    /**
     * 初始化下拉菜单
     */
    public void initSpin() {
        ArrayList<String> data = getSpinner();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, data);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spTradeType.setAdapter(arrayAdapter);
    }

    @Override
    public void updateView() {

    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    iTradeReleasePresenter.doRequest();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    spinData = iTradeReleasePresenter.getCategorieinfo();
                    initSpin();
                    break;
                case ConstantUtil.SUCCESS_UPLOAD_PICTURE:
                    //图片上传成功，执行上传数据信息
                    submitData();
                default:
                    break;
            }
        }
    }

    //======================七牛云存储部分==============================
    public void doupdata() {
        for (int i = 0; i < path.size(); i++) {
            File photoFile = new File(path.get(i));
            Uri photoUri = Uri.fromFile(photoFile);
            uploadFilePath = FileUtilsQiNiu.getPath(this, photoUri);
            uploadPic(uploadToken, domain);
        }
    }

    //获取上传图片权限token
    private void getUploadToken() {
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IUploadToken.class).getUploadToken(authToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                Log.e("UPLOADTOKEN", response.code() + "");
                if (response.isSuccess()) {
                    JsonObject joResponseBody = response.body();
                    JsonObject getData = joResponseBody.getAsJsonObject("success");
                    uploadToken = getData.get("message").getAsString();
                    domain = ConstantUtil.DOMAIN;

                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "获取token失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //=============================必须的===================================
    private void uploadPic(final String uploadToken, final String domain) {
        if (this.uploadManager == null) {
            this.uploadManager = new UploadManager();
        }
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                null, null);
        this.uploadManager.put(uploadFilePath, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo respInfo, JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                DisplayMetrics dm = new DisplayMetrics();
                                final int width = dm.widthPixels;
                                final String imageUrl = domain + fileKey + "?imageView2/0/w/" + width + "/format/jpg";
                                final String imageurl = fileKey;
                                imageUriList.add(imageurl);
                                if (imageUriList.size() == path.size()) {
                                    mhandler.sendEmptyMessage(ConstantUtil.SUCCESS_UPLOAD_PICTURE);
                                }
                                Log.e("imageUrl11111111", imageUrl);
                                Log.e("imageUrl22222222", imageurl);
                            } catch (JSONException e) {
                                Toast.makeText(context, "上传回复解析错误", Toast.LENGTH_LONG).show();
                                Log.e(QiniuLabConfig.LOG_TAG, e.getMessage());
                            }
                        } else {
                            Toast.makeText(
                                    context,
                                    "上传图片失败",
                                    Toast.LENGTH_LONG).show();
                            Log.e(QiniuLabConfig.LOG_TAG, respInfo.toString());
                        }
                    }
                }, uploadOptions);
    }
}


