package com.sxau.agriculture.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.LoginPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.ILoginPresenter;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

/**
 * 登录的activity
 * @author yawen_li
 */
public class LoginActivity extends BaseActivity implements ILoginActivty,View.OnClickListener{

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnRegister;

    private ILoginPresenter iLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //将LoginActiviyt与LoginPresenter进行绑定
        iLoginPresenter = new LoginPresenter(LoginActivity.this);

        initView();
    }

    private void initView(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnRegister = (Button) findViewById(R.id.btn_regist);
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

    }

    @Override
    public String getUsername() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showFailed() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                iLoginPresenter.doLogin();
                break;
            case R.id.btn_regist:
                break;
            default:
                break;
        }
    }
}
