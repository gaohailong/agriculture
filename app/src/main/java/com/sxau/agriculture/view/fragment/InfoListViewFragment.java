package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.sxau.agriculture.adapter.InfoDemandAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.InfoData;
import com.sxau.agriculture.presenter.fragment_presenter.InfoListViewPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IInfoListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IInfoListViewFragment;

/**
 * 信息专区ListView
 * @author 田帅
 */
public class InfoListViewFragment extends BaseFragment implements IInfoListViewFragment {
        private  View mview;
        private ListView lv_Info;
        private ImageView iv_collection;

        private IInfoListViewPresenter iInfoListViewPresenter;


        public InfoListViewFragment() {
        }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

                //将InfoLvFragment与InfolvPresenter绑定
                iInfoListViewPresenter = new InfoListViewPresenter(InfoListViewFragment.this);


                mview=inflater.inflate(R.layout.fragment_info_listview, container, false);

                lv_Info= (ListView) mview.findViewById(R.id.lv_info);
                iv_collection= (ImageView) mview.findViewById(R.id.ib_demand_collection);


                InfoData[] infoDatas=new InfoData[5];

                infoDatas[0]=new InfoData();
                infoDatas[0].setIvHead(R.mipmap.img_default_user_portrait_150px);
                infoDatas[0].setName("天和集团");
                infoDatas[0].setDate("2013.06.22");
                infoDatas[0].setDistance("山西");
                infoDatas[0].setTitle("收购苹果两万斤");
                infoDatas[0].setIvLocation(R.drawable.ic_location_48dp);
                infoDatas[0].setLvCollection(R.drawable.ic_collect_having_48dp);
                infoDatas[0].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

                infoDatas[1]=new InfoData();
                infoDatas[1].setIvHead(R.mipmap.img_default_user_portrait_150px);
                infoDatas[1].setName("天和集团");
                infoDatas[1].setDate("2013.06.22");
                infoDatas[1].setDistance("山西");
                infoDatas[1].setTitle("收购苹果两万斤");
                infoDatas[1].setLvCollection(R.drawable.ic_collect_nothave_48dp);
                infoDatas[1].setIvLocation(R.drawable.ic_location_48dp);
                infoDatas[1].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

                infoDatas[2]=new InfoData();
                infoDatas[2].setIvHead(R.mipmap.img_default_user_portrait_150px);
                infoDatas[2].setName("天和集团");
                infoDatas[2].setDate("2013.06.22");
                infoDatas[2].setDistance("山西");
                infoDatas[2].setTitle("收购苹果两万斤收购苹果两万斤收购苹果两万斤收购苹果两万斤收购苹果两万斤");
                infoDatas[2].setLvCollection(R.drawable.ic_collect_have_48dp);
                infoDatas[2].setIvLocation(R.drawable.ic_location_48dp);
                infoDatas[2].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

                infoDatas[3]=new InfoData();
                infoDatas[3].setLvCollection(R.drawable.ic_collect_have_48dp);
                infoDatas[3].setIvHead(R.mipmap.img_default_user_portrait_150px);
                infoDatas[3].setName("天和集团");
                infoDatas[3].setDate("2013.06.22");
                infoDatas[3].setDistance("山西");
                infoDatas[3].setTitle("收购苹果两万斤");
                infoDatas[3].setIvLocation(R.drawable.ic_location_48dp);
                infoDatas[3].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

                infoDatas[4]=new InfoData();
                infoDatas[4].setLvCollection(R.drawable.ic_collect_have_48dp);
                infoDatas[4].setIvHead(R.mipmap.img_default_user_portrait_150px);
                infoDatas[4].setName("天和集团");
                infoDatas[4].setDate("2013.06.22");
                infoDatas[4].setDistance("山西");
                infoDatas[4].setIvLocation(R.drawable.ic_location_48dp);
                infoDatas[4].setTitle("收购苹果两万斤");
                infoDatas[4].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");


                BaseAdapter adapter=new InfoDemandAdapter(InfoListViewFragment.this.getActivity(),infoDatas);
                lv_Info.setAdapter(adapter);
                return mview;
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
//----------------接口方法结束-------------------
}
