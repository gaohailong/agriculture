package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;
import com.sxau.agriculture.adapter.BannerAdapter;
import com.sxau.agriculture.agriculture.R;

import com.sxau.agriculture.adapter.HomePushAdapter;
import com.sxau.agriculture.api.IHomeArticleList;
import com.sxau.agriculture.api.IHomeRotatePicture;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.HomeRotatePicture;
import com.sxau.agriculture.presenter.fragment_presenter.HomePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.widgets.RefreshLayout;


import java.lang.ref.WeakReference;
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
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, AdapterView.OnItemClickListener, View.OnTouchListener {
    //其他
    private IHomePresenter iHomePresenter;
    private MyHandler myHandler;
    private Context context;
    private ACache aCache;
    //adapter部分
    private BannerAdapter bannerAdapter;
    private HomePushAdapter adapter;
    //控件定义部分
    private ListView lv_push;
    private View mView;
    private ViewPager vp_viewpager;
    private RefreshLayout rl_refresh;
    private TextView tv_more;
    private View footerLayout;
    //常量及集合定义部分
    private HomeRotatePicture homeRotatePicture;
    private int[] imagePath;
    private ArrayList<HomeArticle> homeArticles;
    private ArrayList<ImageView> imageViews;
    private int currentIndex;
    private long lastTime;
    private int currentPage;
    private boolean isLoadOver;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将HomeFragment与HomePresenter绑定起来
//        iHomePresenter = new HomePresenter(HomeFragment.this);

        mView = inflater.inflate(R.layout.fragment_home, container, false);
        lv_push = (ListView) mView.findViewById(R.id.lv_push);

        rl_refresh = (RefreshLayout) mView.findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        footerLayout = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footerLayout.findViewById(R.id.tv_more);

        lv_push.setOnItemClickListener(this);

        currentPage = 1;
        isLoadOver = false;
        currentIndex = 300;
        context = HomeFragment.this.getActivity();
        homeArticles = new ArrayList<HomeArticle>();
        myHandler = new MyHandler(HomeFragment.this);

        aCache = ACache.get(context);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPictureView();
        initRefresh();
        initListView();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);

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
        adapter = new HomePushAdapter(homeArticles, context);
        lv_push.setAdapter(adapter);
    }

    public void initPictureView() {
        vp_viewpager = (ViewPager) mView.findViewById(R.id.vp_viewpager);
        imagePath = new int[]{R.drawable.img_banner_one, R.drawable.img_banner_two, R.drawable.img_banner_three, R.drawable.img_banner_four, R.drawable.img_banner_five};
        imageViews = new ArrayList<ImageView>();

        for (int i = 0; i < imagePath.length; i++) {
            ImageView img = new ImageView(context);
            Picasso.with(context).load(imagePath[i]).resize(360, 200).centerCrop().into(img);
            imageViews.add(img);
        }

        bannerAdapter = new BannerAdapter(imageViews, context);
        vp_viewpager.setOnTouchListener(this);
        vp_viewpager.setAdapter(bannerAdapter);
        vp_viewpager.setCurrentItem(300);
        vp_viewpager.setOnPageChangeListener(this);
        myHandler.postDelayed(runnableForBanner, 2000);
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
                    getHomeArticleData(String.valueOf(currentPage), String.valueOf(ConstantUtil.ITEM_NUMBER), true);
                    break;
                case ConstantUtil.GET_NET_DATA:
                    initListView();
                    break;
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
                    getHomeArticleData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true);
                    rl_refresh.setRefreshing(false);
                    adapter.notifyDataSetChanged();
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    getHomeArticleData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false);
                    rl_refresh.setLoading(false);
                    adapter.notifyDataSetChanged();
                    setTextMore();
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
                    homeArticles.addAll(homeArticles1);

//                    aCache.put("HomeFragment", homeArticles);//获取的是添加后的对象
//                    aCache.remove("HomeFragment");

                    if (homeArticles1.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        isLoadOver = true;
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (currentPage > 1) {
                    rl_refresh.setRefreshing(false);
                    currentPage--;
                } else {
                    rl_refresh.setRefreshing(false);
                }
            }
        });
    }

    //图片网络请求的方法
    public void initRotatePicture() {
        Call<HomeRotatePicture> call = RetrofitUtil.getRetrofit().create(IHomeRotatePicture.class).getResult();
        call.enqueue(new Callback<HomeRotatePicture>() {
            @Override
            public void onResponse(Response<HomeRotatePicture> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    homeRotatePicture = response.body();
                    if (homeRotatePicture != null) {
                        myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);//待定
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    //获取缓存数据
    public void getCacheData() {
        ArrayList<HomeArticle> articles = (ArrayList<HomeArticle>) aCache.getAsObject("HomeFragment");
    }


    //设置listView底部文字
    public void setTextMore() {
        if (isLoadOver) {
            tv_more.setText("没有更多了");
        } else {

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
    public void onPageSelected(int position) {
        currentIndex = position;
        lastTime = System.currentTimeMillis();

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 解决下拉刷新与viewpager滑动冲突
     */
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
//        Toast.makeText(context, homeArticles.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        myHandler.removeCallbacks(runnableForBanner);
    }
}
