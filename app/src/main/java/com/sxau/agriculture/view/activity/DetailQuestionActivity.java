package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.acitivity_presenter.DetailQuestionPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import java.lang.ref.WeakReference;

/**
 * 问题详细信息页面Activity
 * 问题：1、点赞功能未实现，需发送网络请求（接口没有）
 * 2、接口数据部分待修正，问题列出在底部
 *
 * @author 李秉龙
 */
public class DetailQuestionActivity extends BaseActivity implements IDetailQuestionActivity, View.OnClickListener {
    private ImageView rv_question_head, rv_professor_head, iv_fav;
    private TextView tv_question_name, tv_question_content, tv_question_title, tv_question_time, tv_is_answer, tv_professor_name, tv_professor_content, tv_professor_ok;
    private Button bt_answer;
    private LinearLayout ll_expert_answer;
    private TopBarUtil topBarUtil;

    private IDetailQuestionPresenter detailQuestionPresenter;
    private DetailQuestionData detailQuestionData;
    private MyHandler handler;
    private Context context;

    private int favIndex = 0;//是否已赞 0：没有；1：已赞

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);
        handler = new MyHandler(DetailQuestionActivity.this);
        detailQuestionPresenter = new DetailQuestionPresenter(DetailQuestionActivity.this, handler);
        detailQuestionPresenter.getDetailData(String.valueOf(getQuestionId()));

        initView();
        initTopBar();
    }

    private void initView() {
        iv_fav = (ImageView) this.findViewById(R.id.iv_fav);
        rv_question_head = (ImageView) findViewById(R.id.rv_question_head);
        tv_question_content = (TextView) findViewById(R.id.tv_question_content);
        rv_professor_head = (ImageView) findViewById(R.id.rv_professor_head);
        tv_question_name = (TextView) findViewById(R.id.tv_question_name);
        tv_question_title = (TextView) findViewById(R.id.tv_question_title);
        tv_question_time = (TextView) findViewById(R.id.tv_question_time);
        tv_is_answer = (TextView) findViewById(R.id.tv_is_answer);
        tv_professor_name = (TextView) findViewById(R.id.tv_professor_name);
        tv_professor_content = (TextView) findViewById(R.id.tv_professor_content);
        tv_professor_ok = (TextView) findViewById(R.id.tv_professor_ok);
        bt_answer = (Button) findViewById(R.id.bt_answer);
        ll_expert_answer = (LinearLayout) findViewById(R.id.ll_expert_answer);
        topBarUtil = (TopBarUtil) findViewById(R.id.topBar_detail);

        iv_fav.setOnClickListener(this);
        bt_answer.setOnClickListener(this);
    }

    private void initTopBar() {
        topBarUtil.setLeftImageIsVisible(true);
        topBarUtil.setLeftImage(R.mipmap.ic_back_left);
        topBarUtil.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {

            }

            @Override
            public void onClickLeftImage() {
                finish();
            }

            @Override
            public void onClickRightImage() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fav:
                if (favIndex == 0) {
                    iv_fav.setImageResource(R.drawable.ic_praise_48px);
                    favIndex = 1;
                } else if (favIndex == 1) {
                    iv_fav.setImageResource(R.drawable.ic_no_praise_48px);
                    favIndex = 0;
                }
                break;
            case R.id.bt_answer:
                ExpertAnswerActivity.actionStart(DetailQuestionActivity.this, detailQuestionData.getTitle(),detailQuestionData.getId());//有问题,接口返回了多个问题的答案
                break;
            default:
                break;
        }
    }

    public class MyHandler extends Handler {
        WeakReference<DetailQuestionActivity> weakReference;

        public MyHandler(DetailQuestionActivity activity) {
            weakReference = new WeakReference<DetailQuestionActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:
                    detailQuestionData = detailQuestionPresenter.getData();
                    updateView();
                    break;
                default:
                    break;
            }
        }
    }

    public static void actionStart(Context context, int id) {
        Intent intent = new Intent(context, DetailQuestionActivity.class);
        intent.putExtra("indexPosition", id);
        context.startActivity(intent);
    }

    @Override
    public int getQuestionId() {
        Intent intent = getIntent();
        return intent.getIntExtra("indexPosition", 0);
    }

    //视图改变未完成
    @Override
    public void updateView() {
        Picasso.with(context).load(detailQuestionData.getUser().getAvatar()).centerCrop().
                placeholder(R.mipmap.img_default_user_portrait_150px).error(R.mipmap.img_default_user_portrait_150px).into(rv_question_head);
        tv_question_name.setText(detailQuestionData.getUser().getName());
        tv_question_title.setText(detailQuestionData.getTitle());
        tv_question_content.setText(detailQuestionData.getContent());
        tv_question_time.setText(TimeUtil.format(detailQuestionData.getWhenCreated()));
        if (detailQuestionData.getExpert() != null) {
            ll_expert_answer.setVisibility(View.VISIBLE);
            bt_answer.setVisibility(View.GONE);
            tv_is_answer.setText("专家已回答");
            //专家部分
            Picasso.with(context).load(detailQuestionData.getUser().getAvatar()).centerCrop().
                    placeholder(R.mipmap.img_default_user_portrait_150px).error(R.mipmap.img_default_user_portrait_150px).into(rv_professor_head);
            tv_professor_name.setText(detailQuestionData.getExpert().getName());
            tv_professor_content.setText((Integer) detailQuestionData.getAnswers().get(0));//有问题,接口返回了多个问题的答案
            tv_professor_ok.setText("点赞人数" + detailQuestionData.getLikeCount());
        } else {
            ll_expert_answer.setVisibility(View.GONE);
            bt_answer.setVisibility(View.VISIBLE);
            tv_is_answer.setText("专家未回答");
        }
    }
}
