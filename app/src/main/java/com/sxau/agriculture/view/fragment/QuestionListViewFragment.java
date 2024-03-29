package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.api.IQuestionList;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.presenter.fragment_presenter.QuestionListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IQuestionListViewPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.fragment_interface.IQuestionListViewFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问答页面的listView的fragment
 *
 * @author 高海龙
 */
public class QuestionListViewFragment extends BaseFragment implements IQuestionListViewFragment, AdapterView.OnItemClickListener, View.OnTouchListener {
    private View mView;
    private ListView lvQuestionList;
    private QuestionFragment questionFragment;
    private RefreshLayout rl_refresh;
    private View footView;
    private TextView tv_more;

    private Context context;
    private QuestionAdapter adapter;
    private float startX, startY, offsetX, offsetY; //计算触摸偏移量
    private MyHandler myHandler;
    private ArrayList<QuestionData> questionDatas;
    private ArrayList<CategorieData> categorieDatas;
    private int currentPage;
    private boolean isLoadOver;
    private int cateId;
    private ACache aCache;
    private String authToken;
    private IQuestionListViewPresenter iQuestionListViewPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //将QuestionLvFragment与QuestionLvPresenter绑定
        if (mView == null) {
            iQuestionListViewPresenter = new QuestionListViewPresenter(QuestionListViewFragment.this);
            mView = inflater.inflate(R.layout.fragment_question_list, container, false);
            context = QuestionListViewFragment.this.getActivity();
            lvQuestionList = (ListView) mView.findViewById(R.id.lv_question);
            authToken = UserInfoUtil.findAuthToken();
            if (NetUtil.isNetAvailable(context)) {
                lvQuestionList.setOnItemClickListener(this);
            } else {
                Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
            }
            lvQuestionList.setOnTouchListener(this);
            questionFragment = new QuestionFragment();
            questionDatas = new ArrayList<QuestionData>();
            myHandler = new MyHandler(QuestionListViewFragment.this);

            //刷新&加载
            rl_refresh = (RefreshLayout) mView.findViewById(R.id.rl_refresh);
            rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
            footView = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
            tv_more = (TextView) footView.findViewById(R.id.tv_more);
            currentPage = 1;
            isLoadOver = false;
            aCache = ACache.get(context);
            cateId = 0;
            initRefresh();
            initList();
            myHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
                    RefreshBottomTextUtil.setTextMore(tv_more,ConstantUtil.LOADINDG);
                }
            }, 3000);
            RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOADINDG);
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }

        return mView;
    }

    public void initRefresh() {
        lvQuestionList.addFooterView(footView);
        rl_refresh.setChildView(lvQuestionList);
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

    private void initList() {
        adapter = new QuestionAdapter(context, questionDatas);
        lvQuestionList.setAdapter(adapter);
    }

    public class MyHandler extends Handler {
        WeakReference<QuestionListViewFragment> weakReference;

        public MyHandler(QuestionListViewFragment fragment) {
            weakReference = new WeakReference<QuestionListViewFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    if (NetUtil.isNetAvailable(context)) {
                        getCategorie();
                    } else {
                        Toast.makeText(context, "请检查网络！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ConstantUtil.GET_CATEGEORYDATA:
                    currentPage = 1;
                    int position = FragmentPagerItem.getPosition(getArguments());
                    cateId = categorieDatas.get(position).getId();
                    questionFragment.btn_ask.setVisibility(View.VISIBLE);
                    getQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true, cateId);
                    break;
                case ConstantUtil.GET_NET_DATA:
                    adapter.notifyDataSetChanged();
                    if (isLoadOver) {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
                    } else {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    }
                    break;
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
                    getQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true, cateId);
                    rl_refresh.setRefreshing(false);
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    getQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false, cateId);
                    rl_refresh.setLoading(false);
                    break;
                default:
                    break;
            }
        }
    }

    //网络请求方法
    public void getQuestionData(String page, String pageSize, final boolean isRefresh, int category) {
        Call<ArrayList<QuestionData>> call = RetrofitUtil.getRetrofit().create(IQuestionList.class).getQuestionList(authToken,page, pageSize, category);
        call.enqueue(new Callback<ArrayList<QuestionData>>() {
            @Override
            public void onResponse(Response<ArrayList<QuestionData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<QuestionData> questionDatas1 = new ArrayList<QuestionData>();
                    questionDatas1 = response.body();
                    if (isRefresh) {
                        try {
                            questionDatas.clear();
                            questionDatas.addAll(questionDatas1);
                            isLoadOver = false;
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    } else {
                        questionDatas.addAll(questionDatas1);
                    }
                    aCache.remove(ConstantUtil.CACHE_QUESTION_KEY);
                    aCache.put(ConstantUtil.CACHE_QUESTION_KEY, questionDatas);
                    if (questionDatas1.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        isLoadOver = true;
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
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

    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (questionDatas.size() > 0) {
            try {
                DetailQuestionActivity.actionStart(context, questionDatas.get(position).getId());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public void getCategorie() {
        Call<ArrayList<CategorieData>> call = RetrofitUtil.getRetrofit().create(ICategoriesData.class).getCategories();
        call.enqueue(new Callback<ArrayList<CategorieData>>() {
            @Override
            public void onResponse(Response<ArrayList<CategorieData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categorieDatas = response.body();
                }
                myHandler.sendEmptyMessage(ConstantUtil.GET_CATEGEORYDATA);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                offsetX = event.getX() - startX;
                offsetY = event.getY() - startY;
                if (offsetY < 0) {
                    AlphaAnimation animation = new AlphaAnimation(1.0f, 0f);
                    animation.setDuration(500);
                    questionFragment.btn_ask.setAnimation(animation);
                    questionFragment.btn_ask.setVisibility(View.INVISIBLE);
                }
                if (offsetY > 0) {
                    AlphaAnimation animation = new AlphaAnimation(0f, 1.0f);
                    animation.setDuration(500);
                    questionFragment.btn_ask.setAnimation(animation);
                    questionFragment.btn_ask.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        return false;
    }

    //------------------接口方法----------------
    @Override
    public void updateView() {

    }

    @Override
    public void changeItemView() {

    }

    /**
     * 获取催一下的状态
     * 1 为已经催
     * 0 为没有催
     *
     * @return
     */
    @Override
    public int getUrgeState() {
        return 0;
    }

    /**
     * 获取收藏状态（赞一下）
     * 1 为已经收藏
     * 0 为没有收藏
     *
     * @return
     */
    @Override
    public int getFavState() {
        return 0;
    }


//--------------接口方法结束---------------
}
