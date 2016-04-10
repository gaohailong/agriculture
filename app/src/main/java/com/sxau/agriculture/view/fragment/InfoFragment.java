package com.sxau.agriculture.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;

public class InfoFragment extends Fragment implements View.OnClickListener{
private  View mview;
    private ListView lv_Info;
//    private ImageButton ibHead;
//    private TextView name;
//    private TextView date;
//    private TextView distance;
//    private TextView title;
//    private TextView content;
    public InfoFragment() {
    }
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview=inflater.inflate(R.layout.fragment_info, container, false);
        lv_Info= (ListView) mview.findViewById(R.id.lv_info);
//        ibHead= (ImageButton) mview.findViewById(R.id.ib_demand_head);
//        name= (TextView) mview.findViewById(R.id.tv_demand_name);
//        date= (TextView) mview.findViewById(R.id.tv_demand_date);
//        distance= (TextView) mview.findViewById(R.id.tv_demand_distance);
//        title= (TextView) mview.findViewById(R.id.tv_demand_title);
//        content= (TextView) mview.findViewById(R.id.tv_demand_content);

        InfoData[] infoDatas=new InfoData[5];

        infoDatas[0]=new InfoData();
        infoDatas[0].setIbHead(R.drawable.touxiang);
        infoDatas[0].setName("天和集团");
        infoDatas[0].setDate("2013.06.22");
        infoDatas[0].setDistance("2千米");
        infoDatas[0].setTitle("收购苹果两万斤");
        infoDatas[0].setIbDingwei(R.drawable.dingwei);
        infoDatas[0].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

    infoDatas[1]=new InfoData();
    infoDatas[1].setIbHead(R.drawable.touxiang);
    infoDatas[1].setName("天和集团");
    infoDatas[1].setDate("2013.06.22");
    infoDatas[1].setDistance("2千米");
    infoDatas[1].setTitle("收购苹果两万斤");
    infoDatas[1].setIbDingwei(R.drawable.dingwei);

    infoDatas[1].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

    infoDatas[2]=new InfoData();
    infoDatas[2].setIbHead(R.drawable.touxiang);
    infoDatas[2].setName("天和集团");
    infoDatas[2].setDate("2013.06.22");
    infoDatas[2].setDistance("2千米");
    infoDatas[2].setTitle("收购苹果两万斤");
    infoDatas[2].setIbDingwei(R.drawable.dingwei);

    infoDatas[2].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

    infoDatas[3]=new InfoData();
    infoDatas[3].setIbHead(R.drawable.touxiang);
    infoDatas[3].setName("天和集团");
    infoDatas[3].setDate("2013.06.22");
    infoDatas[3].setDistance("2千米");
    infoDatas[3].setTitle("收购苹果两万斤");
    infoDatas[3].setIbDingwei(R.drawable.dingwei);

    infoDatas[3].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

    infoDatas[4]=new InfoData();
    infoDatas[4].setIbHead(R.drawable.touxiang);
    infoDatas[4].setName("天和集团");
    infoDatas[4].setDate("2013.06.22");
    infoDatas[4].setDistance("2千米");
    infoDatas[4].setIbDingwei(R.drawable.dingwei);

    infoDatas[4].setTitle("收购苹果两万斤");
    infoDatas[4].setContent("对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于对于");

        BaseAdapter adapter=new InfoDemandAdapter(InfoFragment.this.getActivity(),infoDatas);
        lv_Info.setAdapter(adapter);
    return mview;

    }

    @Override
    public void onClick(View v) {

    }
}
