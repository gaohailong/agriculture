package com.sxau.agriculture.view.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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

import com.google.gson.JsonObject;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IAskQuestion;
import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.api.IUploadToken;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
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
import java.util.UUID;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 提问页面
 * 提交声音问题部分（上传至自己的服务器，没有接口还没写）
 * 获取提交声音的token接口不对
 * @author 崔志泽
 */
public class AskQuestionActivity extends BaseActivity implements View.OnClickListener {
    //控件定义部分
    private ImageView ib_photo;
    private ImageView ib_voice;
    private Button btn_submit;
    private EditText et_title;
    private EditText et_trade_content;
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

    //语音部分
    private String mFileName = null;    //语音文件保存路径
    private MediaRecorder mRecorder = null;     //用于完成录音
    private String uploadTokenForAudio;
    private String uploadAudioFilePath;
    private String audioUrl;

    private TextView tv_voice;
    private TextView tv_del_voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        tv_voice = (TextView) findViewById(R.id.tv_voice);
        tv_del_voice = (TextView) findViewById(R.id.tv_del_voice);


        ib_voice = (ImageView) findViewById(R.id.ib_voice);
        ib_photo = (ImageView) findViewById(R.id.ib_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_title = (EditText) findViewById(R.id.et_trade_title);
        et_trade_content = (EditText) findViewById(R.id.et_trade_content);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        spinner = (Spinner) findViewById(R.id.sp_trade_cotegory);

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


        ib_photo.setOnClickListener(this);

        ib_voice.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        tv_del_voice.setOnClickListener(this);

        pdLoginwait = new ProgressDialog(AskQuestionActivity.this);
        pdLoginwait.setMessage("提交中...");
        pdLoginwait.setCanceledOnTouchOutside(false);
        pdLoginwait.setCancelable(true);

        authorToken = UserInfoUtil.findAuthToken();

        startNet();
        getUploadToken();
        getUploadTokenForAudio();
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
        topBarUtil.setTitle("发布问题");
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
                if (et_title.getVisibility() == View.GONE){
                    Toast.makeText(context,"一次只能上传一个语音文件，请删除已有文件后再执行操作",Toast.LENGTH_LONG).show();
                }else {
                    showVoiceDialog();
                }
                break;
            case R.id.btn_submit:
                showProgress(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            //提交网络请求发送问题
                            //提交音频问题
                            if (tv_del_voice.getVisibility() == View.VISIBLE){
                                if (path.size() > 0) {
                                    doupdataPhoto();
                                } else {
                                    doupdataAudio();
                                }
                            }else {
                                //提交文字问题
                                if (path.size() > 0) {
                                    doupdataPhoto();
                                } else {
                                    uploadTextQuestion();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                finish();
                break;
            case R.id.tv_del_voice:
                deleteVoice(new File(mFileName));
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

    //获取音频的方法
    public void showVoiceDialog(){
        View view = getLayoutInflater().inflate(R.layout.popwindow_voice, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        TextView btn_finish = (TextView) view.findViewById(R.id.btn_finish);
        final ImageView iv_touchvoice = (ImageView) view.findViewById(R.id.iv_touchvoice);
        final TextView tv_voicemsg = (TextView) view.findViewById(R.id.tv_voicemsg);
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


        iv_touchvoice.setOnTouchListener(new View.OnTouchListener() {
            boolean hasDone = false;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.e("AskQA", "按下");
                        iv_touchvoice.setImageResource(R.mipmap.ic_voice_finish);
                        if (hasDone){
                            //已经录制完成
                            Toast.makeText(context,"已经录制完成，请点击完成",Toast.LENGTH_SHORT).show();
                        }else {
                            tv_voicemsg.setText("正在录音....");
                            startVoice();
                        }
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.e("AskQA", "弹起");
                        tv_voicemsg.setText("录音完成");
                        if (!hasDone){
                            stopVoice();
                        }
                        hasDone = true;

                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 获取最终的数据
                voiceFinish();
                popupWindow.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voiceCancel();
                popupWindow.dismiss();
            }
        });
    }

    //录音完成后续操作
    public void voiceFinish(){
        if (mFileName != null){
            et_title.setVisibility(View.GONE);
            et_trade_content.setVisibility(View.GONE);
            tv_voice.setVisibility(View.VISIBLE);
            tv_del_voice.setVisibility(View.VISIBLE);
        }
    }
    //取消录音后续操作
    public void voiceCancel(){
        //判断下状态，若已经有声音文件则不做操作，若没有声音文件，则删除本次的文件
        if (tv_del_voice.getVisibility() == View.GONE){
            deleteFile(new File(mFileName));
        }
    }

    //开始录音
    private void startVoice() {
        // 设置录音保存路径
        mFileName = ConstantUtil.AUDIO_LOCAL_PATH + UUID.randomUUID().toString() + ".mp3";
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            Log.i("AskQA", "SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(mFileName).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            Log.i("AskQA", "Path to file could not be created");
        }
        Toast.makeText(getApplicationContext(), "开始录音",Toast.LENGTH_SHORT).show();
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("AskQA", "prepare() failed");
        }
        mRecorder.start();
    }

    //停止录音
    private void stopVoice() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
//        Toast.makeText(getApplicationContext(), "保存录音" + mFileName, Toast.LENGTH_SHORT).show();
        LogUtil.e("AskQA", "文件位置：" + mFileName);
    }

    //删除录音操作
    public void deleteVoice(File file){
        deleteFile(file);
        et_title.setVisibility(View.VISIBLE);
        et_trade_content.setVisibility(View.VISIBLE);
        tv_voice.setVisibility(View.GONE);
        tv_del_voice.setVisibility(View.GONE);
    }

    //删除文件
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    this.deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.d("AskQA","文件不存在");
        }
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
                    if (et_title.getVisibility() == View.VISIBLE){
                        uploadTextQuestion();
                    }else {
                        doupdataAudio();
                    }
                    break;
                case ConstantUtil.SUCCESS_UPLOAD_AUDIO:
                    uploadAudioQuestion();
                    break;
                default:
                    break;
            }
        }
    }

    //提交文字问题
    public void uploadTextQuestion() {
        //获得标题
        questionTitle = et_title.getText().toString();
        questionContent = et_trade_content.getText().toString();
        if (imageUriList != null && !imageUriList.isEmpty()) {
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

//        finish();
    }
    //提交声音问题
    public void uploadAudioQuestion() {
        audioUrl = ConstantUtil.UPLOAD_AUDIO_PREFIX + audioUrl;
        if (imageUriList != null && !imageUriList.isEmpty()) {
            questionImage = StringUtil.changeListToString(imageUriList);
        }else {
            questionImage = "";
        }

        //上传接口没给，还没编写这部分
        Map map = new HashMap();
        map.put("categoryId", questionType);
        map.put("title", "语音问题");
        map.put("content", "请点击播放");
        map.put("image", audioUrl);

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

//        finish();
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
    public void doupdataPhoto() {
        for (int i = 0; i < path.size(); i++) {
            File photoFile = new File(path.get(i));
            Uri photoUri = Uri.fromFile(photoFile);
            uploadFilePath = FileUtilsQiNiu.getPath(this, photoUri);
            uploadPic(uploadToken, domain);
        }
    }
    public void doupdataAudio(){
        File audioFile = new File(mFileName);
        Uri audioUri = Uri.fromFile(audioFile);
        uploadAudioFilePath = FileUtilsQiNiu.getPath(this,audioUri);
        uploadAudio(uploadTokenForAudio,domain);
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

    //获取上传音频权限token
    private void getUploadTokenForAudio() {
        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(IUploadToken.class).getUploadToken(authorToken);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                Log.e("UPLOADTOKENForAudio", response.code() + "");
                if (response.isSuccess()) {
                    JsonObject joResponseBody = response.body();
                    Log.e("AskQA","JsonObject:"+joResponseBody.toString());
                    JsonObject getData = joResponseBody.getAsJsonObject("success");
                    uploadTokenForAudio = getData.get("message").getAsString();
                    LogUtil.e("AskQA","uploadTokenForAudio:"+uploadTokenForAudio);
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
                                final String imageurl = fileKey + ".jpg";
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

    private void uploadAudio(final String uploadTokenForAudio, final String domain) {
        if (this.uploadManager == null) {
            this.uploadManager = new UploadManager();
        }
        UploadOptions uploadOptions = new UploadOptions(null, null, false,
                null, null);
        this.uploadManager.put(uploadAudioFilePath, null, uploadTokenForAudio,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo respInfo, JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                audioUrl = fileKey + ".mp3";
                                myHandler.sendEmptyMessage(ConstantUtil.SUCCESS_UPLOAD_AUDIO);

                                Log.e("AudioFileUrl22222222", audioUrl);
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
