package com.sxau.agriculture.view.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;

import java.io.File;


public class AskQuestion extends AppCompatActivity implements View.OnClickListener {
    private ImageButton ib_back;
    private Button btn_submit;
    private Button btn_add_picture;
    private EditText et_question;
    private EditText et_question_info;
    private static final int REQUEST_CODE_ALBUM = 1;//相册
    private static final int REQUEST_CODE_CAMERA = 2;//相机
    private String photoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        ib_back = (ImageButton) findViewById(R.id.ib_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_add_picture = (Button) findViewById(R.id.btn_add_picture);
        et_question = (EditText) findViewById(R.id.et_question);
        et_question_info = (EditText) findViewById(R.id.et_question_info);

        ib_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_add_picture.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_submit:
                //提交网络请求发送问题
                break;
            case R.id.btn_add_picture:
                AddPicture();
                break;
            case R.id.ll_photo:
                getPhotoFromCamera();
                break;
            case R.id.ll_photogroup:
                getPhotoFromAlbum();
                break;
            default:
                break;
        }
    }

    public void AddPicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AskQuestion.this);
        final View view = LayoutInflater.from(AskQuestion.this).inflate(R.layout.dialog_photo, null);
        builder.setView(view);
        LinearLayout ll_photo = (LinearLayout) view.findViewById(R.id.ll_photo);
        LinearLayout ll_photogroup = (LinearLayout) view.findViewById(R.id.ll_photogroup);
        ll_photo.setOnClickListener(this);
        ll_photogroup.setOnClickListener(this);
        builder.show();
    }

    //从相册获取图片
    public void getPhotoFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }

    //拍照获取图片
    public void getPhotoFromCamera() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent cameraintent = new Intent("android.media.action.IMAGE_CAPTURE");
            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (!file.exists()) {
                file.mkdir();
            }
            photoPath = Environment.DIRECTORY_PICTURES + System.currentTimeMillis() + ".jpg";
            cameraintent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            cameraintent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            startActivityForResult(cameraintent, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(AskQuestion.this, "请确认已插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    //回调函数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_ALBUM) {
                Uri uri = data.getData();
            } else if (requestCode == REQUEST_CODE_CAMERA) {
                Uri uri = data.getData();
                if (uri == null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap photo = (Bitmap) bundle.get("data");

                    }
                }
            }
        }
    }
}
