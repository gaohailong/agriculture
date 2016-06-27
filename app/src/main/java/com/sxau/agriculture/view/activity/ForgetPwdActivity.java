package com.sxau.agriculture.view.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.ForgetPwdPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IForgetPwdPresenter;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.TimerCount;
import com.sxau.agriculture.view.activity_interface.IForgetPwdActivity;
import com.sxau.agriculture.view.activity_interface.IForgetPwdActivity;

/**
 * 找回密码activity
 *
 * @author yawen_li
 */
public class ForgetPwdActivity extends BaseActivity implements IForgetPwdActivity, View.OnClickListener {

    private static long CHECKTIME = 60000;      //60秒后可重新获取验证码
    private EditText etUsername;
    private EditText etPassword;
    private EditText etPhone;
    private EditText etCheckNum;
    private Button btnSubmit;
    private Button btnGetCheckNum;
    private ProgressDialog pdSubmitwait;
    private TimerCount timer;

    private IForgetPwdPresenter iForgetPwdPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpwd);

        //将RegActivity与RegPresenter绑定
        iForgetPwdPresenter = new ForgetPwdPresenter(ForgetPwdActivity.this);

        initView();

    }

    private void initView() {
        etPassword = (EditText) findViewById(R.id.et_password);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etCheckNum = (EditText) findViewById(R.id.et_checknum);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnGetCheckNum = (Button) findViewById(R.id.btn_getchecknum);
        timer = new TimerCount(CHECKTIME, 1000, btnGetCheckNum);

        pdSubmitwait = new ProgressDialog(ForgetPwdActivity.this);
        pdSubmitwait.setMessage("提交中...");
        pdSubmitwait.setCanceledOnTouchOutside(false);
        pdSubmitwait.setCancelable(true);

        btnSubmit.setOnClickListener(this);
        btnGetCheckNum.setOnClickListener(this);
    }


    /**
     * 在正式提交请求前对用户输入的数据进行验证
     * 通过验证，提交请求
     * 验证失败，提示错误信息
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                boolean isNetAvailable = NetUtil.isNetAvailable(ForgetPwdActivity.this);
                iForgetPwdPresenter.initData();
                //输入验证
                if ( iForgetPwdPresenter.isPhoneEnable() && isNetAvailable && iForgetPwdPresenter.isCheckNumEnable()) {
                    iForgetPwdPresenter.doUpdata();
                } else {
                    //输入验证出错，显示对应信息
                    if (!iForgetPwdPresenter.isPhoneEnable()) {
                        Toast.makeText(ForgetPwdActivity.this, "手机号输入不正确，请重新输入", Toast.LENGTH_LONG).show();
                    } else if (!iForgetPwdPresenter.isPasswordEnable()) {
                        Toast.makeText(ForgetPwdActivity.this, "密码长度不得小于6位", Toast.LENGTH_LONG).show();
                    } else if (!iForgetPwdPresenter.isCheckNumEnable()) {
                        Toast.makeText(ForgetPwdActivity.this, "验证码格式错误", Toast.LENGTH_LONG).show();
                    }else if (!isNetAvailable){
                        Toast.makeText(ForgetPwdActivity.this,"没有网络连接，请检查网络",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.btn_getchecknum:
                if(NetUtil.isNetAvailable(ForgetPwdActivity.this)){
                    iForgetPwdPresenter.initData();
                    if (iForgetPwdPresenter.isPhoneEnable()) {
                        ChangeBtnUi();
                        iForgetPwdPresenter.sendPhoneRequest();
                    }else {
                        Toast.makeText(ForgetPwdActivity.this, "手机号输入不正确，请重新输入", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(ForgetPwdActivity.this,"没有网络连接，请检查网络",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 更改获取验证码按钮
     */
    private void ChangeBtnUi() {
        timer.start();
    }

    //----------------接口方法开始--------------

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }


    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    @Override
    public String getCheckNum() {
        return etCheckNum.getText().toString();
    }

    /**
     * 点击注册后显示原形进度条等待服务器结果
     */
    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            pdSubmitwait.show();
        } else {
            pdSubmitwait.cancel();
        }
    }

    /**
     * 注册成功后进行UI显示更新
     */
    @Override
    public void showFindPwdSucceed() {
        Toast.makeText(ForgetPwdActivity.this, "密码更新成功，请牢记您的新密码", Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册失败后进行UI显示更新
     */
    @Override
    public void showFindPwdFailed() {
        Toast.makeText(ForgetPwdActivity.this, "手机号未注册，更新密码失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRequestTimeout() {
        Toast.makeText(ForgetPwdActivity.this, "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishForgetPwdActivity() {
        ForgetPwdActivity.this.finish();
    }
//-----------------接口方法结束---------------------
}
