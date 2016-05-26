package com.sxau.agriculture.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;


public class AskQuestion extends AppCompatActivity implements View.OnClickListener {
    private ImageView ib_photo;
    private Button btn_submit;
    private EditText et_title;
    private EditText editText;
    private RecyclerView recycler;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path=new ArrayList<>();

    private static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        ib_photo = (ImageView) findViewById(R.id.ib_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_title = (EditText) findViewById(R.id.et_title);
        editText = (EditText) findViewById(R.id.editText);
        recycler= (RecyclerView) findViewById(R.id.recycler);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter=new SelectPhotoAdapter(this,path);
        recycler.setAdapter(selectPhotoAdapter);

        ib_photo.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_photo:
                showPhotoDialog();
                break;
            case R.id.btn_submit:
                //提交网络请求发送问题
                break;
            default:
                break;
        }
    }


    public void showPhotoDialog(){
        ImageConfig imageConfig
                = new ImageConfig.Builder(
                new GlideLoaderUtil())
                .steepToolBarColor(getResources().getColor(R.color.mainColor))
                .titleBgColor(getResources().getColor(R.color.mainColor))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                .crop()
                .mutiSelectMaxSize(9)
                .pathList(path)
                .filePath("/ImageSelector/Pictures")
                .showCamera()
                .requestCode(REQUEST_CODE)
                .build();
        ImageSelector.open(AskQuestion.this,imageConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                //TODO 将网络上传写到这
                Log.i("ImagePathList", path);
            }
            path.clear();
            path.addAll(pathList);
            selectPhotoAdapter.notifyDataSetChanged();
        }
    }
}
