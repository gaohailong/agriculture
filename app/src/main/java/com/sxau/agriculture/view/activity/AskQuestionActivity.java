package com.sxau.agriculture.view.activity;


import android.content.Intent;
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
import android.widget.Spinner;
import android.widget.Toast;

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
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.AuthTokenUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.TopBarUtil;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
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
    private Button btn_submit;
    private EditText et_title;
    private EditText et_trade_content;
    private TopBarUtil top_question;
    private RecyclerView recycler;
    private Spinner spinner;
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
    private TradeReleaseActivity context;

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

        ib_photo = (ImageView) findViewById(R.id.ib_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_title = (EditText) findViewById(R.id.et_trade_title);
        et_trade_content = (EditText) findViewById(R.id.et_trade_content);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        spinner = (Spinner) findViewById(R.id.sp_trade_cotegory);
        top_question = (TopBarUtil) findViewById(R.id.top_question);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter = new SelectPhotoAdapter(this, path);
        recycler.setAdapter(selectPhotoAdapter);
        spinData = new ArrayList<>();
        myHandler = new MyHandler();
        categorieDatas = new ArrayList<>();

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
        btn_submit.setOnClickListener(this);

        startNet();
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
            case R.id.btn_submit:
                //提交网络请求发送问题
                doupdata();
                //获得标题
                questionTitle = et_title.getText().toString();
                questionContent = et_trade_content.getText().toString();
                questionImage = imageUriList.toString();

                Map map = new HashMap();
                map.put("categoryId", questionType);
                map.put("title", questionTitle);
                map.put("content", questionContent);
                map.put("image", questionImage);
                authorToken = AuthTokenUtil.findAuthToken();

                Call<String> call = RetrofitUtil.getRetrofit().create(IAskQuestion.class).sendQuestion(map, authorToken);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Response<String> response, Retrofit retrofit) {
                        Toast.makeText(AskQuestionActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
                finish();
                break;
            default:
                break;
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

    //回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                //TODO 将网络上传写到这
                LogUtil.i("ImagePathList", path);
            }
            path.clear();
            path.addAll(pathList);
            selectPhotoAdapter.notifyDataSetChanged();
        }
    }


    public class MyHandler extends Handler {
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
                default:
                    break;
            }
        }
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
        for (String imgpath : path) {
            File photoFile = new File(imgpath);
            Uri photoUri = Uri.fromFile(photoFile);
            uploadFilePath = FileUtilsQiNiu.getPath(this, photoUri);
            uploadPicture();
        }
    }

    //将图片路径添加到这外部添加一个图片
    public void uploadPicture() {
        LogUtil.d("TradeReleaseA", "uploadPicture execute");
        if (this.uploadFilePath == null) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClient httpClient = new OkHttpClient();
                Request req = new Request.Builder().url(QiniuLabConfig.makeUrl(
                        QiniuLabConfig.REMOTE_SERVICE_SERVER,
                        QiniuLabConfig.QUICK_START_IMAGE_DEMO_PATH)).method("GET", null).build();
                com.squareup.okhttp.Response resp = null;
                try {
                    resp = httpClient.newCall(req).execute();
                    JSONObject jsonObject = new JSONObject(resp.body().string());
                    String uploadToken = jsonObject.getString("uptoken");
                    String domain = jsonObject.getString("domain");
                    upload(uploadToken, domain);
                } catch (Exception e) {
                    AsyncRun.run(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(
                                    context,
                                    "申请上传凭证失败",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                    Log.e(QiniuLabConfig.LOG_TAG, e.getMessage());
                } finally {
                    if (resp != null) {
                        try {
                            resp.body().close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    //=============================必须的===================================
    private void upload(final String uploadToken, final String domain) {
        if (this.uploadManager == null) {
            this.uploadManager = new UploadManager();
        }
        File uploadFile = new File(this.uploadFilePath);
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                null, null);
        this.uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo respInfo,
                                         JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                DisplayMetrics dm = new DisplayMetrics();
                                final int width = dm.widthPixels;
                                final String imageUrl = domain + "/" + fileKey + "?imageView2/0/w/" + width + "/format/jpg";
                                imageUriList.add(imageUrl);
                                Log.e("imageUrl11111111", imageUrl);
                            } catch (JSONException e) {
                                Toast.makeText(
                                        context,
                                        "上传回复解析错误",
                                        Toast.LENGTH_LONG).show();
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
