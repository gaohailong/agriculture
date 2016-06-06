package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.sxau.agriculture.adapter.SelectPhotoAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.presenter.acitivity_presenter.TradeReleasePresenter;
import com.sxau.agriculture.presenter.activity_presenter_interface.ITradeReleasePresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.GlideLoaderUtil;
import com.sxau.agriculture.view.activity_interface.ITradeReleaseActivity;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Info发布供求界面
 *
 * @author 田帅.
 */
public class TradeReleaseActivity extends BaseActivity implements View.OnClickListener,ITradeReleaseActivity{
    private ImageView ivPhoto;
    private List<String> photoPath;
    private Spinner spinner;

    public static final int REQUEST_CODE = 1000;
    private SelectPhotoAdapter selectPhotoAdapter;
    private ArrayList<String> path = new ArrayList<>();
    private ITradeReleasePresenter iTradeReleasePresenter;
    private Handler mhandler;

    private ArrayList<String> spinData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade_release);
        spinner= (Spinner) findViewById(R.id.spinner);

        initView();
        ivPhoto.setOnClickListener(this);

        mhandler=new MyHandler();
        iTradeReleasePresenter=new TradeReleasePresenter(mhandler);
        spinData=new ArrayList<>();
        startNet();
    }

    public void initView() {
        ivPhoto = (ImageView) findViewById(R.id.iv_info_release_photo);

        RecyclerView recycler = (RecyclerView)findViewById(R.id.recycler);
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
        ImageSelector.open(TradeReleaseActivity.this, imageConfig);   // 开启图片选择器
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
    public void startNet(){
        initSpin();
        mhandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
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
    public void updateView() {

    }
    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.INIT_DATA:
                        iTradeReleasePresenter.doRequest();
                    break;
                case ConstantUtil.GET_NET_DATA:
                        spinData=iTradeReleasePresenter.getCategorieinfo();
                    initSpin();
                    break;
                default:
                    break;
            }
        }
    }
}


