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
import com.sxau.agriculture.bean.Question;

/**
<<<<<<< HEAD
 * 问题详细信息页面
 * 李秉龙
=======
 * 问题详情Activity
>>>>>>> 191c6b3d66869a39634014e8597fb7d643371ba8
 */
public class DetailQuestion extends BaseActivity implements View.OnClickListener{
    private ImageButton ib_Back;
    private ImageButton ib_Fav;
    private ImageButton ib_Quick;
    private Button btn_Answer;
    private TextView tv_Question;

    String question = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_question);
        initView();
        Intent intent = getIntent();
        int indexPosition = intent.getIntExtra("indexPosition",0);
        Toast.makeText(DetailQuestion.this,indexPosition+"",Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        ib_Back = (ImageButton) this.findViewById(R.id.ib_back);
        ib_Fav = (ImageButton) this.findViewById(R.id.ib_fav);
        ib_Quick = (ImageButton) this.findViewById(R.id.ib_quick);
        btn_Answer = (Button) this.findViewById(R.id.btn_answer);
        tv_Question = (TextView) this.findViewById(R.id.tv_question);

        ib_Back.setOnClickListener(this);
        ib_Fav.setOnClickListener(this);
        ib_Quick.setOnClickListener(this);
        btn_Answer.setOnClickListener(this);

        question = tv_Question.getText().toString();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_fav:
                Toast.makeText(DetailQuestion.this,"fav",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_quick:
                Toast.makeText(DetailQuestion.this,"quick",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_answer:

                ExpertAnswerActivity.actionStart(DetailQuestion.this,question);
                break;
            default:
                break;
        }
    }
    public static void actionStart(Context context,int position){
        Intent intent = new Intent(context,DetailQuestion.class);
        intent.putExtra("indexPosition",position);
        context.startActivity(intent);
    }
}
