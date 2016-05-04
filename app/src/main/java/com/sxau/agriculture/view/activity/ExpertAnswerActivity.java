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

public class ExpertAnswerActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ib_Back;
    private TextView tv_Question;
    private Button btn_Submit;

    private String updateQuestion;//更新问题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_answer);

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
}
