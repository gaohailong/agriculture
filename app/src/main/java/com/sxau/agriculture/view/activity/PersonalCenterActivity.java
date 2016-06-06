package com.sxau.agriculture.view.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ActivityCollectorUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.fragment.PersonalCollectQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalCollectTradeFragment;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalTradeInfoFragment;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 个人中心Activity
 * @author 李秉龙
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager vTitlePaper;
    private ImageButton ib_back;
    private Button btn_compile;
    private Button btn_exit;
    private ACache mCache;
    private TextView tvUserName;
    private String userName;
    private String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);

        initView();

       FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
               .add(R.string.MyQuestion, PersonalQuestionFragment.class)
               .add(R.string.TradeInfo,PersonalTradeInfoFragment.class)
               .add(R.string.CollectQuestion,PersonalCollectQuestionFragment.class)
               .add(R.string.CollectTrade,PersonalCollectTradeFragment.class)
               .create() );
        vTitlePaper  = (ViewPager) findViewById(R.id.viewpager);
        vTitlePaper.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(vTitlePaper);
    }
    private void  initView(){
        mCache = ACache.get(PersonalCenterActivity.this);
        tvUserName = (TextView) this.findViewById(R.id.tv_personal_nickname);
        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);
        btn_exit = (Button) this.findViewById(R.id.btn_exit);

        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        userName = user.getName();
        phone = String.valueOf(user.getPhone());
        if (userName != null){
            tvUserName.setText(userName);
        }else {
            tvUserName.setText(phone);
        }

        ib_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        btn_compile.setOnClickListener(this);
    }
    private void showDialog(){
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
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_compile:
                Intent intent2 = new Intent();
                intent2.setClass(PersonalCenterActivity.this,PersonalCompileActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_exit:
                showDialog();
                break;
            default:
        }
    }
}
