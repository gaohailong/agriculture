package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.sxau.agriculture.adapter.BannerAdapter;
import com.sxau.agriculture.agriculture.R;

import com.sxau.agriculture.adapter.HomePushAdapter;
import com.sxau.agriculture.api.IHomeArticleList;
import com.sxau.agriculture.api.IHomeRotatePicture;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.HomeRotatePicture;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;


import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 主界面的Fragment
 *
 * @author 崔志泽
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener, View.OnTouchListener {

    private IHomePresenter iHomePresenter;
    private HomeRotatePicture homeRotatePicture;
    private BannerAdapter bannerAdapter;
    private ListView lv_push;
    private View mView;
    private HomePushAdapter adapter;
    private ViewPager vp_viewpager;
    private SwipeRefreshLayout srl_refresh;
    private Context context;

    private int[] imagePath;
    private ArrayList<HomeArticle> homeArticles;
    private ArrayList<ImageView> views;
    private int currentIndex = 300;
    private long lastTime;
    private MyHandler myHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将HomeFragment与HomePresenter绑定起来
//        iHomePresenter = new HomePresenter(HomeFragment.this);

        mView = inflater.inflate(R.layout.fragment_home, container, false);

        lv_push = (ListView) mView.findViewById(R.id.lv_push);
        srl_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);

        myHandler = new MyHandler();
        context = HomeFragment.this.getActivity();

        srl_refresh.setOnRefreshListener(this);
        srl_refresh.setColorSchemeColors(R.color.yellow, R.color.colorPrimary);
        lv_push.setOnItemClickListener(this);

        homeArticles = new ArrayList<HomeArticle>();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListView();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    getHomeArticleData();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    initListView();
                    break;
            }
        }
    }

    //获得首页文章列表网络的数据
    public void getHomeArticleData() {
        Call<ArrayList<HomeArticle>> call = RetrofitUtil.getRetrofit().create(IHomeArticleList.class).getArticleList();
        call.enqueue(new Callback<ArrayList<HomeArticle>>() {
            @Override
            public void onResponse(Response<ArrayList<HomeArticle>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    homeArticles = response.body();
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public void initListView() {
        adapter = new HomePushAdapter(homeArticles, context);
        lv_push.setAdapter(adapter);
    }

    /**
     * 加载图片与小圆点
     */
    public void initView() {
        vp_viewpager = (ViewPager) mView.findViewById(R.id.vp_viewpager);
        imagePath = new int[]{R.drawable.img_banner_one, R.drawable.img_banner_two, R.drawable.img_banner_three, R.drawable.img_banner_four, R.drawable.img_banner_five};
        views = new ArrayList<ImageView>();

        for (int i = 0; i < imagePath.length; i++) {
            ImageView img = new ImageView(context);
            Picasso.with(context).load(imagePath[i]).resize(360, 200).centerCrop().into(img);
            views.add(img);
        }

        bannerAdapter = new BannerAdapter(views, context);
        vp_viewpager.setOnTouchListener(this);
        vp_viewpager.setAdapter(bannerAdapter);
        vp_viewpager.setCurrentItem(300);
        vp_viewpager.setOnPageChangeListener(this);
        myHandler.postDelayed(runnableForBanner, 2000);
    }


    /**
     * 设置轮播时间间隔
     */
    private Runnable runnableForBanner = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - lastTime >= 3000) {
                currentIndex++;
                vp_viewpager.setCurrentItem(currentIndex);
                lastTime = System.currentTimeMillis();
            }
            myHandler.postDelayed(runnableForBanner, 3000);
        }
    };

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        lastTime = System.currentTimeMillis();

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        srl_refresh.setRefreshing(true);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                srl_refresh.setRefreshing(false);
            }
        }, 2000);
    }


    /**
     * 解决下拉刷新与viewpager滑动冲突
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                srl_refresh.setEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                srl_refresh.setEnabled(true);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(context, homeArticles.get(position).getTitle(), Toast.LENGTH_SHORT).show();
      /*  Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.oschina.net/question/1420591_137134?sort=time"));
        startActivity(openUrlIntent);*/
    }


    /**
     * 网络请求的方法
     */
    public void initRotatePicture() {
        Call<HomeRotatePicture> call = RetrofitUtil.getRetrofit().create(IHomeRotatePicture.class).getResult();
        call.enqueue(new Callback<HomeRotatePicture>() {
            @Override
            public void onResponse(Response<HomeRotatePicture> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    homeRotatePicture = response.body();
                    if (homeRotatePicture != null) {
                        myHandler.sendEmptyMessage(0);//待定
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(runnableForBanner);
    }

}
