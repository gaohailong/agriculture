package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.acitivity_presenter.ExpertAnswerPresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IExpertAnswerPresenter;
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

public class ExpertAnswerActivity extends BaseActivity implements View.OnClickListener ,IExpertAnswerActivity{
    private ImageButton ib_Back;
    private TextView tv_Question;
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
                Toast.makeText(ExpertAnswerActivity.this,"提交",Toast.LENGTH_SHORT).show();
                break;

            default:

        }
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
