
package com.sxau.agriculture.view.activity;

        import android.app.Activity;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.os.Bundle;
        import android.util.AttributeSet;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.sxau.agriculture.agriculture.R;
        import com.sxau.agriculture.widgets.RoundImageView;

/**
 * Created by Administrator on 2016/4/13.
 */
public class PersonalCompile extends BaseActivity implements View.OnClickListener {
    private ImageButton ib_Back;
    private RoundImageView rw_Head;
    private TextView tv_HeadPortrait;
    private TextView tv_NickName;
    private TextView tv_UserNick;
    private TextView tv_Telephone;
    private TextView tv_PhoneNumber;
    private TextView tv_Address;
    private TextView tv_UserAddress;
    private TextView tv_Identity;
    private TextView tv_UserIdentity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_compile);
        initView();
    }

    private void initView() {
        ib_Back = (ImageButton) this.findViewById(R.id.ib_back);
        tv_HeadPortrait = (TextView) this.findViewById(R.id.tv_head_portrait);
        rw_Head = (RoundImageView) this.findViewById(R.id.rw_head);
        tv_NickName = (TextView) this.findViewById(R.id.tv_nick_name);
        tv_UserNick = (TextView) this.findViewById(R.id.tv_user_nick);
        tv_Telephone = (TextView) this.findViewById(R.id.tv_telephone);
        tv_PhoneNumber = (TextView) this.findViewById(R.id.tv_phone_number);
        tv_Address = (TextView) this.findViewById(R.id.tv_address);
        tv_UserAddress = (TextView) this.findViewById(R.id.tv_user_address);
        tv_Identity = (TextView) this.findViewById(R.id.tv_identity);
        tv_UserIdentity = (TextView) this.findViewById(R.id.tv_user_identity);

        ib_Back.setOnClickListener(this);
        rw_Head.setOnClickListener(this);
        tv_UserNick.setOnClickListener(this);
        tv_PhoneNumber.setOnClickListener(this);
        tv_UserAddress.setOnClickListener(this);
        tv_UserIdentity.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rw_head:
                Toast.makeText(PersonalCompile.this,"1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_nick:
                Toast.makeText(PersonalCompile.this,"2",Toast.LENGTH_SHORT).show();

                break;
            case R.id.tv_phone_number:
                Toast.makeText(PersonalCompile.this,"3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_address:
                Toast.makeText(PersonalCompile.this,"4",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_identity:
                Toast.makeText(PersonalCompile.this,"5",Toast.LENGTH_SHORT).show();
                break;
            default:
        }

    }
}
