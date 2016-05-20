package com.sxau.agriculture.view.fragment;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxau.agriculture.adapter.InfoDemandAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.InfoData;

import com.sxau.agriculture.view.activity.InfoContentActivity;
import com.sxau.agriculture.view.activity.InfoReleaseActivity;
import com.sxau.agriculture.view.activity.MainActivity;

import com.sxau.agriculture.presenter.fragment_presenter.InfoListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IInfoListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IInfoListViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息专区ListView
 *
 * @author 田帅
 */


public class InfoListViewFragment extends BaseFragment implements IInfoListViewFragment, AdapterView.OnItemClickListener ,View.OnTouchListener{
    private View mview;
    private ListView lv_Info;
    private ImageView iv_collection;
    private InfoData minfoData;
    private float startX, startY, offsetX, offsetY; //计算触摸偏移量

    private List<InfoData> infoDatas = new ArrayList<InfoData>();

    private IInfoListViewPresenter iInfoListViewPresenter;


    public InfoListViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //将InfoLvFragment与InfolvPresenter绑定
        iInfoListViewPresenter = new InfoListViewPresenter(InfoListViewFragment.this);

        mview = inflater.inflate(R.layout.fragment_info_listview, container, false);

        lv_Info = (ListView) mview.findViewById(R.id.lv_info);
        iv_collection = (ImageView) mview.findViewById(R.id.iv_demand_collection);



        initInfoData();
        BaseAdapter adapter = new InfoDemandAdapter(InfoListViewFragment.this.getActivity(), infoDatas);
        lv_Info.setAdapter(adapter);
        lv_Info.setOnItemClickListener(this);
        lv_Info.setOnTouchListener(this);
        return mview;
    }


    public void initInfoData() {
        InfoData infoData = new InfoData(R.drawable.ic_collect_have_48dp, R.mipmap.img_default_user_portrait_150px, "天和集团", "2013.06.22", "2千米", "收购苹果两万斤", "对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于", R.drawable.ic_location_48dp);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
        infoDatas.add(infoData);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(InfoListViewFragment.this.getActivity(), InfoContentActivity.class);
        startActivity(intent);
    }

    //------------------接口方法-------------------
    @Override
    public void updateView() {

    }

    @Override
    public void changeItemView() {

    }

    /**
     * 获取收藏的状态，是否已经收藏
     * 1代表已经收藏
     * 2代表没有收藏
     */
    @Override
    public int getCollectState() {
        return 0;
    }

    /**
     * 实现滑动屏幕隐藏浮动按钮和显示按钮效果
    * */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                Log.d("test", "1");
                break;
            case MotionEvent.ACTION_UP:
                offsetX = event.getX() - startX;
                offsetY = event.getY() - startY;
                Log.d("test","2");

                if (offsetY < 0) {
                    new TradeFragment().btn_info_float.setVisibility(View.INVISIBLE);
                    Log.d("test","123");
                }
                if (offsetY>0){
                    new TradeFragment().btn_info_float.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;

        }return false;
    }


//----------------接口方法结束-------------------
}
