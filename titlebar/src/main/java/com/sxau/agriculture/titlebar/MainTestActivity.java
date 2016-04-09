package com.sxau.agriculture.titlebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        new TitleBuilder(this).setLeftImage(R.mipmap.default_user_portrait).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainTestActivity.this, "ddddd", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
