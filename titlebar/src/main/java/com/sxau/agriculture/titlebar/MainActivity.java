package com.sxau.agriculture.titlebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TopBarUtil topBar= (TopBarUtil) findViewById(R.id.TopBar);
       /* topBar.setLeftImageIsVisible(true);
        topBar.setLeftImage(R.mipmap.default_user_portrait);
*/
        topBar.setLeftRoundImageIsVisible(true);
        topBar.setLeftRoundImage(R.mipmap.default_user_portrait);

        topBar.setTitleIsVisible(true);
        topBar.setContent("文章");

        topBar.setRightImageIsVisible(true);
        topBar.setRightImage(R.mipmap.default_user_portrait);
        topBar.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {
                Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickLeftImage() {
                Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickRightImage() {
                Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
            }
        });
    }


  /*  @Override
    public void onClickLeftRoundImage() {
        Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickLeftImage() {
        Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickRightImage() {
        Toast.makeText(MainActivity.this,"天哪",Toast.LENGTH_SHORT).show();
    }*/
}
