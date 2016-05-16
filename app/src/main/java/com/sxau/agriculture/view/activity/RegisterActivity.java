package com.sxau.agriculture.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.RegisterPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IRegisterPresenter;
import com.sxau.agriculture.view.activity_interface.IRegisterActivity;

/**
 *注册的activity
 *@author yawen_li
 */
public class RegisterActivity extends BaseActivity implements IRegisterActivity ,View.OnClickListener{

    private EditText etUsername;
    private EditText etPassword;
    private EditText etAffirmPassword;
    private EditText etPhone;
    private Button btnRegister;
    private ProgressBar pbLogin;

    private IRegisterPresenter iRegisterPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //将RegActivity与RegPresenter绑定
        iRegisterPresenter = new RegisterPresenter(RegisterActivity.this);

        initView();

    }

    private void initView(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        etAffirmPassword = (EditText) findViewById(R.id.et_password2);
        etPhone = (EditText) findViewById(R.id.et_phone);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login);

        btnRegister = (Button) findViewById(R.id.btn_regist);
        btnRegister.setOnClickListener(this);
    }


    /**
     * 在正式提交请求前对用户输入的数据进行验证
     * 通过验证，提交请求
     * 验证失败，提示错误信息
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_regist:
                iRegisterPresenter.initData();
                if (iRegisterPresenter.isPasswordSame() && iRegisterPresenter.isPhoneEnable() && iRegisterPresenter.isUsernameEnable()){
                    iRegisterPresenter.doRegist();
                }else {
                    if (!iRegisterPresenter.isPhoneEnable()){
                        Toast.makeText(RegisterActivity.this,"手机号输入不正确，请重新输入",Toast.LENGTH_LONG).show();
                    }else if (!iRegisterPresenter.isUsernameEnable()){
                        Toast.makeText(RegisterActivity.this,"用户名的长度为3-12位，请重新输入",Toast.LENGTH_LONG).show();
                    }else if (!iRegisterPresenter.isPasswordEnable()){
                        Toast.makeText(RegisterActivity.this,"密码长度不得小于6位",Toast.LENGTH_LONG).show();
                    }else if (!iRegisterPresenter.isPasswordSame()){
                        Toast.makeText(RegisterActivity.this,"两次密码不一致",Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }

//----------------接口方法开始--------------
    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public String getAffirmPassword() {
        return etAffirmPassword.getText().toString();
    }

    @Override
    public String getPhone() {
        return etPhone.getText().toString();
    }

    /**
     * 点击注册后显示原形进度条等待服务器结果
     */
    @Override
    public void showProgress(int visibility) {
        pbLogin.setVisibility(visibility);
    }

    /**
     * 注册成功后进行UI显示更新
     */
    @Override
    public void showRegisteSucceed() {
        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
    }

    /**
     * 注册失败后进行UI显示更新
     */
    @Override
    public void showRegistFailed() {
        Toast.makeText(RegisterActivity.this,"用户名或手机号已经存在",Toast.LENGTH_LONG);
    }
//-----------------接口方法结束---------------------
}
