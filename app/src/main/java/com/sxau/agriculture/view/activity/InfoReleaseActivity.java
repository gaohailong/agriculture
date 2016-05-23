package com.sxau.agriculture.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.sxau.agriculture.agriculture.R;

/**
 * Info发布供求界面
 * @author 田帅.
 */
public class InfoReleaseActivity extends BaseActivity{
    private ImageView ivPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_release);
        initView();

    }
    public void initView(){
        ivPhoto= (ImageView) findViewById(R.id.iv_info_release_photo);
    }
}
