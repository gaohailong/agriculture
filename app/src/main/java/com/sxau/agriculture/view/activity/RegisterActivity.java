package com.sxau.agriculture.view.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.RegisterPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IRegisterPresenter;
import com.sxau.agriculture.view.activity_interface.IRegisterActivity;

public class RegisterActivity extends BaseActivity implements IRegisterActivity {

    private EditText etUsername;
    private EditText etPassword;
    private EditText etAffirmPassword;
    private Button btnRegister;

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

        btnRegister = (Button) findViewById(R.id.btn_regist);
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
    public String getAffirmPassword() {
        return etAffirmPassword.getText().toString();
    }

    @Override
    public void showIsRegister() {

    }

    @Override
    public void showIsPwdsame() {

    }
}
