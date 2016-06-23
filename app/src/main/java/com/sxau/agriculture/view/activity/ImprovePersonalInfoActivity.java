package com.sxau.agriculture.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.ImprovePersonalInfoPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IImprovePersonalInfoPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity_interface.IImprovePersonalInfoActivity;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.widgets.CityPicker;

import java.lang.ref.WeakReference;

/**
 * 注册第二步，完善个人信息activity
 *
 * @author Yawen_Li
 */

public class ImprovePersonalInfoActivity extends BaseActivity implements IImprovePersonalInfoActivity, View.OnClickListener {

    private TextView tvAddress;
    private EditText etRealName;
    private EditText etIndustry;
    private EditText etScale;
    private Button btnConfirm;
    private MyHandler myHandler;

    private String citystr;
    private ProgressDialog pdWaiting;

    private IImprovePersonalInfoPresenter iImprovePersonalInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_improve_personal_info);

        myHandler = new MyHandler(ImprovePersonalInfoActivity.this);

        //将ImproveActivity与ImprovePresenter绑定
        iImprovePersonalInfoPresenter = new ImprovePersonalInfoPresenter(ImprovePersonalInfoActivity.this, myHandler);
        initView();
    }

    private void initView() {
        tvAddress = (TextView) findViewById(R.id.tv_address);
        etIndustry = (EditText) findViewById(R.id.et_industry);
        etRealName = (EditText) findViewById(R.id.et_realName);
        etScale = (EditText) findViewById(R.id.et_scale);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        pdWaiting = new ProgressDialog(ImprovePersonalInfoActivity.this);
        pdWaiting.setMessage("完善信息中...");
        pdWaiting.setCanceledOnTouchOutside(false);
        pdWaiting.setCancelable(true);


        btnConfirm.setOnClickListener(this);
        tvAddress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                iImprovePersonalInfoPresenter.initData();
                if (iImprovePersonalInfoPresenter.isAddressAvailable() && iImprovePersonalInfoPresenter.isRealNameAvailable()) {
                    showProgress(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1500);
                                iImprovePersonalInfoPresenter.submitPersonalData();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } else if (!iImprovePersonalInfoPresenter.isRealNameAvailable()) {
                    Toast.makeText(ImprovePersonalInfoActivity.this, "请填写真实姓名", Toast.LENGTH_LONG).show();
                } else if (!iImprovePersonalInfoPresenter.isAddressAvailable()) {
                    Toast.makeText(ImprovePersonalInfoActivity.this, "请填写地址信息", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.tv_address:
                showAddressSelector();
                break;
            default:
                break;
        }
    }

    //地址选择弹窗
    public void showAddressSelector() {
        View view = getLayoutInflater().inflate(R.layout.activity_three_level_linkage, null);
        TextView btn_cancel = (TextView) view.findViewById(R.id.btn_cancel);
        TextView btn_finish = (TextView) view.findViewById(R.id.btn_finish);
        final CityPicker cityPicker = (CityPicker) view.findViewById(R.id.citypicker);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setAnimationStyle(android.R.style.Animation_Translucent);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_pop_alert));

        //点击窗口外边消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        //显示位置
        popupWindow.showAtLocation(tvAddress, Gravity.BOTTOM, 0, 0);
        cityPicker.setOnSelectingListener(new CityPicker.OnSelectingListener() {
            @Override
            public void selected(boolean selected) {
                citystr = cityPicker.getCity_string();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 获取最终的数据citystr
                tvAddress.setText(citystr);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    public class MyHandler extends Handler {
        WeakReference<ImprovePersonalInfoActivity> weakReference;

        public MyHandler(ImprovePersonalInfoActivity activity) {
            weakReference = new WeakReference<ImprovePersonalInfoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    Intent intent = new Intent(ImprovePersonalInfoActivity.this,MainActivity.class);
                    ImprovePersonalInfoActivity.this.startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }



    //--------------------接口方法开始--------------------------
    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            pdWaiting.show();
        } else {
            pdWaiting.cancel();
        }
    }
    @Override
    public String getAddress() {
        return tvAddress.getText().toString();
    }

    @Override
    public String getRealName() {
        return etRealName.getText().toString();
    }

    @Override
    public String getIndustry() {
        return etIndustry.getText().toString();
    }

    @Override
    public String getScale() {
        return etScale.getText().toString();
    }

    /**
     * 提示更新成功
     */
    @Override
    public void showSuccess() {
        Toast.makeText(ImprovePersonalInfoActivity.this, "完善信息成功", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailed() {
        Toast.makeText(ImprovePersonalInfoActivity.this, "完善信息失败", Toast.LENGTH_LONG).show();
    }
//-------------------接口方法结束------------------------
}
