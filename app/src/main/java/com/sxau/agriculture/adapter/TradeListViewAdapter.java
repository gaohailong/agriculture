package com.sxau.agriculture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;
import com.sxau.agriculture.utils.TimeUtil;

import java.util.ArrayList;

/**
 * 信息专区ListView的Adapter
 *
 * @author 田帅
 */
public class TradeListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TradeData> datas;
    private ViewHolder holder;

    public TradeListViewAdapter(Context context, ArrayList<TradeData> datas) {
        this.context = context;
        this.datas = datas;
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
        TradeData infoData = datas.get(position);
        if (infoData.getUser().getAvatar() == null) {
            holder.ivHead.setImageResource(R.mipmap.img_default_user_portrait_150px);
        } else {
            Picasso.with(context).load(infoData.getUser().getAvatar()).resize(150, 150).centerCrop().placeholder(R.mipmap.ic_loading).error(R.mipmap.ic_load_fail).into(holder.ivHead);
        }
        holder.name.setText(infoData.getUser().getName());
//        Log.e("responseCode1", infoData.getUser().getName());
        holder.date.setText(TimeUtil.format(infoData.getWhenCreated()));
        holder.distance.setText(infoData.getUser().getAddress());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getDescription());

        if (infoData.isFav()) {
            holder.ivCollection.setImageResource(R.drawable.collection_fill);
        } else {
            holder.ivCollection.setImageResource(R.drawable.collection);
        }
        holder.ivCollection.setOnClickListener(new CollectionListener(position, infoData.isFav()));
        return convertView;
    }

    /**
     * 收藏按钮点击事件
     */
   public class CollectionListener implements View.OnClickListener {
        private int position;
        private boolean isCollection;

        public CollectionListener(int position, boolean isCollection) {
            this.position = position;
            this.isCollection = isCollection;
        }

        @Override
        public void onClick(View v) {
            holder.ivCollection = (ImageView) v.findViewById(R.id.iv_demand_collection);
            if (holder.ivCollection.getId() == v.getId()) {
                if (isCollection) {
                    holder.ivCollection.setImageResource(R.drawable.collection);
                    Log.d("TradeListViewAdapter", "取消收藏" + position);
                    isCollection = false;
                } else {
                    holder.ivCollection.setImageResource(R.drawable.collection_fill);
                    Log.d("TradeListViewAdapter", "收藏成功" + position);
                    isCollection = true;
                }
            }
        }
    }

    public class ViewHolder {
        ImageView ivCollection;
        ImageView ivHead;
        TextView name;
        TextView date;
        TextView distance;
        TextView title;
        TextView content;
    }

}
