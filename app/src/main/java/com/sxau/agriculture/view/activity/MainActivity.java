package com.sxau.agriculture.view.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.update.UpdateChecker;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ActivityCollectorUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.JPushUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.fragment.HomeFragment;
import com.sxau.agriculture.view.fragment.TradeFragment;
import com.sxau.agriculture.view.fragment.MessageFragment;
import com.sxau.agriculture.view.fragment.QuestionFragment;


import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 主界面的activity
 *
 * @author 高海龙
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private FragmentTabHost fragmentTabHost;
    private RadioGroup radioGroup;

    private Context context;
    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;
    private final Class[] fragments = {HomeFragment.class, QuestionFragment.class, TradeFragment.class, MessageFragment.class};
    private int flag = 0;
    private String phoneNumber;
    private static final String TAG = "JPush";
    private static final int MSG_SET_ALIAS = 1001;
    private TitleBarTwo titleBarTwo;

    public static boolean isForeground = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniTitle();
        initView();
        registerMessageReceiver();  // used for receive msg
        init();
        setAlias();
    }

    private void initView() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);

        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(i + "").setIndicator(i + "");
            fragmentTabHost.addTab(tabSpec, fragments[i], null);
        }
        radioGroup.setOnCheckedChangeListener(this);
        fragmentTabHost.setCurrentTab(0);
        //TODO 设置自动更新
        UpdateChecker.checkForDialog(MainActivity.this, ConstantUtil.APP_UPDATE_SERVER_URL);
    }

    private void iniTitle() {
        TopBarUtil topBar = (TopBarUtil) findViewById(R.id.topBar);
        topBar.setLeftRoundImageIsVisible(false);
        topBar.setLeftImageIsVisible(true);

        if (flag == 0) {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_phone_white);
        } else {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_search);
        }
        topBar.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickLeftImage() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickRightImage() {
                if (flag == 0) {
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.dialog_phone, null);
                    aBuilder.setView(view);
                    LinearLayout ll_agriculture = (LinearLayout) view.findViewById(R.id.ll_agriculture);
                    LinearLayout ll_healthy = (LinearLayout) view.findViewById(R.id.ll_healthy);
                    ll_agriculture.setOnClickListener(MainActivity.this);
                    ll_healthy.setOnClickListener(MainActivity.this);
                    aBuilder.show();
                } else {
                    //TODO 将搜索功能写到这
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_agriculture:
                phoneNumber = "tel:0351-7537092";
                callPhone(v, phoneNumber);
                break;
            case R.id.ll_healthy:
                phoneNumber = "tel:0791-86665536";
                callPhone(v, phoneNumber);
                break;
            default:
                break;
        }
    }

    //打电话
    public void callPhone(View view, String phoneNumber) {
        Uri data = Uri.parse(phoneNumber);
        String action = Intent.ACTION_DIAL;
        Intent intent = new Intent(action, data);
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                fragmentTabHost.setCurrentTab(0);
                flag = 0;
                iniTitle();
                break;
            case R.id.rb_queston:
                fragmentTabHost.setCurrentTab(1);
                flag = 0;
                iniTitle();
                break;
            case R.id.rb_info:
                fragmentTabHost.setCurrentTab(2);
                flag = 0;
                iniTitle();
                break;
            case R.id.rb_message:
                fragmentTabHost.setCurrentTab(3);
                flag = 0;
                iniTitle();
                break;
            default:
                break;
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init() {
        JPushInterface.init(getApplicationContext());
    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                String messge = intent.getStringExtra(KEY_MESSAGE);
                String extras = intent.getStringExtra(KEY_EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                if (!JPushUtil.isEmpty(extras)) {
                    showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCollectorUtil.finishAll();
            finish();
        }
    }


    //JPush方法别名的设置
    private void setAlias() {
        ACache aCache = ACache.get(MainActivity.this);
        User user = (User) aCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (!JPushUtil.isValidTagAndAlias(String.valueOf(user.getPhone()))) {
            return;
        }
        //调用JPush API设置Alias
        handler.sendMessage(handler.obtainMessage(MSG_SET_ALIAS, user.getPhone()
        ));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(MainActivity.this, (String) msg.obj, null, mAliasCallback);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
//            String logs;
            switch (code) {
                case 0:
//                    logs = "Set tag and alias success";
//                    Log.i(TAG, logs);
                    break;
                case 6002:
                  /*  logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);*/
                    if (JPushUtil.isConnected(AgricultureApplication.getContext())) {
                        handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
//                        Log.i(TAG, "No network");
                    }
                    break;
                default:
                  /*  logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);*/
            }
//            JPushUtil.showToast(logs, AgricultureApplication.getContext());
        }
    };
}
