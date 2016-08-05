package com.sxau.agriculture.view.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.TitleBarTwo;

/**
 * @author gaohailong
 */
public class SearchArticleActivity extends AppCompatActivity {
    private TitleBarTwo topBarUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_article);
        topBarUtil = (TitleBarTwo) findViewById(R.id.top_search);
        topBarUtil.setBackgroundColor(Color.parseColor("#00b5ad"));
        topBarUtil.setLeftImageResource(R.mipmap.ic_back_left);
        topBarUtil.setLeftTextColor(Color.WHITE);
        topBarUtil.setDividerColor(Color.GRAY);
        topBarUtil.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBarUtil.setTitle("搜索文章");
        topBarUtil.setTitleColor(Color.WHITE);
    }
}
