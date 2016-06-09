package com.sxau.agriculture.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.widgets.CityPicker;
import com.sxau.agriculture.widgets.CityPicker.OnSelectingListener;


/**
 * @author 李秉龙
 */
public class ThreeLevelLinkageActivity extends BaseActivity {
    private CityPicker cityPicker;
    private Button btn_cancel;
    private Button btn_finish;
    private String citystr;
    private String codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_level_linkage);
        cityPicker = (CityPicker) findViewById(R.id.citypicker);
        btn_cancel = (Button) this.findViewById(R.id.btn_cancel);
        btn_finish = (Button) this.findViewById(R.id.btn_finish);
        cityPicker.setOnSelectingListener(new OnSelectingListener() {

            @Override
            public void selected(boolean selected) {
                // TODO Auto-generated method stub
                citystr = cityPicker.getCity_string();
                codeStr = cityPicker.getCity_code_string();
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", citystr);
                //设置回传的意图p
                setResult(1001, intent);
                finish();

            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
