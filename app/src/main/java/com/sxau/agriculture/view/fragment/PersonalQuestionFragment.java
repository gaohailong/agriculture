package com.sxau.agriculture.view.fragment;


import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sxau.agriculture.adapter.PersonalQuestionAdapter;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.api.IPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.presenter.fragment_presenter.PersonalQuestionPresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.DetailQuestion;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 个人中心问题的listView的fragment
 * @author 李秉龙
 */
public class PersonalQuestionFragment extends BaseFragment implements IPersonalQuestionFragment{
    private ListView listView;

    private IPersonalQuestionPresenter iPersonalQuestionPresenter;
    private ArrayList<MyPersonalQuestion> mquestionslist;
    private PersonalQuestionAdapter adapter;
    private MyHandler myHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //将Fragment与Presenter绑定
        iPersonalQuestionPresenter = new PersonalQuestionPresenter(PersonalQuestionFragment.this);

        View myQuestionView = inflater.inflate(R.layout.frament_personal_myquestion, null);
        listView = (ListView) myQuestionView.findViewById(R.id.lv_MyQuestionListView);
        mquestionslist = new ArrayList<MyPersonalQuestion>();
        myHandler = new MyHandler();
     return myQuestionView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListView();
        myHandler.sendEmptyMessage(ConstantUtil.INIT_DATA);
    }

    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.INIT_DATA:
                    getPersonalQuestionDate();
                    break;
                case ConstantUtil.GET_NET_DATA:
                    initListView();
                    break;
            }
        }
    }


    private void initListView(){

        adapter = new PersonalQuestionAdapter(PersonalQuestionFragment.this.getActivity(),mquestionslist);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailQuestion.actionStart(PersonalQuestionFragment.this.getActivity(), position);
            }
        });
    }
    //获得个人问题列表网络的数据
    private void getPersonalQuestionDate(){
        Call<ArrayList<MyPersonalQuestion>> call = RetrofitUtil.getRetrofit().create(IPersonalQuestion.class).getMessage();
        call.enqueue(new Callback<ArrayList<MyPersonalQuestion>>() {
            @Override
            public void onResponse(Response<ArrayList<MyPersonalQuestion>> response, Retrofit retrofit) {
                Log.d("aaaaaaa", response.code()+"");

                if (response.isSuccess()){
                mquestionslist = response.body();
                Log.d("aaaaaaa",mquestionslist.size()+"");
                myHandler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);

                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }


//-----------------接口方法----------------
    @Override
    public void updateView() {

    }
//----------------接口方法结束------------------
}
