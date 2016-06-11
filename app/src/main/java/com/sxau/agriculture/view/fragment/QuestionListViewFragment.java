package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IQuestionList;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.presenter.fragment_presenter.QuestionListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IQuestionListViewPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.DetailQuestionActivity;
import com.sxau.agriculture.view.fragment_interface.IQuestionListViewFragment;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问答页面的listView的fragment
 *
 * @author 李秉龙
 */
public class QuestionListViewFragment extends BaseFragment implements IQuestionListViewFragment, AdapterView.OnItemClickListener, View.OnTouchListener {
    private View mView;
    private ListView lvQuestionList;
    private Context context;
    private QuestionAdapter adapter;
    private MyHandler myHandler;
    private ArrayList<QuestionData> questionDatas;
    private float startX, startY, offsetX, offsetY; //计算触摸偏移量
    private QuestionFragment questionFragment;

    private RefreshLayout rl_refresh;
    private View footView;
    private TextView tv_more;
    private int currentPage;
    private boolean isLoadOver;
    private DbUtils dbUtil;
    private int cateId;

    private IQuestionListViewPresenter iQuestionListViewPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将QuestionLvFragment与QuestionLvPresenter绑定
        iQuestionListViewPresenter = new QuestionListViewPresenter(QuestionListViewFragment.this);

        mView = inflater.inflate(R.layout.fragment_question_list, container, false);
        context = QuestionListViewFragment.this.getActivity();

        lvQuestionList = (ListView) mView.findViewById(R.id.lv_question);

        if (NetUtil.isNetAvailable(context)) {
            lvQuestionList.setOnItemClickListener(this);
        } else {
            Toast.makeText(context, "请检查网络设置", Toast.LENGTH_SHORT).show();
        }

        lvQuestionList.setOnTouchListener(this);
        questionFragment = new QuestionFragment();

        questionDatas = new ArrayList<QuestionData>();
        myHandler = new MyHandler();

        pullCategorieId();

        Log.d("555", cateId + "");

        //刷新&加载
        rl_refresh = (RefreshLayout) mView.findViewById(R.id.rl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));
        footView = getLayoutInflater(savedInstanceState).inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footView.findViewById(R.id.tv_more);
        currentPage = 1;
        isLoadOver = false;
        dbUtil = DbUtils.create(context);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefresh();
        initList();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    public void pullCategorieId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cate", Context.MODE_PRIVATE);
        cateId = sharedPreferences.getInt("cateId", 0);
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


    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    currentPage = 1;
                    if (NetUtil.isNetAvailable(context)) {
                        getQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, true, String.valueOf(cateId));
                    } else {
                        try {
                            dbUtil.createTableIfNotExist(QuestionData.class);
                            getCatchData();
                            initList();
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
                case ConstantUtil.PULL_REFRESH:
                    currentPage = 1;
                    getQuestionData(String.valueOf(currentPage) , ConstantUtil.ITEM_NUMBER, true, String.valueOf(cateId));
                    rl_refresh.setRefreshing(false);
                    RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    getQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false, String.valueOf(cateId));
                    rl_refresh.setLoading(false);
                    break;
                default:
                    break;
            }
        }
    }

    //网络请求方法
    public void getQuestionData(String page, String pageSize, final boolean isRefresh, String category) {
        Log.d("666", category);
        Call<ArrayList<QuestionData>> call = RetrofitUtil.getRetrofit().create(IQuestionList.class).getQuestionList(page, pageSize, category);
        call.enqueue(new Callback<ArrayList<QuestionData>>() {
            @Override
            public void onResponse(Response<ArrayList<QuestionData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<QuestionData> questionDatas1 = response.body();
                    try {
                        dbUtil.deleteAll(QuestionData.class);
                        if (isRefresh) {
                            questionDatas.clear();
                            questionDatas.addAll(questionDatas1);
                            dbUtil.saveAll(questionDatas1);
                            isLoadOver = false;
                        } else {
                            questionDatas.addAll(questionDatas1);
                            dbUtil.saveAll(questionDatas);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    if (questionDatas1.size() < Integer.parseInt("3")) {
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

    public void getCatchData() {
        try {
            List<QuestionData> list = dbUtil.findAll(QuestionData.class);
            questionDatas = (ArrayList<QuestionData>) list;
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 给listview填充数据
     */
    private void initList() {
        adapter = new QuestionAdapter(context, questionDatas);
        lvQuestionList.setAdapter(adapter);
    }

    //item点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DetailQuestionActivity.actionStart(context, questionDatas.get(position).getId());
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
