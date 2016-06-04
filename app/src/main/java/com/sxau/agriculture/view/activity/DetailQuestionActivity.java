package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IDetailQuestion;
import com.sxau.agriculture.bean.DetailQuestionData;
import com.sxau.agriculture.presenter.acitivity_presenter.DetailQuestionPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IDetailQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.IDetailQuestionActivity;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问题详细信息页面Activity
 *
 * @author 李秉龙
 */
public class DetailQuestionActivity extends BaseActivity implements IDetailQuestionActivity, View.OnClickListener {
    private ImageButton ib_Back;
    private ImageView iv_Fav;
    private ImageView iv_Quick;
    private Button btn_Answer;
    private TextView tv_Question;

    private IDetailQuestionPresenter detailQuestionPresenter;
    private MyHandler handler;

    private Context context;

    private int favIndex = 0;//是否已赞 0：没有；1：已赞
    private int quickIndex = 0;//是否已催 0：没有；1：已催

    private int id;

    String question = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);

        detailQuestionPresenter = new DetailQuestionPresenter(DetailQuestionActivity.this,handler);

        initView();
        Intent intent = getIntent();
        int indexPosition = intent.getIntExtra("indexPosition", 0);
        Toast.makeText(DetailQuestionActivity.this, indexPosition + "", Toast.LENGTH_SHORT).show();

    }

    private void initView() {
        ib_Back = (ImageButton) this.findViewById(R.id.ib_back);
        iv_Fav = (ImageView) this.findViewById(R.id.iv_fav);
        btn_Answer = (Button) this.findViewById(R.id.btn_answer);
        tv_Question = (TextView) this.findViewById(R.id.tv_question);
        ib_Back.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        btn_Answer.setOnClickListener(this);
        question = tv_Question.getText().toString();
        detailQuestionPresenter.getDetailData(String.valueOf(id));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_fav:
                if (favIndex == 0) {
                    iv_Fav.setBackgroundResource(R.drawable.ic_praise_48px);
                    favIndex = 1;
                } else if (favIndex == 1) {
                    iv_Fav.setBackgroundResource(R.drawable.ic_no_praise_48px);
                    favIndex = 0;
                }
                break;
            case R.id.btn_answer:
                ExpertAnswerActivity.actionStart(DetailQuestionActivity.this, question);
                break;
            default:
                break;
        }
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.GET_NET_DATA:

                    break;
            }
        }
    }

    public static void actionStart(Context context, int id) {
        Intent intent = new Intent(context, DetailQuestionActivity.class);
        intent.putExtra("indexPosition", id);
        Log.e("indexPosition", id + "");
        id = id;
        context.startActivity(intent);
    }

   /* public void getDetailData(String id) {
        Call<DetailQuestionData> detailQuestionDataCall = RetrofitUtil.getRetrofit().create(IDetailQuestion.class).getDetailQuestionData(id);
        detailQuestionDataCall.enqueue(new Callback<DetailQuestionData>() {
            @Override
            public void onResponse(Response<DetailQuestionData> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    DetailQuestionData detailQuestionData =response.body();
                    Toast.makeText(DetailQuestionActivity.this,detailQuestionData.getTitle(),Toast.LENGTH_SHORT).show();
                    LogUtil.d("dd",detailQuestionData.getTitle());
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }*/

    //接口方法
    @Override
    public int getQuestionId(int id) {
        return id;
    }
}
