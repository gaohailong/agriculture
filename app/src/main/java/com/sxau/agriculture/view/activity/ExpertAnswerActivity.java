package com.sxau.agriculture.view.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.ExpertAnswerPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IExpertAnswerPresenter;
import com.sxau.agriculture.utils.TitleBarTwo;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

/**
 * 回答界面Activity
 *
 * @author 李秉龙
 */
public class ExpertAnswerActivity extends BaseActivity implements View.OnClickListener, IExpertAnswerActivity {
    private TextView tv_question;
    private EditText et_answer;
    private Button bt_submit;
    private TitleBarTwo topBarUtil;

    private String getQuestion;
    private int id;
    private IExpertAnswerPresenter iExpertAnswerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        //将ExpertAnswerActivity 与 ExpertAnswerPresenter绑定
        iExpertAnswerPresenter = new ExpertAnswerPresenter(ExpertAnswerActivity.this);
        initView();
        initTitlebar();
    }

    private void initView() {
        topBarUtil = (TitleBarTwo) this.findViewById(R.id.topBarUtil);
        tv_question = (TextView) this.findViewById(R.id.tv_question);
        bt_submit = (Button) this.findViewById(R.id.bt_submit);
        et_answer = (EditText) this.findViewById(R.id.et_answer);

    bt_submit.setOnClickListener(this);

        Intent intent = getIntent();
        getQuestion = intent.getStringExtra("question");
        this.id = intent.getIntExtra("id", 0);
        tv_question.setText(getQuestion);
    }

    private void initTitlebar() {
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
        topBarUtil.setTitle("专家回答");
        topBarUtil.setTitleColor(Color.WHITE);
    }


    public static void actionStart(Context context, String question, int id) {
        Intent intent = new Intent(context, ExpertAnswerActivity.class);
        intent.putExtra("question", question);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.bt_submit:
                submitDialog();
                break;
            default:
        }
    }

    private void submitDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您是否提交所填答案？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iExpertAnswerPresenter.submitAnswer();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

//------------------------接口方法-----------------

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getAnswerContent() {
        return et_answer.getText().toString();
    }
//----------------------接口方法结束------------------
}
