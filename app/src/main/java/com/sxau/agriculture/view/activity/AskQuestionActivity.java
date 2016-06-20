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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.AsyncRun;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IAskQuestion;
import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.api.IUploadToken;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.AuthTokenUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.widgets.CityPicker;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 提问页面
 *
 * @author 崔志泽
 */
public class AskQuestionActivity extends BaseActivity implements View.OnClickListener {
    //控件定义部分
    private ImageView ib_photo;
    private ImageView ib_voice;
    private Button btn_submit;
    private EditText et_title;
    private EditText et_trade_content;
    private TopBarUtil top_question;
    private RecyclerView recycler;
    private Spinner spinner;
    private ProgressDialog pdLoginwait;
    private TitleBarTwo topBarUtil;
    //adapter
    private SelectPhotoAdapter selectPhotoAdapter;
    //数据集合定义部分
    private ArrayList<String> path = new ArrayList<>();
    private ArrayList<String> spinData;
    private ArrayList<CategorieData> categorieDatas;
    private ArrayList<String> imageUriList = new ArrayList<String>();

    private MyHandler myHandler;
    private String cat;

    private String uploadFilePath;
    private UploadManager uploadManager;
    private AskQuestionActivity context;
    private String uploadToken;
    private String domain;

    private String questionImage;
    private String questionTitle;
    private String questionContent;
    private int questionType;
    private String authorToken;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        ib_voice = (ImageView) findViewById(R.id.ib_voice);
        ib_photo = (ImageView) findViewById(R.id.ib_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_title = (EditText) findViewById(R.id.et_trade_title);
        et_trade_content = (EditText) findViewById(R.id.et_trade_content);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        spinner = (Spinner) findViewById(R.id.sp_trade_cotegory);
//        top_question = (TopBarUtil) findViewById(R.id.top_question);
        initTitlebar();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter = new SelectPhotoAdapter(this, path);
        recycler.setAdapter(selectPhotoAdapter);
        spinData = new ArrayList<>();
        myHandler = new MyHandler(AskQuestionActivity.this);
        categorieDatas = new ArrayList<>();
        context = AskQuestionActivity.this;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionType = categorieDatas.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        top_question.setLeftImageIsVisible(true);
        top_question.setLeftImage(R.mipmap.ic_back_left);
        top_question.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {

            }

            @Override
            public void onClickLeftImage() {
                finish();
            }

            @Override
            public void onClickRightImage() {

            }
        });

        ib_photo.setOnClickListener(this);
        ib_voice.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        pdLoginwait = new ProgressDialog(AskQuestionActivity.this);
        pdLoginwait.setMessage("提交中...");
        pdLoginwait.setCanceledOnTouchOutside(false);
        pdLoginwait.setCancelable(true);

        authorToken = AuthTokenUtil.findAuthToken();

        startNet();
        getUploadToken();
    }

    private void initTitlebar() {
        topBarUtil = (TitleBarTwo) findViewById(R.id.top_question);
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
        topBarUtil.setTitle("问答详情");
        topBarUtil.setTitleColor(Color.WHITE);
    }

    public void startNet() {
        initSpin();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    //初始化下拉菜单
    public void initSpin() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_spinner, spinData);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_photo:
                showPhotoDialog();
                break;
            case R.id.ib_voice:
                showVoiceDialog();
                break;
            case R.id.btn_submit:
                showProgress(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            //提交网络请求发送问题
                            if (path.size() > 0) {
                                doupdata();
                            } else {
                                upload();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    public void showProgress(boolean flag) {
        if (flag) {
            pdLoginwait.show();
        } else {
            pdLoginwait.cancel();
        }
    }

    //获取图片的方法
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
        ImageSelector.open(AskQuestionActivity.this, imageConfig);
    }

    public void showVoiceDialog(){
        View view = getLayoutInflater().inflate(R.layout.popwindow_voice, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        TextView btn_finish = (TextView) view.findViewById(R.id.btn_finish);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop_alert));

        //点击窗口外边不消失
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        //显示位置
        popupWindow.showAtLocation(ib_voice, Gravity.BOTTOM, 0, 0);

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 获取最终的数据

                popupWindow.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
    //回调函数
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


    public class MyHandler extends Handler {
        WeakReference<AskQuestionActivity> activityWeakReference;

        public MyHandler(AskQuestionActivity activity) {
            activityWeakReference = new WeakReference<AskQuestionActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    getCategories();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    getCategorieinfo();
                    break;
                case ConstantUtil.SUCCESS_UPLOAD_PICTURE:
                    upload();
                    break;
                default:
                    break;
            }
        }
    }

    //提交问题
    public void upload() {
        //获得标题
        questionTitle = et_title.getText().toString();
        questionContent = et_trade_content.getText().toString();
        if (imageUriList != null) {
            questionImage = StringUtil.changeListToString(imageUriList);
        }else {
            questionImage = "";
        }

        Map map = new HashMap();
        map.put("categoryId", questionType);
        map.put("title", questionTitle);
        map.put("content", questionContent);
        map.put("image", questionImage);

        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IAskQuestion.class).sendQuestion(authorToken, map);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                showProgress(false);
                Log.e("getCode", response.code() + "");
                Toast.makeText(AskQuestionActivity.this, "提问成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Throwable t) {
                showProgress(false);
                Toast.makeText(AskQuestionActivity.this, "提问失败", Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }

    //网络请求分类数据
    public void getCategories() {
        Call<ArrayList<CategorieData>> call = RetrofitUtil.getRetrofit().create(ICategoriesData.class).getCategories();
        call.enqueue(new Callback<ArrayList<CategorieData>>() {
            @Override
            public void onResponse(Response<ArrayList<CategorieData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categorieDatas = response.body();
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //遍历请求数据获取分类信息
    public void getCategorieinfo() {
        for (int i = 0; i < categorieDatas.size(); i++) {
            cat = categorieDatas.get(i).getName();
            spinData.add(cat);
        }
        initSpin();
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
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IUploadToken.class).getUploadToken(authorToken);
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
                                final String imageUrl = domain  + fileKey + "?imageView2/0/w/" + width + "/format/jpg";
                                final String imageurl = fileKey+".jpg";
                                imageUriList.add(imageurl);
                                if (imageUriList.size() == path.size()) {
                                    myHandler.sendEmptyMessage(ConstantUtil.SUCCESS_UPLOAD_PICTURE);
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
