package com.sxau.agriculture.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.adapter.ReleaseGridViewAdapter;
import com.sxau.agriculture.agriculture.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Info发布供求界面
 *
 * @author 田帅.
 */
public class InfoReleaseActivity extends BaseActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private ImageView ivPhoto;
    private GridView gvPhoto;
    private List<String> photoPath;
    private ReleaseGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_release);
        initView();
        //ivPhoto.setOnClickListener(this);
        initPhotoDatas();
        adapter=new ReleaseGridViewAdapter(InfoReleaseActivity.this,photoPath);
        gvPhoto.setAdapter(adapter);
        gvPhoto.setOnItemClickListener(this);
    }

    public void initView() {
        //ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);
        gvPhoto= (GridView) findViewById(R.id.gv_info_release_photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //case R.id.iv_info_release_photo:
                //发布供求界面弹出选择照片
                //showPhotoDialog();
                //break;
            default:
                break;
        }
    }

    public void showPhotoDialog() {
        AlertDialog dialog = new AlertDialog.Builder(InfoReleaseActivity.this).create();
        View view = LayoutInflater.from(InfoReleaseActivity.this).inflate(R.layout.diallog_personal_head_select, null);
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        dialog.onWindowAttributesChanged(wl);

        TextView tv_PhotoGraph = (TextView) view.findViewById(R.id.tv_personal_photo_graph);
        TextView tv_PhotoAlbum = (TextView) view.findViewById(R.id.tv_personal_photo_album);

        tv_PhotoGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoReleaseActivity.this, "123", Toast.LENGTH_SHORT).show();
                openPhotoGraph();
            }
        });
        tv_PhotoAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(InfoReleaseActivity.this, "1231234", Toast.LENGTH_SHORT).show();
                openPhotoAlbum();
            }
        });
    }

    private void openPhotoGraph() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    private void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    String sdStatus = Environment.getExternalStorageState();
                    if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                        Toast.makeText(this, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    new DateFormat();
                    String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Toast.makeText(this, name, Toast.LENGTH_LONG).show();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                    FileOutputStream b = null;
                    File file = new File("/sdcard/Image/");
                    file.mkdirs();// 创建文件夹
                    String fileName = "/sdcard/Image/" + name;
                    try {
                        b = new FileOutputStream(fileName);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                    try {
                        b.flush();
                        b.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case 2:
                Uri uri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                String picturePath = cursor.getString(columnIndex);
                photoPath.add(picturePath);
                photoPath.remove("111111");
                adapter.notifyDataSetChanged();
                cursor.close();

                break;

        }
    }
    private void initPhotoDatas(){
        photoPath=new ArrayList<String>();
        photoPath.add("111111");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position==adapter.getMaxPosition()){
            showPhotoDialog();
        }
        }


    }


