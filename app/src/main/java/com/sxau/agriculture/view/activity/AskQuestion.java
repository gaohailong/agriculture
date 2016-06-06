package com.sxau.agriculture.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 提问页面
 * @author 崔志泽
 */
public class AskQuestion extends AppCompatActivity implements View.OnClickListener {
    private ImageView ib_photo;
    private Button btn_submit;
    private EditText et_title;
    private EditText editText;
    private RecyclerView recycler;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path=new ArrayList<>();
    private Spinner spinner;
    private ArrayList<String> spinData;
    private MyHandler myHandler;
    private ArrayList<CategorieData> categorieDatas;
    private String cat;

    private static final int REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        ib_photo = (ImageView) findViewById(R.id.ib_photo);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        et_title = (EditText) findViewById(R.id.et_trade_title);
        editText = (EditText) findViewById(R.id.et_trade_content);
        recycler= (RecyclerView) findViewById(R.id.recycler);
        spinner= (Spinner) findViewById(R.id.sp_trade_cotegory);


        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,3);
        recycler.setLayoutManager(gridLayoutManager);
        selectPhotoAdapter=new SelectPhotoAdapter(this,path);
        recycler.setAdapter(selectPhotoAdapter);
        spinData=new ArrayList<>();
        myHandler=new MyHandler();
        categorieDatas=new ArrayList<>();


        ib_photo.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

        startNet();
    }

    public void startNet(){
        initSpin();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    /**
     * 初始化下拉菜单
     */
    public void initSpin(){
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinData);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
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


    /**
     * 获取图片的方法
     */
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

    /**
     * 回调函数
     */
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


    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.INIT_DATA:
                    getCategories();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    getCategorieinfo();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 网络请求
     */
    public void getCategories(){
        Call<ArrayList<CategorieData>> call= RetrofitUtil.getRetrofit().create(ICategoriesData.class).getCategories();
        call.enqueue(new Callback<ArrayList<CategorieData>>() {
            @Override
            public void onResponse(Response<ArrayList<CategorieData>> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    categorieDatas=response.body();
                    myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    /**
     * 遍历请求数据获取分类信息
     */
    public void getCategorieinfo(){
        for (int i=0;i<categorieDatas.size();i++){
            cat=categorieDatas.get(i).getName();
            spinData.add(cat);
        }
        initSpin();
    }
}
