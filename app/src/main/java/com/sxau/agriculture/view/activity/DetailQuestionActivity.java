package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.squareup.picasso.Picasso;
import com.sxau.agriculture.adapter.NineGridImageViewAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.acitivity_presenter.DetailQuestionPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 问题详细信息页面Activity
 * 问题：1、点赞功能未实现，需发送网络请求（接口没有）
 * 2、接口数据部分待修正，问题列出在底部
 *
 * @author 李秉龙
 */
public class DetailQuestionActivity extends BaseActivity implements IDetailQuestionActivity, View.OnClickListener {
    private ImageView rv_question_head, rv_professor_head, iv_collection;
    private TextView tv_question_name, tv_question_content, tv_question_title, tv_question_time, tv_is_answer, tv_professor_name, tv_professor_content, tv_professor_ok;
    private Button bt_answer;
    private LinearLayout ll_expert_answer;
    private TitleBarTwo topBarUtil;

    private IDetailQuestionPresenter detailQuestionPresenter;
    private DetailQuestionData detailQuestionData;
    private MyHandler handler;
    private Context context;

    private NineGridImageView nineGridImageView;    //九宫格View
    private List<String> imgDatas;                  //九宫格图片数据


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
        iv_collection = (ImageView) this.findViewById(R.id.iv_collection);
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
        topBarUtil = (TitleBarTwo) findViewById(R.id.topBar_detail);
        nineGridImageView = (NineGridImageView) findViewById(R.id.mNineGridImageView);

        bt_answer.setOnClickListener(this);
    }

    private void initTopBar() {
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
        topBarUtil.setTitle("问答详情");
        topBarUtil.setTitleColor(Color.WHITE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_answer:
                ExpertAnswerActivity.actionStart(DetailQuestionActivity.this, detailQuestionData.getTitle(), detailQuestionData.getId());//有问题,接口返回了多个问题的答案
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
                    LogUtil.e("DetailQuestionA","images:"+detailQuestionData.getImages());
                    imgDatas = StringUtil.changeStringToList(detailQuestionData.getImages());
                    for (int i = 0; i < imgDatas.size(); i++) {
                        LogUtil.e("DetailQuestionA", "imgDatas:"+imgDatas.get(i).toString());
                    }
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

//    private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
//        @Override
//        protected void onDisplayImage(Context context, ImageView imageView, String s) {
//            Picasso.with(context)
//                    .load(s.getSmallUrl)
//                    .placeholder(R.drawable.ic_default_image)
//                    .into(imageView);
//        }
//
//        @Override
//        protected ImageView generateImageView(Context context) {
//            return super.generateImageView(context);
//        }
//
//        @Override
//        protected void onItemImageClick(Context context, int index, List<String> photoList) {
//            Toast.makeText(context, "点击了图片", Toast.LENGTH_LONG).show();
//        }
//    };

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

        if (detailQuestionData.isFav()) {
            iv_collection.setImageResource(R.drawable.collection_fill);
        } else {
            iv_collection.setImageResource(R.drawable.collection);
        }

//        nineGridImageView.setAdapter(mAdapter);
    }


}
