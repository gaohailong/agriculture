package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;

/**
 * 具体问题、内容的详情activity
 * Created by Yawen_Li on 2016/4/13.
 */
public class TradeContentActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_content);
        Toast.makeText(TradeContentActivity.this,"click",Toast.LENGTH_LONG).show();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context,TradeContentActivity.class);
        context.startActivity(intent);
    }
}
