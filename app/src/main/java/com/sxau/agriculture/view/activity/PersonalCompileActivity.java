
package com.sxau.agriculture.view.activity;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.widgets.RoundImageView;

import java.io.File;

import com.sxau.agriculture.presenter.acitivity_presenter.PersonalCompilePresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCompilePresenter;
import com.sxau.agriculture.view.activity_interface.IPersonalCompileActivity;

/**
 * 修改个人信息Activity
 *
 * @author 李秉龙
 */
public class PersonalCompileActivity extends BaseActivity implements View.OnClickListener, IPersonalCompileActivity {
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
    private Button btn_finish;

    /**
     * 定义三种状态
     */
    private static final int HEAD_PORTRAIT_PIC = 1;//相册
    private static final int HEAD_PORTRAIT_CAM = 2;//相机
    private static final int HEAD_PORTRAIT_CUT = 3;//图片裁剪
    private File photoFile;
    private Bitmap photoBitmap;

    //编辑个人昵称
    private String compileNickname;
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
        btn_finish = (Button) this.findViewById(R.id.btn_finish);

        ib_Back.setOnClickListener(this);
        rw_Head.setOnClickListener(this);
        tv_UserNick.setOnClickListener(this);
        tv_PhoneNumber.setOnClickListener(this);
        tv_UserAddress.setOnClickListener(this);
        tv_UserIdentity.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.rw_head:
                showDialog();
                break;
            case R.id.tv_user_nick:
                showCompileDialog();
                break;
            case R.id.tv_user_address:
                Intent intent = new Intent(PersonalCompileActivity.this,
                        ThreeLevelLinkage.class);
                startActivityForResult(intent, 1000);// requestCode

                break;
            case R.id.btn_finish:
                finish();
                break;
            default:
                break;
        }

    }


    //编辑昵称等 调用的dialog
    private void showCompileDialog() {
        final EditText et = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //使用xml文件定义视图

        builder.setTitle("编辑昵称：");
        builder.setView(et);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (et.getText().equals(null)) {
                    Toast.makeText(PersonalCompileActivity.this, "请输入您的昵称！", Toast.LENGTH_SHORT).show();
                } else {
                    compileNickname = et.getText().toString();
                    tv_UserNick.setText(compileNickname);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    //显示Dialog选择拍照还是从相册选择
    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.PhotoDialog);
        final View view = LayoutInflater.from(PersonalCompileActivity.this).inflate(R.layout.diallog_personal_head_select, null);
        dialog.setContentView(view);
        TextView tv_PhotoGraph = (TextView) view.findViewById(R.id.tv_personal_photo_graph);
        TextView tv_PhotoAlbum = (TextView) view.findViewById(R.id.tv_personal_photo_album);
        TextView tv_Cancel = (TextView) view.findViewById(R.id.tv_cancel);


        tv_PhotoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoGraph();
            }
        });

        tv_PhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhotoAlbum();
            }
        });

        tv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        //设置出现Dialog位置
        Window window = dialog.getWindow();
        // 可以在此设置显示动画
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        dialog.show();

    }

    //打开相册方法
    private void openPhotoAlbum() {
        Intent picIntent = new Intent(Intent.ACTION_PICK, null);
        picIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(picIntent, HEAD_PORTRAIT_PIC);
    }

    //打开相机方法
    private void openPhotoGraph() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdirs();
            }
            photoFile = new File(file, System.currentTimeMillis() + ".jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(intent, HEAD_PORTRAIT_CAM);
        } else {
            Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    //回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case HEAD_PORTRAIT_CAM:
                    startPhotoZoom(Uri.fromFile(photoFile));
                    break;
                case HEAD_PORTRAIT_PIC:

                    if (data == null || data.getData() == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());

                    break;
                case HEAD_PORTRAIT_CUT:

                    if (data != null) {
                        setPicToView(data);
                    }
                    break;
            }
        }
        if(requestCode==1000&&resultCode==1001){
            String citystr = data.getStringExtra("result");
            Toast.makeText(this,citystr,Toast.LENGTH_SHORT).show();
        }
    }

    //将图片加载到View上
    private void setPicToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            //这里也可以做文件上传
            photoBitmap = bundle.getParcelable("data");
            rw_Head.setImageBitmap(photoBitmap);
        }
    }

    /**
     * 打开系统图片裁剪功能
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true); //黑边
        intent.putExtra("scaleUpIfNeeded", true); //黑边
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, HEAD_PORTRAIT_CUT);
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