package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.ICategoriesData;
import com.sxau.agriculture.bean.CategorieData;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.AskQuestion;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 问答页面list的Fragment
 * @author 李秉龙
 */
public class QuestionFragment extends BaseFragment implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private View mView;
    private ViewPager vPager = null;
    public static Button btn_ask;
    private Context context;
    private ArrayList<String> list;
    private ArrayList<CategorieData> categorieDatas;
    private MyHandler myHandler;
    private int categorieId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FragmentPagerItems.Creator creater;//对标题的动态添加

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_question, container, false);

        vPager = (ViewPager) mView.findViewById(R.id.vp_question_viewpager);
        btn_ask= (Button) mView.findViewById(R.id.btn_ask);
        btn_ask.setOnClickListener(this);
        myHandler=new MyHandler();
        categorieDatas=new ArrayList<>();
        context=QuestionFragment.this.getActivity();
        list =new ArrayList<>();

        sharedPreferences=getActivity().getSharedPreferences("cate",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        list.add("等待加载");

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addData();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);

    }

    //伴随数据动态加载fragment
    public void addData(){
        creater = FragmentPagerItems.with(context);
        for (int i = 0; i < list.size(); i++) {
            creater.add(list.get(i), QuestionListViewFragment.class);
        }
        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
                , creater.create());
        vPager.setAdapter(fragmentadapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) mView.findViewById(R.id.viewpager_question_title);
        viewPagerTab.setViewPager(vPager);
        vPager.setOnPageChangeListener(this);
    }

    public class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ConstantUtil.INIT_DATA:
                    getCategorie();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    addData();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(context, AskQuestion.class);
        startActivity(intent);
    }

    public void getCategorie(){
        Call<ArrayList<CategorieData>> call= RetrofitUtil.getRetrofit().create(ICategoriesData.class).getCategories();
        call.enqueue(new Callback<ArrayList<CategorieData>>() {
            @Override
            public void onResponse(Response<ArrayList<CategorieData>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    categorieDatas = response.body();
                    if (categorieDatas == null || categorieDatas.size() == 0) {
                        list.add("问题列表");
                    } else {
                        list.clear();
                        getCategorieInfo();
                        categorieId = categorieDatas.get(0).getId();
                        editor.putInt("cateId",categorieId);
                        editor.commit();
                        Log.d("333", categorieId + "");
                    }
                }
                myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


    public void getCategorieInfo(){
        for (int i=0;i<categorieDatas.size();i++){
            list.add(categorieDatas.get(i).getName());
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        categorieId=categorieDatas.get(position).getId();
        Log.d("111", String.valueOf(categorieId));
        Log.d("222", position+"");
        pushCategorieId(categorieId);
    }

    public void pushCategorieId(int id){

        editor.putInt("cateId",id);
        Log.d("444", id+"");
        editor.commit();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
