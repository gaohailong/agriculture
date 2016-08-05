package com.sxau.agriculture.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.sxau.agriculture.adapter.QuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ISearchArticle_Question;
import com.sxau.agriculture.bean.QuestionData;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RefreshBottomTextUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.widgets.RefreshLayout;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 搜索文章activity
 *
 * @author gaohailong
 */
public class SearchQuestionActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnTouchListener {
    private TitleBarTwo topBarUtil;
    private ListView lv_question;
    private EditText et_search;
    private String str_keyword;
    private Button btn_search;

    private ProgressDialog pdLoginwait;

    //问答界面一致
    private QuestionAdapter adapter;
    private ArrayList<QuestionData> questionData;
    private Context context;
    private MyHandler myHandler;
    private int currentPage;
    private boolean isLoadOver;
    private TextView tv_more;
    private View footerView;
    private RefreshLayout rl_refresh;

    private DbUtils dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_question);
        topBarUtil = (TitleBarTwo) findViewById(R.id.top_search);
        topBarUtil.setBackgroundColor(Color.parseColor("#00b5ad"));
        topBarUtil.setLeftImageResource(R.mipmap.ic_back_left);
        topBarUtil.setLeftTextColor(Color.WHITE);
        topBarUtil.setDividerColor(Color.GRAY);
        topBarUtil.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        topBarUtil.setTitle("搜索问题");
        topBarUtil.setTitleColor(Color.WHITE);

        footerView = getLayoutInflater().inflate(R.layout.listview_footer, null);
        tv_more = (TextView) footerView.findViewById(R.id.tv_more);
        rl_refresh = (RefreshLayout) findViewById(R.id.srl_refresh);
        rl_refresh.setColorSchemeColors(Color.parseColor("#00b5ad"));

        btn_search = (Button) findViewById(R.id.btn_search);
        et_search = (EditText) findViewById(R.id.et_search);
        lv_question = (ListView) findViewById(R.id.lv_questions);

        questionData = new ArrayList<QuestionData>();
        myHandler = new MyHandler();
        context = SearchQuestionActivity.this;
        btn_search.setOnClickListener(this);
        isLoadOver = false;
        currentPage = 1;
        dbUtil = DbUtils.create(context);

        if (NetUtil.isNetAvailable(context)) {
            lv_question.setOnItemClickListener(this);
        }

        pdLoginwait = new ProgressDialog(SearchQuestionActivity.this);
        pdLoginwait.setMessage("搜索中...");
        pdLoginwait.setCanceledOnTouchOutside(false);
        pdLoginwait.setCancelable(true);


        initListView();
        initRefresh();
        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOADINDG);
    }

    public void initRefresh() {
        lv_question.addFooterView(footerView);
        rl_refresh.setChildView(lv_question);
        rl_refresh.setRefreshing(false);
        tv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myHandler.sendEmptyMessage(ConstantUtil.UP_LOAD);
            }
        });
        rl_refresh.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rl_refresh.setRefreshing(false);
            }
        });
    }

    public void initListView() {
        adapter = new QuestionAdapter(context,questionData);
        lv_question.setAdapter(adapter);
    }


    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    currentPage = 1;
//                    if (NetUtil.isNetAvailable(context)) {
                    getSearchQuestionData(String.valueOf(currentPage), String.valueOf(ConstantUtil.ITEM_NUMBER), true, str_keyword);
//                    } else {
//                        try {
//                            Toast.makeText(context, "当前没有网络，请检查网络设置", Toast.LENGTH_LONG).show();
//                            dbUtil.createTableIfNotExist(HomeArticle.class);
//                            initListView();
//                        } catch (DbException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    break;
                case ConstantUtil.GET_NET_DATA:
                    adapter.notifyDataSetChanged();
                    if (isLoadOver) {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_OVER);
                    } else {
                        RefreshBottomTextUtil.setTextMore(tv_more, ConstantUtil.LOAD_MORE);
                    }
                    break;
                case ConstantUtil.UP_LOAD:
                    currentPage++;
                    getSearchQuestionData(String.valueOf(currentPage), ConstantUtil.ITEM_NUMBER, false, str_keyword);
                    rl_refresh.setLoading(false);
                    break;
                default:
                    break;
            }
        }
    }

    public void getSearchQuestionData(String page, String pageSize, final boolean isRefresh, String keyWord) {
        Call<ArrayList<QuestionData>> call = RetrofitUtil.getRetrofit().create(ISearchArticle_Question.class).getQuestionList(keyWord, page, pageSize);
        call.enqueue(new Callback<ArrayList<QuestionData>>() {
            @Override
            public void onResponse(Response<ArrayList<QuestionData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ArrayList<QuestionData> questionDatas1 = new ArrayList<QuestionData>();
                    questionDatas1 = response.body();
                    LogUtil.e("SearchQA","data:"+questionDatas1.toString());

                    try {
                        dbUtil.deleteAll(QuestionData.class);
                        if (isRefresh) {
                            questionData.clear();
                            try {
                                questionData.addAll(questionDatas1);
                                dbUtil.saveAll(questionDatas1);
                                isLoadOver = false;
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                questionData.addAll(questionDatas1);
                                dbUtil.saveAll(questionData);
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    if (questionDatas1.size() < Integer.parseInt(ConstantUtil.ITEM_NUMBER)) {
                        isLoadOver = true;
                    }
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);

                }
                showProgress(false);
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
        if (questionData.size() > 0) {
            try {
                DetailQuestionActivity.actionStart(context, questionData.get(position).getId());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                str_keyword = et_search.getText().toString();
                if (!str_keyword.isEmpty()) {
                    if (NetUtil.isNetAvailable(context)) {
                        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
                        showProgress(true);
                    }else {
                        Toast.makeText(context, "当前没有网络，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    //显示进度条
    public void showProgress(boolean flag) {
        if (flag) {
            pdLoginwait.show();
        } else {
            pdLoginwait.cancel();
        }
    }

    //点击别处隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
