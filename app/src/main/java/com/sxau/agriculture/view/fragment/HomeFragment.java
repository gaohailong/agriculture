package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.sxau.agriculture.adapter.BannerAdapter;
import com.sxau.agriculture.agriculture.R;

import com.sxau.agriculture.adapter.HomePushAdapter;
import com.sxau.agriculture.api.IHomeRotatePicture;
import com.sxau.agriculture.bean.HomeListViewBean;
import com.sxau.agriculture.bean.HomeRotatePicture;
import com.sxau.agriculture.presenter.fragment_presenter.HomePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.fragment_interface.IHomeFragment;


import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
<<<<<<< HEAD
 * <<<<<<< HEAD
 * 主界面的Fragment
 *
 * @author 高海龙
 *         =======
 *         首页Fragment
 * @author 崔志泽
 *         >>>>>>> 15e48df8d091c9641a4022c8cbefa6a04ebe488f
=======
 * 首页Fragment
 *
 * @author 崔志泽
>>>>>>> 22b636c64bb6d557f81fb80a446b2fcf1a96a160
 */
<<<<<<< HEAD
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener, IHomeFragment, AdapterView.OnItemClickListener {
=======
public class HomeFragment extends BaseFragment implements ViewPager.OnPageChangeListener, SwipeRefreshLayout.OnRefreshListener, IHomeFragment, AdapterView.OnItemClickListener, View.OnTouchListener {
>>>>>>> 191c6b3d66869a39634014e8597fb7d643371ba8
    private IHomePresenter iHomePresenter;
    private HomeRotatePicture homeRotatePicture;
    private BannerAdapter bannerAdapter;
    private ListView lv_push;
    private View mView;
    private HomePushAdapter adapter;
    private ViewPager vp_viewpager;
    private LinearLayout ll_point;
    private SwipeRefreshLayout srl_refresh;
    private Context context;

    private int[] imagePath;
    private int[] pointPath;
    private ArrayList<HomeListViewBean> homeListViewBeans;
    private ArrayList<ImageView> views;
    private int currentIndex = 300;
    private long lastTime;

    private Handler handlerForBanner;
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
        //将HomeFragment与HomePresenter绑定起来
        iHomePresenter = new HomePresenter(HomeFragment.this);
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        lv_push = (ListView) mView.findViewById(R.id.lv_push);
        srl_refresh = (SwipeRefreshLayout) mView.findViewById(R.id.srl_refresh);
        handlerForRefresh = new Handler();
        handlerForBanner = new Handler();
        context = HomeFragment.this.getActivity();

        srl_refresh.setOnRefreshListener(this);
        lv_push.setOnItemClickListener(this);
        //将HomeFragment与HomePresenter绑定起来
        iHomePresenter = new HomePresenter(HomeFragment.this);
        initView();
        initListData();
        return mView;
    }

    public void initListData() {
        homeListViewBeans = new ArrayList<HomeListViewBean>();

        HomeListViewBean[] pushs = new HomeListViewBean[6];
        pushs[0] = new HomeListViewBean();
        pushs[0].setRead("12万");
        pushs[0].setBrowseid(R.drawable.ic_read_48px);
        pushs[0].setNewsid(R.drawable.phone_48dp);
        pushs[0].setTime("2016.4.9");
        pushs[0].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[1] = new HomeListViewBean();
        pushs[1].setRead("12万");
        pushs[1].setBrowseid(R.drawable.ic_read_48px);
        pushs[1].setNewsid(R.drawable.phone_48dp);
        pushs[1].setTime("2016.4.9");
        pushs[1].setTitle("全面推动“三品一标”工作啦啦啦再上新台阶");

        pushs[2] = new HomeListViewBean();
        pushs[2].setRead("12万");
        pushs[2].setBrowseid(R.drawable.ic_read_48px);
        pushs[2].setNewsid(R.drawable.phone_48dp);
        pushs[2].setTime("2016.4.9");
        pushs[2].setTitle("我也是服气了");

        pushs[3] = new HomeListViewBean();
        pushs[3].setRead("12万");
        pushs[3].setBrowseid(R.drawable.ic_read_48px);
        pushs[3].setNewsid(R.drawable.phone_48dp);
        pushs[3].setTime("2016.4.9");
        pushs[3].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[4] = new HomeListViewBean();
        pushs[4].setRead("12万");
        pushs[4].setBrowseid(R.drawable.ic_read_48px);
        pushs[4].setNewsid(R.drawable.phone_48dp);
        pushs[4].setTime("2016.4.9");
        pushs[4].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[5] = new HomeListViewBean();
        pushs[5].setRead("12万");
        pushs[5].setBrowseid(R.drawable.ic_read_48px);
        pushs[5].setNewsid(R.drawable.phone_48dp);
        pushs[5].setTime("2016.4.9");
        pushs[5].setTitle("全面推动“三品一标”工作再上新台阶");
        homeListViewBeans.add(pushs[0]);
        homeListViewBeans.add(pushs[1]);
        homeListViewBeans.add(pushs[2]);
        homeListViewBeans.add(pushs[3]);
        homeListViewBeans.add(pushs[4]);
        homeListViewBeans.add(pushs[5]);

        adapter = new HomePushAdapter(homeListViewBeans, context);
        lv_push.setAdapter(adapter);
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
        imagePath = new int[]{R.drawable.img_banner_red, R.drawable.img_banner_white, R.drawable.img_banner_red, R.drawable.img_banner_white, R.drawable.img_banner_red};
        pointPath = new int[]{R.drawable.img_banner_red, R.drawable.img_banner_white, R.drawable.img_banner_white, R.drawable.img_banner_white, R.drawable.img_banner_white};
        views = new ArrayList<ImageView>();

        for (int i = 0; i < imagePath.length; i++) {
            ImageView img = new ImageView(context);
            img.setImageResource(imagePath[i]);
            views.add(img);
            //圈
            ImageView imgCircle = new ImageView(context);
            imgCircle.setImageResource(pointPath[i]);//默认不中
            imgCircle.setPadding(10, 5, 10, 5);
            ll_point.addView(imgCircle);
        }


        bannerAdapter = new BannerAdapter(views, context);
        vp_viewpager.setOnTouchListener(this);
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
        handlerForRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                srl_refresh.setRefreshing(false);
            }
        }, 2000);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, homeListViewBeans.get(position).getTitle(), Toast.LENGTH_SHORT).show();
    }

<<<<<<< HEAD
    //网络请求的方法
    public void initRotatePicture() {
        Call<HomeRotatePicture> call = RetrofitUtil.getRetrofit().create(IHomeRotatePicture.class).getResult();
        call.enqueue(new Callback<HomeRotatePicture>() {
            @Override
            public void onResponse(Response<HomeRotatePicture> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    homeRotatePicture = response.body();
                    if (homeRotatePicture != null) {
                        handlerForBanner.sendEmptyMessage(0);//待定
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
=======
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
>>>>>>> 191c6b3d66869a39634014e8597fb7d643371ba8
    }
}
