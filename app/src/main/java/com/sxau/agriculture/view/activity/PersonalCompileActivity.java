
package com.sxau.agriculture.view.activity;

        import android.os.Bundle;
        import android.view.View;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.sxau.agriculture.agriculture.R;
        import com.sxau.agriculture.presenter.acitivity_presenter.PersonalCompilePresenter;
        import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCompilePresenter;
        import com.sxau.agriculture.view.activity_interface.IPersonalCompileActivity;
        import com.sxau.agriculture.widgets.RoundImageView;

/**
 * Created by Administrator on 2016/4/13.
 */
public class PersonalCompileActivity extends BaseActivity implements View.OnClickListener ,IPersonalCompileActivity {
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

    private IPersonalCompilePresenter iPersonalCompilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persional_compile);

        //将Activity与Presenter进行绑定
        iPersonalCompilePresenter = new PersonalCompilePresenter(PersonalCompileActivity.this);

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
                Toast.makeText(PersonalCompileActivity.this,"1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_nick:
                Toast.makeText(PersonalCompileActivity.this,"2",Toast.LENGTH_SHORT).show();

                break;
            case R.id.tv_phone_number:
                Toast.makeText(PersonalCompileActivity.this,"3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_address:
                Toast.makeText(PersonalCompileActivity.this,"4",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_user_identity:
                Toast.makeText(PersonalCompileActivity.this,"5",Toast.LENGTH_SHORT).show();
                break;
            default:
        }

    }

//--------------------接口方法--------------------
    @Override
    public void setHead() {

    }

    @Override
    public void setNickName() {

    }

    @Override
    public void setPhone() {

    }

    @Override
    public void setUserPosition() {

    }

    @Override
    public void setIdentity() {

    }

    @Override
    public String getHead() {
        return null;
    }

    @Override
    public String getNickName() {
        return null;
    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public String getUserPosition() {
        return null;
    }

    @Override
    public String getIdentity() {
        return null;
    }
//---------------------接口方法结束--------------------
}
