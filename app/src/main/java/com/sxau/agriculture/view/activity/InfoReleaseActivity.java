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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
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
import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

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
public class InfoReleaseActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageView ivPhoto;
//    private GridView gvPhoto;
    private List<String> photoPath;
    private ReleaseGridViewAdapter adapter;

    public static final int REQUEST_CODE = 1000;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_release);
        initView();
        ivPhoto.setOnClickListener(this);
//        initPhotoDatas();
        adapter = new ReleaseGridViewAdapter(InfoReleaseActivity.this, photoPath);
    }

    public void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);

        RecyclerView recycler = (RecyclerView) super.findViewById(R.id.recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter = new SelectPhotoAdapter(this, path);
        recycler.setAdapter(selectPhotoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_info_release_photo:
            //发布供求界面弹出选择照片
            showPhotoDialog();
            break;
            default:
                break;
        }
    }

    public void showPhotoDialog() {
        ImageConfig imageConfig
                = new ImageConfig.Builder(
                // GlideLoader 可用自己用的缓存库
                new GlideLoaderUtil())
                // 如果在 4.4 以上，则修改状态栏颜色 （默认黑色）
                .steepToolBarColor(getResources().getColor(R.color.mainColor))
                // 标题的背景颜色 （默认黑色）
                .titleBgColor(getResources().getColor(R.color.mainColor))
                // 提交按钮字体的颜色  （默认白色）
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                // 标题颜色 （默认白色）
                .titleTextColor(getResources().getColor(R.color.white))
                // 开启多选   （默认为多选）  (单选 为 singleSelect)
                .crop()
                // 多选时的最大数量   （默认 9 张）
                .mutiSelectMaxSize(9)
                // 已选择的图片路径
                .pathList(path)
                // 拍照后存放的图片路径（默认 /temp/picture）
                .filePath("/ImageSelector/Pictures")
                // 开启拍照功能 （默认开启）
                .showCamera()
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(InfoReleaseActivity.this, imageConfig);   // 开启图片选择器
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (String path : pathList) {
                Log.i("ImagePathList", path);
            }

            path.clear();
            path.addAll(pathList);
            adapter.notifyDataSetChanged();
        }
    }

   /* private void openPhotoGraph() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    private void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//调用android的图库
        startActivityForResult(intent, 2);
    }*/
/*
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
    }*/

    private void initPhotoDatas() {
        photoPath = new ArrayList<String>();
        photoPath.add("111111");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == adapter.getMaxPosition()) {
            showPhotoDialog();
        }
    }
}


