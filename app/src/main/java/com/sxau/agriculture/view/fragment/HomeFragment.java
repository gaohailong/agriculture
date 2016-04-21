package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.sxau.agriculture.adapter.BannerAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.adapter.HomePushAdapter;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener {




/*


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /* @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        *//* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*//*
    }*/

    private BannerAdapter bannerAdapter;
    private ListView lv_push;
    private View mView;
    private HomePushAdapter adapter;
    private ViewPager vp_viewpager;
    private LinearLayout ll_point;
    private int[] imagePath;
    private int currentIndex = 300;
    private ArrayList<ImageView> views;
    private Context context;
    private long lastTime;
    private SwipeRefreshLayout srl_refresh;
    private Handler handlerForBanner = new Handler();
    private Handler handlerForRefresh;

    /*
    设置轮播时间间隔
     */
    private Runnable runnableForBanner = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - lastTime >= 3000) {
                currentIndex++;
                vp_viewpager.setCurrentItem(currentIndex);
                lastTime = System.currentTimeMillis();
            }
            handlerForBanner.postDelayed(runnableForBanner, 3000);
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        lv_push = (ListView) mView.findViewById(R.id.lv_push);
        srl_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);
        handlerForRefresh = new Handler();
        context = HomeFragment.this.getActivity();

        srl_refresh.setOnRefreshListener(this);

        initView();

        PushBean[] pushs = new PushBean[6];
        pushs[0] = new PushBean();
        pushs[0].setRead("12万");
        pushs[0].setBrowseid(R.drawable.ic_read_48px);
        pushs[0].setNewsid(R.drawable.phone_48dp);
        pushs[0].setTime("2016.4.9");
        pushs[0].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[1] = new PushBean();
        pushs[1].setRead("12万");
        pushs[1].setBrowseid(R.drawable.ic_read_48px);
        pushs[1].setNewsid(R.drawable.phone_48dp);
        pushs[1].setTime("2016.4.9");
        pushs[1].setTitle("全面推动“三品一标”工作啦啦啦再上新台阶");

        pushs[2] = new PushBean();
        pushs[2].setRead("12万");
        pushs[2].setBrowseid(R.drawable.ic_read_48px);
        pushs[2].setNewsid(R.drawable.phone_48dp);
        pushs[2].setTime("2016.4.9");
        pushs[2].setTitle("帅子是煞笔");

        pushs[3] = new PushBean();
        pushs[3].setRead("12万");
        pushs[3].setBrowseid(R.drawable.ic_read_48px);
        pushs[3].setNewsid(R.drawable.phone_48dp);
        pushs[3].setTime("2016.4.9");
        pushs[3].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[4] = new PushBean();
        pushs[4].setRead("12万");
        pushs[4].setBrowseid(R.drawable.ic_read_48px);
        pushs[4].setNewsid(R.drawable.phone_48dp);
        pushs[4].setTime("2016.4.9");
        pushs[4].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[5] = new PushBean();
        pushs[5].setRead("12万");
        pushs[5].setBrowseid(R.drawable.ic_read_48px);
        pushs[5].setNewsid(R.drawable.phone_48dp);
        pushs[5].setTime("2016.4.9");
        pushs[5].setTitle("全面推动“三品一标”工作再上新台阶");

        adapter = new HomePushAdapter(pushs, context);
        lv_push.setAdapter(adapter);
        return mView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerForBanner.removeCallbacks(runnableForBanner);
    }
    /*
    加载图片与小圆点
     */
    public void initView() {
        vp_viewpager = (ViewPager) mView.findViewById(R.id.vp_viewpager);
        ll_point = (LinearLayout) mView.findViewById(R.id.ll_point);
        imagePath = new int[]{R.drawable.img_banner_red, R.drawable.img_banner_white,R.drawable.img_banner_red, R.drawable.img_banner_white, R.drawable.img_banner_red};
        views = new ArrayList<ImageView>();

        for (int i = 0; i < imagePath.length; i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(imagePath[i]);
            views.add(img);
            //圈
            ImageView imgCircle = new ImageView(context);
            imgCircle.setImageResource(R.drawable.img_banner_white);//默认不中
            imgCircle.setPadding(10, 5, 10, 5);
            ll_point.addView(imgCircle);
        }


        bannerAdapter = new BannerAdapter(views, context);
        vp_viewpager.setAdapter(bannerAdapter);
        vp_viewpager.setCurrentItem(300);
        vp_viewpager.setOnPageChangeListener(this);
        handlerForBanner.postDelayed(runnableForBanner, 2000);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    /*
    得到当前图片并记录当前时间
     */
    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        int index = position % views.size();
        setCurrentSelector(index);
        lastTime = System.currentTimeMillis();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /*
    设置小圆点的变色
     */
    public void setCurrentSelector(int index) {
        for (int i = 0; i < ll_point.getChildCount(); i++) {
            ImageView child = (ImageView) ll_point.getChildAt(i);
            if (i == index) {
                child.setImageResource(R.drawable.img_banner_red);
            } else {
                child.setImageResource(R.drawable.img_banner_white);
            }
        }
    }
    /*
    下拉刷新
     */

    @Override
    public void onRefresh() {
        srl_refresh.setRefreshing(true);
//        refreshContent();
        handlerForRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                srl_refresh.setRefreshing(false);
            }
        }, 2000);
    }
}
