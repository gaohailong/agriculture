package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.utils.AsyncRun;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.sxau.agriculture.api.IUserData;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCompilePresenter;
import com.sxau.agriculture.qiniu.FileUtilsQiNiu;
import com.sxau.agriculture.qiniu.QiniuLabConfig;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IPersonalCompileActivity;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCompilePresenter implements IPersonalCompilePresenter {

    private IPersonalCompileActivity iPersonalCompileActivity;
    private String authToken;
    private ACache mCache;
    private Handler handler;
    private Context context;
    private String uploadFilePath;
    private UploadManager uploadManager;
    private String avatar;
    private String realName;
    private String address;


    /**
     *  请求部分还没写完
     * @param iPersonalCompileActivity
     * @param context
     * @param handler
     */

    public PersonalCompilePresenter(IPersonalCompileActivity iPersonalCompileActivity, Context context, Handler handler) {
        this.iPersonalCompileActivity = iPersonalCompileActivity;
        this.mCache = ACache.get(context);
        this.handler = handler;
        this.context = context;
    }

    public void setImageUri(File photoFile){
        final Uri uri = Uri.fromFile(photoFile);
        try {
            String path = FileUtilsQiNiu.getPath(context, uri);
            this.uploadFilePath = path;
        } catch (Exception e) {
            Toast.makeText(context, "没有找到文件", Toast.LENGTH_LONG).show();
        }
    }

    //将图片路径添加到这外部添加一个图片
    public void uploadPicture() {
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
                                avatar = imageUrl;
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

    //-----------------------接口方法---------------------
    @Override
    public void doUpdate() {
        realName = iPersonalCompileActivity.getRealName();
        address = iPersonalCompileActivity.getUserPosition();
        uploadPicture();
    }

    @Override
    public void setInformation() {

    }

    @Override
    public User getData() {
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        return user;
    }

    /**
     * 获取用户信息数据
     */
    @Override
    public void requestUserData() {
        //获取缓存中的authToken，添加到请求header中
        User user = new User();
        user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        authToken = user.getAuthToken();
        LogUtil.d("PersonalCompileP",authToken);
        Call<User> call = RetrofitUtil.getRetrofit().create(IUserData.class).getUserData(authToken);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    User user = response.body();
                    //因为请求下来的数据是不包含token的，所以需要手动添加进去，保存后不丢失
                    user.setAuthToken(authToken);
                    LogUtil.d("PersonalCompileP","username:"+user.getName()+"  phone:"+user.getPhone()+"  address:"+user.getAddress()+"  token:"+user.getAuthToken());
                    //存储到缓存中，一定包含用户名和用户的电话
                    mCache.put(ConstantUtil.CACHE_KEY,user);
                    //通知主线程更新UI数据
                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }else {
                    //请求失败
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求失败
            }
        });
    }

    @Override
    public Object getInformation() {
        return null;
    }
//------------------接口方法结束---------------------
}
