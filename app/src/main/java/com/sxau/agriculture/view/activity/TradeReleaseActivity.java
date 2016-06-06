package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.sxau.agriculture.api.ITradeRelease;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
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

/**
 * Info发布供求界面
 *
 * 问题：请求没有发完，请在doupdata中去查看
 *
 * @author 田帅.
 */
public class TradeReleaseActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivPhoto;
    private List<String> photoPath;
    private Button btnSubmit;

    private TradeReleaseActivity context;
    private String uploadFilePath;
    private UploadManager uploadManager;
    private ArrayList<String> imageUriList = new ArrayList<String>();

    public static final int REQUEST_CODE = 1000;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_release);
        initView();
        ivPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    public void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
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
            case R.id.btn_submit:
                doupdata();
                finish();
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
//            for (String path : pathList) {
//                //TODO 将网络上传写到这
//                Log.i("ImagePathList", path);
//            }
            path.clear();
            path.addAll(pathList);
            LogUtil.d("TradeReleaseA", "路径集合：" + pathList.toString());
            selectPhotoAdapter.notifyDataSetChanged();
        }
    }

    public void doupdata() {
        for (String imgpath : path) {
            File photoFile = new File(imgpath);
            Uri photoUri = Uri.fromFile(photoFile);
            uploadFilePath = FileUtilsQiNiu.getPath(this, photoUri);
            uploadPicture();
        }
        doPost();
    }

    public void doPost(){
        Map map = new HashMap();
        map.put("categoryId","");
        map.put("title","");
        map.put("tradeType","");
        map.put("content","");
        map.put("image","");


        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeRelease.class).doPostTradeRelease(map);
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


