package com.sxau.agriculture.view.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

/**
 * 回答界面Activity
 * @author 李秉龙
 */
public class ExpertAnswerActivity extends BaseActivity implements View.OnClickListener ,IExpertAnswerActivity{
    private ImageButton ib_Back;
    private TextView tv_Question;
    private EditText et_answer;
    private Button btn_Submit;
    private String updateQuestion;//更新问题

    private IExpertAnswerPresenter iExpertAnswerPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);
        //将ExpertAnswerActivity 与 ExpertAnswerPresenter绑定
        iExpertAnswerPresenter = new ExpertAnswerPresenter(ExpertAnswerActivity.this);
        initView();
    }


    private void initView() {
        ib_Back = (ImageButton) this.findViewById(R.id.ib_back);
        tv_Question = (TextView) this.findViewById(R.id.tv_question);
        btn_Submit = (Button) this.findViewById(R.id.btn_submit);
        et_answer = (EditText)this.findViewById(R.id.et_answer);

        ib_Back.setOnClickListener(this);
        btn_Submit.setOnClickListener(this);

        Intent intent = getIntent();
        updateQuestion = intent.getStringExtra("question");

        tv_Question.setText(updateQuestion);//更新问题
    }

    public static void actionStart(Context context,String question){
        Intent intent = new Intent(context,ExpertAnswerActivity.class);
        intent.putExtra("question",question);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_submit:
                submitDialog();
                break;
            default:
        }
    }

    private void submitDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("您是否提交所填答案？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ExpertAnswerActivity.this, et_answer.getText().toString(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }
//------------------------接口方法-----------------

    /**
     * 获取催一下状态
     * 0 为没有催
     * 1 为已经催
     * @return
     */
    @Override
    public int getUrgeState() {
        return 0;
    }

    /**
     * 获取赞一下状态（收藏状态）
     * 0 为没有赞
     * 1 为已经赞
     * @return
     */
    @Override
    public int getFavState() {
        return 0;
    }
//----------------------接口方法结束------------------
}
