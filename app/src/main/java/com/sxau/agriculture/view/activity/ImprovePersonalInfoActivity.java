package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.ImprovePersonalInfoPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IImprovePersonalInfoPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.view.activity_interface.IImprovePersonalInfoActivity;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;

import java.lang.ref.WeakReference;

/**
 * 注册第二步，完善个人信息activity
 *
 * @author Yawen_Li
 */

public class ImprovePersonalInfoActivity extends BaseActivity implements IImprovePersonalInfoActivity, View.OnClickListener {

    private EditText etAddress;
    private EditText etRealName;
    private EditText etIndustry;
    private EditText etScale;
    private Button btnConfirm;
    private MyHandler myHandler;

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
        etAddress = (EditText) findViewById(R.id.et_address);
        etIndustry = (EditText) findViewById(R.id.et_industry);
        etRealName = (EditText) findViewById(R.id.et_realName);
        etScale = (EditText) findViewById(R.id.et_scale);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        btnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                iImprovePersonalInfoPresenter.initData();
                if (iImprovePersonalInfoPresenter.isAddressAvailable() && iImprovePersonalInfoPresenter.isRealNameAvailable()) {
                    iImprovePersonalInfoPresenter.submitPersonalData();
                } else if (!iImprovePersonalInfoPresenter.isRealNameAvailable()) {
                    Toast.makeText(ImprovePersonalInfoActivity.this, "请填写真实姓名", Toast.LENGTH_LONG).show();
                } else if (!iImprovePersonalInfoPresenter.isAddressAvailable()) {
                    Toast.makeText(ImprovePersonalInfoActivity.this, "请填写地址信息", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
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
    public String getAddress() {
        return etAddress.getText().toString();
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
