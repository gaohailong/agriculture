package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.widgets.CityPicker;
import com.sxau.agriculture.widgets.CityPicker.OnSelectingListener;


/**
 *地区三级联动
 * //TODO 最终可以删除这个类
 * @author 李秉龙
 */
public class ThreeLevelLinkageActivity extends BaseActivity {
    private CityPicker cityPicker;
    private TextView btn_cancel;
    private TextView btn_finish;
    private String citystr;
    private String codeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_level_linkage);
        cityPicker = (CityPicker) findViewById(R.id.citypicker);
        btn_cancel = (TextView) this.findViewById(R.id.btn_cancel);
        btn_finish = (TextView) this.findViewById(R.id.btn_finish);
        cityPicker.setOnSelectingListener(new OnSelectingListener() {

            @Override
            public void selected(boolean selected) {
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
