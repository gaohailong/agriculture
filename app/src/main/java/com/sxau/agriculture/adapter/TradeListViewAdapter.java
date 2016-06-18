package com.sxau.agriculture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.utils.AuthTokenUtil;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 信息专区ListView的Adapter
 *
 * @author 田帅
 */
public class TradeListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TradeData> datas;
    private ViewHolder holder;
    private String authToken;
    private Map<Integer , Boolean> isCheckMap = new HashMap<Integer,Boolean>();

    public TradeListViewAdapter(Context context, ArrayList<TradeData> datas) {
        this.context = context;
        this.datas = datas;
        authToken = AuthTokenUtil.findAuthToken();
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_trade_demand, null);
            holder.ivHead = (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.name = (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title = (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ivCollection = (ImageView) convertView.findViewById(R.id.iv_demand_collection);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final TradeData infoData = datas.get(position);

        if (infoData.getUser().getAvatar() == null) {
            holder.ivHead.setImageResource(R.mipmap.img_default_user_portrait_150px);
        } else {
            Picasso.with(context).load(infoData.getUser().getAvatar()).resize(150, 150).centerCrop().placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.ivHead);
        }
        holder.name.setText(infoData.getUser().getName());
        holder.date.setText(TimeUtil.format(infoData.getWhenCreated()));
        holder.distance.setText(infoData.getUser().getAddress());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getDescription());
        holder.id = infoData.getId();
        Log.e("TradeList","Position:"+position+"  isFav:"+datas.get(position).isFav()+"  tradeId:"+datas.get(position).getId());

/*
        if (infoData.isFav()) {
//            isCheckMap.put(datas.get(position).getId(),true);
            holder.ivCollection.setImageResource(R.drawable.collection_fill);
        } else {
            holder.ivCollection.setImageResource(R.drawable.collection);
        }
        holder.ivCollection.setOnClickListener(new CollectionListener(infoData, infoData.isFav(),position));
        */
        return convertView;
    }

    /**
     * 将item中的点击事件隐藏掉，以后用
     * 目前存在的问题：data缓存存在问题，由于点击收藏后没有将本地的数据进行修改，所以存在显示数据错误问题
     * 疑惑1：数据成功在服务器端更新之后，在本地刷新出新的数据且缓存到本地，但是仍然显示没有收藏，这个问题想不到原因
     */

    /*
    /**
     * 收藏按钮点击事件

    public class CollectionListener implements View.OnClickListener {
        private int position;
        private boolean isCollection;
        private TradeData infoData;

        public CollectionListener(TradeData infoData, boolean isCollection,int position) {
            this.infoData = infoData;
            this.isCollection = isCollection;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            holder.ivCollection = (ImageView) v.findViewById(R.id.iv_demand_collection);
            if (holder.ivCollection.getId() == v.getId()) {
                if (isCollection) {
//                if (FavoBeansHelper.getInstance().contains(infoData)) {
                    //执行取消收藏操作
                    //使用乐观锁机制，后台执行更新操作
                    holder.ivCollection.setImageResource(R.drawable.collection);
                    isCollection = false;
                    //设置缓存的收藏状态变化
//                    infoData.setFav(isCollection);
//                    datas.get(position).setFav(isCollection);
//                    FavoBeansHelper.getInstance().removeFavo(infoData);

//                    isCheckMap.put(datas.get(position).getId(),isCollection);
//                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);

                    if (NetUtil.isNetAvailable(context)) {

                        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeFav.class).doUnCollectTrade(authToken, holder.id);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    //取消收藏执行成功
                                    Log.d("TradeListViewAdapter", "取消收藏" + infoData.getId());
                                    Log.d("TradeAdapter", "code:" + response.code());
                                } else {
                                    //取消收藏执行失败
                                    Log.e("TradeListViewAdapter", "code:" + response.code() + " body:" + response.body() + "  id:" + holder.id + "  message:" + response.message());

                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                //取消收藏执行失败
                                Log.e("TradeListViewAdapter", "执行失败");
                            }
                        });
                    } else {
                        showNetUnavailable();
                    }
                } else {
                    //执行收藏操作
                    //使用乐观锁机制，后台更新数据
                    holder.ivCollection.setImageResource(R.drawable.collection_fill);
                    isCollection = true;
                    //设置缓存收藏状态变化
//                    infoData.setFav(isCollection);
//                    datas.get(position).setFav(isCollection);
//                    isCheckMap.put(datas.get(position).getId(),isCollection);
//                    FavoBeansHelper.getInstance().addFavo(infoData);
//                    handler.sendEmptyMessage(ConstantUtil.GET_NET_DATA);

                    if (NetUtil.isNetAvailable(context)) {

                        Call<JsonObject> call = RetrofitUtil.getRetrofit().create(ITradeFav.class).doCollectTrade(authToken, holder.id);
                        call.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    //收藏执行成功
                                    Log.d("TradeListViewAdapter", "收藏成功" + infoData.getId());
                                    Log.d("TradeAdapter", "code:" + response.code());
                                } else {
                                    //收藏执行失败
                                    Log.e("TradeListViewAdapter", "code:" + response.code() + " body:" + response.body() + "  id:" + holder.id + "  message:" + response.message());
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                //收藏执行失败
                                Log.e("TradeListViewAdapter", "执行失败");
                            }
                        });
                    } else {
                        showNetUnavailable();
                    }
                }
            }
        }
    }
    */

    public void showRequestTimeout() {
        Toast.makeText(context, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
    }

    public void showNetUnavailable() {
        Toast.makeText(context, "网络不可用，请检查网络", Toast.LENGTH_LONG).show();
    }


    public class ViewHolder{
        ImageView ivCollection;
        ImageView ivHead;
        TextView name;
        TextView date;
        TextView distance;
        TextView title;
        TextView content;
        int id;

    }

}
