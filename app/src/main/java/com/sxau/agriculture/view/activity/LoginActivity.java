package com.sxau.agriculture.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

public class LoginActivity extends BaseActivity implements ILoginActivty{

    private EditText etUsername;
    private EditText etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
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
}
