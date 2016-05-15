package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.Question;

/**
 * 问题详细信息页面Activity
 * @author 李秉龙
 */
public class DetailQuestion extends BaseActivity implements View.OnClickListener{
    private ImageButton ib_Back;
    private ImageView iv_Fav;
    private ImageView iv_Quick;
    private Button btn_Answer;
    private TextView tv_Question;

    private int favIndex=0;//是否已赞 0：没有；1：已赞
    private int quickIndex=0;//是否已催 0：没有；1：已催

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
        iv_Fav = (ImageView) this.findViewById(R.id.iv_fav);
        iv_Quick = (ImageView) this.findViewById(R.id.iv_quick);
        btn_Answer = (Button) this.findViewById(R.id.btn_answer);
        tv_Question = (TextView) this.findViewById(R.id.tv_question);

        ib_Back.setOnClickListener(this);
        iv_Fav.setOnClickListener(this);
        iv_Quick.setOnClickListener(this);
        btn_Answer.setOnClickListener(this);

        question = tv_Question.getText().toString();



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_fav:
                if(favIndex==0){
                    iv_Fav.setBackgroundResource(R.drawable.ic_praise_48px);
                    favIndex=1;
                }else if (favIndex==1){
                    iv_Fav.setBackgroundResource(R.drawable.ic_no_praise_48px);
                    favIndex=0;
                }
                break;
            case R.id.iv_quick:
                if(quickIndex==0){
                    iv_Quick.setBackgroundResource(R.mipmap.ic_quick_false_48px);
                    quickIndex=1;
                }else if (quickIndex==1){
                    iv_Quick.setBackgroundResource(R.mipmap.ic_quick_true_48px);
                    quickIndex=0;
                }
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
