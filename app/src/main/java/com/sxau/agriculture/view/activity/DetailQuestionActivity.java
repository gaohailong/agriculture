package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.acitivity_presenter.DetailQuestionPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.UserInfoUtil;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.StringUtil;
import com.sxau.agriculture.utils.TimeUtil;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import java.io.IOException;
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
    private TextView tv_voice;
    private Button bt_answer;
    private LinearLayout ll_expert_answer;
    private TitleBarTwo topBarUtil;

    private IDetailQuestionPresenter idetailQuestionPresenter;
    private DetailQuestionData detailQuestionData;
    private MyHandler handler;
    private Context context;
    private Boolean isFav = false;  //问题是否收藏状态

    private NineGridImageView nineGridImageView;    //九宫格View
    private List<String> imgDatas;                  //九宫格图片数据
    private NineGridImageViewAdapter<String> mAdapter;

    private MediaPlayer mediaPlayer;
    private String audioUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);
        handler = new MyHandler(DetailQuestionActivity.this);
        idetailQuestionPresenter = new DetailQuestionPresenter(DetailQuestionActivity.this, handler);
        idetailQuestionPresenter.getDetailData(String.valueOf(getQuestionId()));
        context = DetailQuestionActivity.this;

        initView();
        initTopBar();
        initNineGridView();
    }

    private void initView() {
        iv_collection = (ImageView) findViewById(R.id.iv_collection);
        rv_question_head = (ImageView) findViewById(R.id.rv_question_head);
        tv_question_content = (TextView) findViewById(R.id.tv_question_content);
        rv_professor_head = (ImageView) findViewById(R.id.rv_professor_head);
        tv_question_name = (TextView) findViewById(R.id.tv_question_name);
        tv_question_title = (TextView) findViewById(R.id.tv_question_title);
        tv_question_time = (TextView) findViewById(R.id.tv_question_time);
        tv_is_answer = (TextView) findViewById(R.id.tv_is_answer);
        tv_professor_name = (TextView) findViewById(R.id.tv_professor_name);
        tv_professor_content = (TextView) findViewById(R.id.tv_professor_content);
        tv_voice = (TextView) findViewById(R.id.tv_voice);
        bt_answer = (Button) findViewById(R.id.bt_answer);
        ll_expert_answer = (LinearLayout) findViewById(R.id.ll_expert_answer);
        topBarUtil = (TitleBarTwo) findViewById(R.id.topBar_detail);
        nineGridImageView = (NineGridImageView) findViewById(R.id.mNineGridImageView);

        mediaPlayer = new MediaPlayer();

        iv_collection.setOnClickListener(this);
        bt_answer.setOnClickListener(this);
        tv_voice.setOnClickListener(this);
    }

    public void initNineGridView() {
         mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String t) {
                Picasso.with(context).load(t).placeholder(R.mipmap.ic_loading).into(imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> list) {
                Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
            }
        };
        nineGridImageView.setAdapter(mAdapter);
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
            case R.id.iv_collection:
                doCollection();
                break;
            case R.id.tv_voice:
                playAudio();
                break;
            default:
                break;
        }
    }

    public void playAudio(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
                    detailQuestionData = new DetailQuestionData();
                    detailQuestionData = idetailQuestionPresenter.getData();
                    isFav = detailQuestionData.isFav();
                    LogUtil.e("DetailQuestionA", "images:" + detailQuestionData.getImages());
                    if (detailQuestionData.getImages() != null && detailQuestionData.getImages().length() > 4) {
                        imgDatas = StringUtil.changeStringToList(detailQuestionData.getImages());
                        imgDatas = StringUtil.changeToWholeUrlList(imgDatas);
                        Log.d("DetailQA","imgDatas："+detailQuestionData.getImages().toString());
                        for (int i=0;i<imgDatas.size();i++){
                            Log.d("DetailQA","imgDatas："+imgDatas.get(i)+" 位置："+i);
                        }
                        //设置九宫格数据源
                        nineGridImageView.setImagesData(imgDatas);
                    }
                    updateView();
                    break;
                case ConstantUtil.CHANGE_TO_COLLECTION_STATE:
                    changeToCollectionIC();
                    break;
                case ConstantUtil.CHANGE_TO_NOCOLLECTION_STATE:
                    changeToNoCollectionIC();
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

    public void doCollection() {
        if (isFav) {
            //执行取消收藏操作
            if (NetUtil.isNetAvailable(context)) {
                idetailQuestionPresenter.doUnCollection(detailQuestionData.getId());
            } else {
                showNetUnavailable();
            }
        } else {
            //执行收藏操作
            if (NetUtil.isNetAvailable(context)) {
                idetailQuestionPresenter.doCollection(detailQuestionData.getId());
            } else {
                showNetUnavailable();
            }
        }
    }

    //更改收藏图标
    private void changeToCollectionIC(){
        iv_collection.setImageResource(R.drawable.collection_fill);
        isFav = true;
    }
    private void changeToNoCollectionIC(){
        iv_collection.setImageResource(R.drawable.collection);
        isFav = false;
    }




    //----------------------接口方法---------------------------
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
        Log.e("DetailQA","mediaId:"+detailQuestionData.getMediaId());
        if (detailQuestionData.getMediaId() != null){
            tv_question_content.setVisibility(View.GONE);
            tv_voice.setVisibility(View.VISIBLE);
            audioUrl = detailQuestionData.getMediaId();
        }else {
            tv_voice.setVisibility(View.GONE);
        }
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
            tv_professor_content.setText(detailQuestionData.getAnswer());//有问题,接口返回了多个问题的答案
//            tv_professor_ok.setText("点赞人数" + detailQuestionData.getLikeCount());
        } else {
            ll_expert_answer.setVisibility(View.GONE);
            if (UserInfoUtil.isUserTypeEXPERT()){
                bt_answer.setVisibility(View.VISIBLE);
            }else {
                bt_answer.setVisibility(View.GONE);
            }
            tv_is_answer.setText("专家未回答");
        }
        if (detailQuestionData.isFav()) {
            iv_collection.setImageResource(R.drawable.collection_fill);
        } else {
            iv_collection.setImageResource(R.drawable.collection);
        }

    }

    @Override
    public void showServiceError() {
        Toast.makeText(context, "服务器提出了一个问题", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFailed() {
        Toast.makeText(context, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
    }


    //------------------------接口方法结束--------------------------

    public void showNetUnavailable() {
        Toast.makeText(context, "网络不可用，请检查网络", Toast.LENGTH_LONG).show();
    }
}
