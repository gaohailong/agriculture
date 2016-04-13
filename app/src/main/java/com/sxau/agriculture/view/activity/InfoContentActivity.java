package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;

/**
 * Created by Yawen_Li on 2016/4/13.
 */
public class InfoContentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_content);
        Toast.makeText(InfoContentActivity.this,"click",Toast.LENGTH_LONG).show();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,InfoContentActivity.class);
        context.startActivity(intent);
    }
}
