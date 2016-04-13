package com.sxau.agriculture.view.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class PresonalCenterActivity extends FragmentActivity {
    private ViewPager vPager = null;
    private TextView text1,text2;
    private List<View> viewlist;
    private View MyQusetionView,TradeInfoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);
        vPager = (ViewPager) findViewById(R.id.vPager);
        LayoutInflater inflater = getLayoutInflater();
        text1 = (TextView) this.findViewById(R.id.text1);
        text2 = (TextView) this.findViewById(R.id.text2);
        MyQusetionView = inflater.inflate(R.layout.frament_personal_myquestion,null);
        TradeInfoView = inflater.inflate(R.layout.frament_personal_tradeinfo,null);

        viewlist = new ArrayList<View>();
        viewlist.add(MyQusetionView);
        viewlist.add(TradeInfoView);

        PagerAdapter pagerAdapter = new PagerAdapter() {


            @Override
            public int getCount() {
                return viewlist.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewlist.get(position));
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewlist.get(position));
                return viewlist.get(position);
            }
        };
        vPager.setAdapter(pagerAdapter);

    }
}
