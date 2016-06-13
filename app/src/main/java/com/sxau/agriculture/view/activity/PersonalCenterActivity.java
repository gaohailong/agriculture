package com.sxau.agriculture.view.activity;


import android.app.AlertDialog;
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
import com.sxau.agriculture.bean.MyPersonalCenter;
import com.sxau.agriculture.presenter.acitivity_presenter.PersonalCenterPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCenterPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity_interface.IPersonalCenterActivity;

import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.utils.ActivityCollectorUtil;

import com.sxau.agriculture.view.fragment.PersonalCollectQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalCollectTradeFragment;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalTradeInfoFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


/**
 * 个人中心Activity
 * @author 李秉龙
 */

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener,IPersonalCenterActivity {

    private TextView tv_personal_nickname;
    private IPersonalCenterPresenter  iPersonalCenterPresenter;
    private ArrayList<MyPersonalCenter> myPersonalCenterList;
    private ViewPager vTitlePaper;
    private ImageButton ib_back;
    private Button btn_compile;
    private Button btn_exit;
    private ACache mCache;
    private TextView tvUserName;
    private String userName;
    private String phone;
    private MyHander myHander;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);
        myHander = new MyHander(this);
        iPersonalCenterPresenter = new PersonalCenterPresenter(PersonalCenterActivity.this,myHander);
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
    }

    private void initView() {

        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);
        tv_personal_nickname = (TextView) this.findViewById(R.id.tv_personal_nickname);

        mCache = ACache.get(PersonalCenterActivity.this);
        tvUserName = (TextView) this.findViewById(R.id.tv_personal_nickname);
        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);
        btn_exit = (Button) this.findViewById(R.id.btn_exit);

        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (userName != null) {
            userName = user.getName();
            phone = String.valueOf(user.getPhone());
            tvUserName.setText(userName);
        } else {
            tvUserName.setText(phone);
        }

        ib_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_compile.setOnClickListener(this);
        LogUtil.d("PersonalCenter", "1、初始化View，获取数据");
        myPersonalCenterList = iPersonalCenterPresenter.getDates();
        if (!myPersonalCenterList.isEmpty()) {
            updateView(myPersonalCenterList);
        }
//        if (iPersonalCenterPresenter.isNetAvailable()){
//            LogUtil.d("PersonalCenter", "3、发起请求，请求数据");
//            iPersonalCenterPresenter.doRequest();
//        }else {
//            showNoNetworking();
//        }

    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalCenterActivity.this);
        builder.setTitle("提示");
        builder.setMessage("确认退出吗？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ActivityCollectorUtil.finishAll();
                //删除所用缓存
                mCache.clear();
                Intent intent = new Intent(PersonalCenterActivity.this, LoginActivity.class);
                startActivity(intent);
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
    public class MyHander extends Handler{
        WeakReference<PersonalCenterActivity> weakRefersence;
        public MyHander(PersonalCenterActivity activity) {
            weakRefersence =new  WeakReference<PersonalCenterActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.GET_NET_DATA:
                  //  LogUtil.d("pca", "5、收到通知，数据已经更新，拿数据，更新页面，执行updateView方法");
                    myPersonalCenterList =  iPersonalCenterPresenter.getDates();
                    updateView(myPersonalCenterList);
                    break;
                default:
                    break;
            }
        }
    }
//接口方法开始
    @Override
    public void updateView(ArrayList<MyPersonalCenter> myPersonalCenters) {
        if (!myPersonalCenters.isEmpty()){
            userName = myPersonalCenters.get(0).getName();
            tv_personal_nickname.setText(userName);
        }

    }

    @Override
    public void showNoNetworking() {
        Toast.makeText(PersonalCenterActivity.this, "没有网络连接，请检查网络", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRequestTimeout() {
        Toast.makeText(PersonalCenterActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();

    }
//接口方法结束
}
