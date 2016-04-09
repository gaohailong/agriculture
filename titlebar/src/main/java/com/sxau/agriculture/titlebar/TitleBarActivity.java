package com.sxau.agriculture.titlebar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class TitleBarActivity extends AppCompatActivity {

    private ImageView mCollectView;
 //   private boolean mIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_bar);
        final TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        //左边
        titleBar.setBackgroundColor(Color.parseColor("#64b4ff"));
        titleBar.setLeftImageResource(R.mipmap.default_user_portrait);
      //  titleBar.setLeftText("返回");
        titleBar.setLeftTextColor(Color.WHITE);
        titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //中间
        titleBar.setTitle("文章详情");
        titleBar.setTitleColor(Color.WHITE);
        titleBar.setSubTitleColor(Color.WHITE);
        titleBar.setDividerColor(Color.GRAY);//设置边线颜色
        titleBar.setActionTextColor(Color.WHITE);
        //设置右边图片
        mCollectView = (ImageView) titleBar.addAction(new TitleBar.ImageAction(R.mipmap.collect) {
            @Override
            public void performAction(View view) {
                Toast.makeText(TitleBarActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
                mCollectView.setImageResource(R.mipmap.fabu);
            //    titleBar.setTitle(mIsSelected ? "文章详情\n朋友圈" : "帖子详情");
              //  mIsSelected = !mIsSelected;
            }
        });
        //设置右边文字
        titleBar.addAction(new TitleBar.TextAction("发布") {
            @Override
            public void performAction(View view) {
                Toast.makeText(TitleBarActivity.this, "点击了发布", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
