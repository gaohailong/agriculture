package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.activity.HomePushAdapter;

public class HomeFragment extends BaseFragment {


    private Context mContext;

/*


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    /* @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        *//* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*//*
    }*/
    private ListView lv_push;
    private View mView;
    HomePushAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        lv_push = (ListView) mView.findViewById(R.id.lv_push);

        PushBean[] pushs=new PushBean[6];
        pushs[0]=new PushBean();
        pushs[0].setRead("12万");
        pushs[0].setBrowseid(R.drawable.browse);
        pushs[0].setNewsid(R.drawable.dialsmall);
        pushs[0].setTime("2016.4.9");
        pushs[0].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[1]=new PushBean();
        pushs[1].setRead("12万");
        pushs[1].setBrowseid(R.drawable.browse);
        pushs[1].setNewsid(R.drawable.dialsmall);
        pushs[1].setTime("2016.4.9");
        pushs[1].setTitle("全面推动“三品一标”工作啦啦啦再上新台阶");

        pushs[2]=new PushBean();
        pushs[2].setRead("12万");
        pushs[2].setBrowseid(R.drawable.browse);
        pushs[2].setNewsid(R.drawable.dialsmall);
        pushs[2].setTime("2016.4.9");
        pushs[2].setTitle("帅子是煞笔");

        pushs[3]=new PushBean();
        pushs[3].setRead("12万");
        pushs[3].setBrowseid(R.drawable.browse);
        pushs[3].setNewsid(R.drawable.dialsmall);
        pushs[3].setTime("2016.4.9");
        pushs[3].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[4]=new PushBean();
        pushs[4].setRead("12万");
        pushs[4].setBrowseid(R.drawable.browse);
        pushs[4].setNewsid(R.drawable.dialsmall);
        pushs[4].setTime("2016.4.9");
        pushs[4].setTitle("全面推动“三品一标”工作再上新台阶");

        pushs[5]=new PushBean();
        pushs[5].setRead("12万");
        pushs[5].setBrowseid(R.drawable.browse);
        pushs[5].setNewsid(R.drawable.dialsmall);
        pushs[5].setTime("2016.4.9");
        pushs[5].setTitle("全面推动“三品一标”工作再上新台阶");

        adapter=new HomePushAdapter(pushs,HomeFragment.this.getActivity());
        lv_push.setAdapter(adapter);
        return mView;
    }

}
