package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.squareup.picasso.Picasso;
import com.sxau.agriculture.adapter.BannerAdapter;
import com.sxau.agriculture.adapter.HomeArticlesAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IHomeArticleList;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.HomeBannerPicture;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.view.activity.WebViewActivity;
import com.sxau.agriculture.view.activity.WebViewTwoActivity;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 主界面的Fragment
 * 问题：
 * 1、连续点击底部"没有更多”会出现报错:解决思路：报错为数组越界异常估计思路和3一样。
 * 2、点击加载更多，若到底部会出现：先显示没有更多后出现列表添加：解决思路：当列表显示完成后，载改变文字的内容
 * 3、点击下拉刷新时候再点击item会报异常：解决思路：下拉刷新不允许点击
 *
 * @author 崔志泽
 */
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener, View.OnTouchListener {
    //其他
    private IHomePresenter iHomePresenter;
    private MyHandler myHandler;
    private Context context;
    //adapter部分
    private BannerAdapter bannerAdapter;
    private HomeArticlesAdapter adapter;
    //控件定义部分
    private TextView tv_title;
    private ListView lv_push;
    private View mView;
    private ViewPager vp_viewpager;
    private RefreshLayout rl_refresh;
    private TextView tv_more;
    private View footerLayout;
    private FrameLayout fl_adv;
    //常量及集合定义部分
    private ArrayList<String> imagePath;
    private ArrayList<HomeArticle> homeArticles;
    private ArrayList<ImageView> imageViews;
    private ArrayList<HomeBannerPicture> bannerData;
    private int currentIndex;
    private long lastTime;
    private int currentPage;
    private boolean isLoadOver;

    private DbUtils dbUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将HomeFragment与HomePresenter绑定起来
//        iHomePresenter = new HomePresenter(HomeFragment.this);
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, container, false);
            lv_push = (ListView) mView.findViewById(R.id.lv_push);

            rl_refresh = (RefreshLayout) mView.findViewById(R.id.srl_refresh);
            rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
            footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
            tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);
            tv_title = (TextView) mView.findViewById(R.id.tv_title);
            fl_adv = (FrameLayout) mView.findViewById(R.id.fl_adv);

            currentPage = 1;
            isLoadOver = false;
            currentIndex = 300;
            context = HomeFragment.this.getActivity();
            homeArticles = new ArrayList<HomeArticle>();
            myHandler = new MyHandler(HomeFragment.this);
            bannerData = new ArrayList<>();
            imagePath = new ArrayList<>();

            if (NetUtil.isNetAvailable(context)) {
                lv_push.setOnItemClickListener(this);
            }

            imagePath.add("error");
            dbUtil = DbUtils.create(context);

            initPictureView();
            initListView();
            initRefresh();
            myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOADINDG);
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void initRefresh() {
        lv_push.addFooterView(footerLayout);
        rl_refresh.setChildView(lv_push);
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myHandler.sendEmptyMessage(ConstantUtil.PULL_REFRESH);
            }
        });

        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
    }

    public void initListView() {
        adapter = new HomeArticlesAdapter(homeArticles, context);
        lv_push.setAdapter(adapter);
    }

    public void initPictureView() {
        tv_title.setText("数据加载中 请稍候……");
        vp_viewpager = (ViewPager) mView.findViewById(R.id.vp_viewpager);
        imageViews = new ArrayList<ImageView>();
        if (NetUtil.isNetAvailable(context)) {
            for (int i = 0; i < imagePath.size(); i++) {
                ImageView img = new ImageView(context);
                Picasso.with(context).load(imagePath.get(i)).resize(360, 200).centerCrop()
                        .placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(img);
                imageViews.add(img);
                myHandler.postDelayed(runnableForBanner, 2000);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                ImageView img = new ImageView(context);
                Picasso.with(context).load(R.mipmap.ic_phone_green_96px).resize(48, 48).centerCrop().into(img);
                imageViews.add(img);
            }
        }
        bannerAdapter = new BannerAdapter(imageViews, context);
        vp_viewpager.setOnTouchListener(this);
        vp_viewpager.setAdapter(bannerAdapter);
        vp_viewpager.setCurrentItem(300);
        vp_viewpager.setOnPageChangeListener(this);
    }

    public class MyHandler extends Handler {
        WeakReference<HomeFragment> weakReference;

        public MyHandler(HomeFragment fragment) {
            weakReference = new WeakReference<HomeFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    currentPage = 1;
                    if (NetUtil.isNetAvailable(context)) {
                        getHomeArticleData(String.valueOf(currentPage), String.valueOf(ConstantUtil.ITEM_NUMBER), true);
                        getHomeBannerData();
                    } else {
                        try {
                            Toast.makeText(context, "当前没有网络，请检查网络设置", Toast.LENGTH_LONG).show();
                            dbUtil.createTableIfNotExist(HomeArticle.class);
                            dbUtil.createTableIfNotExist(HomeBannerPicture.class);
                            getCacheData();
                            getPictureInfo();
                            initListView();
                            initPictureView();
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case ConstantUtil.GET_NET_DATA:
                    adapter.notifyDataSetChanged();
                    if (isLoadOver) {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
                    } else {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    }
                    break;
                case ConstantUtil.GET_PICTURE_DATA:
                    initPictureView();
                    break;
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
                    getHomeArticleData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                    getHomeBannerData();
                    rl_refresh.setRefreshing(false);
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    getHomeArticleData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                    rl_refresh.setLoading(false);
                    break;
                default:
                    break;
            }
        }
    }

    //获得首页文章列表网络的数据
    public void getHomeArticleData(String page, String pageSize, final boolean isRefresh) {
        Call<ArrayList<HomeArticle>> call = RetrofitUtil.getRetrofit().create(IHomeArticleList.class).getArticleList(page, pageSize);
        call.enqueue(new Callback<ArrayList<HomeArticle>>() {
            @Override
            public void onResponse(Response<ArrayList<HomeArticle>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<HomeArticle> homeArticles1 = response.body();
                    try {
                        dbUtil.deleteAll(HomeArticle.class);
                        if (isRefresh) {
                            homeArticles.clear();
                            homeArticles.addAll(homeArticles1);
                            dbUtil.saveAll(homeArticles1);
                            isLoadOver = false;
                        } else {
                            homeArticles.addAll(homeArticles1);
                            dbUtil.saveAll(homeArticles);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    if (homeArticles1.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        isLoadOver = true;
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_FAIL);
                if (currentPage > 1) {
                    rl_refresh.setRefreshing(false);
                    currentPage--;
                } else {
                    rl_refresh.setRefreshing(false);
                }
            }
        });
    }

    //获取轮播图网络数据
    public void getHomeBannerData() {
        Call<ArrayList<HomeBannerPicture>> pictureCall = RetrofitUtil.getRetrofit().create(IHomeArticleList.class).getPicturelist();
        pictureCall.enqueue(new Callback<ArrayList<HomeBannerPicture>>() {
            @Override
            public void onResponse(Response<ArrayList<HomeBannerPicture>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    bannerData = response.body();
                    if ((bannerData == null) || (bannerData.size() == 0)) {
                        imagePath.clear();
                        imagePath.add("error");
                        imagePath.add("error");
                        fl_adv.setVisibility(View.GONE);
                    } else {
                        try {
                            dbUtil.deleteAll(HomeBannerPicture.class);
                            dbUtil.saveAll(bannerData);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        getPictureInfo();
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_PICTURE_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                imagePath.clear();
                imagePath.add("error");
                imagePath.add("error");
//                fl_adv.setVisibility(View.GONE);
                myHandler.sendEmptyMessage(ConstantUtil.GET_PICTURE_DATA);
            }
        });
    }

    //拼接图片地址
    public void getPictureInfo() {
        imagePath.clear();
        for (int j = 0; j < bannerData.size(); j++) {
            String img = ConstantUtil.BASE_PICTURE_URL + bannerData.get(j).getImage();
            imagePath.add(img);
            Log.e("imgUrlGet", "" + img);
        }
    }

    //获取缓存数据
    public void getCacheData() {
        try {
            List<HomeArticle> list = dbUtil.findAll(HomeArticle.class);
            homeArticles = (ArrayList<HomeArticle>) list;
            List<HomeBannerPicture> listBanner = dbUtil.findAll(HomeBannerPicture.class);
            bannerData = (ArrayList<HomeBannerPicture>) listBanner;
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    // 设置轮播时间间隔
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
    public void onPageSelected(final int position) {
        currentIndex = position;
        lastTime = System.currentTimeMillis();
        //设置轮播文字改变
        final int index = position % imageViews.size();
        if (NetUtil.isNetAvailable(context)) {
            if (bannerData == null || bannerData.size() == 0) {
                tv_title.setText("暂无数据");
            } else {
                tv_title.setText(bannerData.get(index).getName());
                //TODO 轮播图点击事件
                imageViews.get(index).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentStart = new Intent();
                        int id = StringUtil.rotatePictureCut(bannerData.get(position % imageViews.size()).getUrl());
                        Log.e("getId",""+id);
                        intentStart.setClass(context, WebViewTwoActivity.class);
                        intentStart.putExtra("article", id);
                        startActivity(intentStart);
                    }
                });
            }
        } else {
            tv_title.setText("当前没有网络，请检查网络设置");
        }
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //解决下拉刷新与viewpager滑动冲突
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                rl_refresh.setEnabled(false);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                rl_refresh.setEnabled(true);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (homeArticles.size() > 0) {
            try {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArticleData", homeArticles.get(position));
                intent.putExtras(bundle);
                intent.setClass(context, WebViewActivity.class);
                startActivity(intent);
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(runnableForBanner);
    }
}
