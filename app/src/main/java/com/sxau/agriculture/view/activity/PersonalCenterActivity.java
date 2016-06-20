package com.sxau.agriculture.view.activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.widget.Toast;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.AuthTokenUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IPersonalCenterActivity;

import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.utils.ActivityCollectorUtil;

import com.sxau.agriculture.view.fragment.PersonalCollectQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalCollectTradeFragment;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalTradeInfoFragment;

import java.lang.ref.WeakReference;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 个人中心Activity
 * @author 李秉龙
 * @update Yawen_Li
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener,IPersonalCenterActivity {

    private ViewPager vTitlePaper;
    private ImageButton ib_back;
    private Button btn_compile;
    private Button btn_exit;
    private ACache mCache;
    private TextView tvUserName;
    private String userName;
    private String phone;
    private String authToken;
    private ProgressDialog pdLoginwait;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);
        initView();

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.MyQuestion, PersonalQuestionFragment.class)
                .add(R.string.TradeInfo, PersonalTradeInfoFragment.class)
                .add(R.string.CollectQuestion, PersonalCollectQuestionFragment.class)
                .add(R.string.CollectTrade, PersonalCollectTradeFragment.class)
                .create());
        vTitlePaper = (ViewPager) findViewById(R.id.viewpager);
        vTitlePaper.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(vTitlePaper);
        mCache = ACache.get(this);
        authToken = AuthTokenUtil.findAuthToken();
    }

    private void initView() {

        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);


        mCache = ACache.get(PersonalCenterActivity.this);
        tvUserName = (TextView) this.findViewById(R.id.tv_personal_nickname);
        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);
        btn_exit = (Button) this.findViewById(R.id.btn_exit);

        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        userName = user.getName();
        phone = user.getPhone();
        if (userName != null) {
            tvUserName.setText(userName);
        } else {
            tvUserName.setText(phone);
        }

        ib_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_compile.setOnClickListener(this);
        LogUtil.d("PersonalCenter", "1、初始化View，获取数据");

        pdLoginwait = new ProgressDialog(PersonalCenterActivity.this);
        pdLoginwait.setMessage("正在退出...");
        pdLoginwait.setCanceledOnTouchOutside(false);
        pdLoginwait.setCancelable(true);

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalCenterActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确认退出吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showProgress(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1500);
                            doExitRequest();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_compile:
                Intent intent2 = new Intent();
                intent2.setClass(PersonalCenterActivity.this, PersonalCompileActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_exit:
                showDialog();
                break;
            default:
        }
    }

    private void doExitRequest(){
        Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).doExitRequest(authToken);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()){
                    showProgress(false);
                    //退出成功
                    doExit();
                }else {
                    showProgress(false);
                    //退出失败
                    showRequestTimeout();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                showProgress(false);
                //退出请求错误
                showServiceError();
            }
        });
    }

    private void doExit(){
        ActivityCollectorUtil.finishAll();
        //删除所用缓存
        mCache.clear();
        Intent intent = new Intent(PersonalCenterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //退出显示圆形进度条
    public void showProgress(boolean flag) {
        if (flag) {
            pdLoginwait.show();
        } else {
            pdLoginwait.cancel();
        }
    }

    public void showServiceError() {
        Toast.makeText(PersonalCenterActivity.this, "服务器提出了一个问题", Toast.LENGTH_LONG).show();
    }
//----------------------接口方法开始

    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalCenterActivity.this, "没有网络连接，请检查网络", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalCenterActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();

    }
//-----------------------接口方法结束
}
