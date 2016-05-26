package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.TradeData;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 信息专区ListView的Adapter
 * @author 田帅
 */
public class TradeDemandAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<TradeData> datas;
    ViewHolder holder;
    private boolean flag=true;

    public TradeDemandAdapter(Context context, List<TradeData> datas) {
        this.context = context;
        this.datas=datas;
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
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_trade_demand, null);
            holder = new ViewHolder();
            holder.ivHead= (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.name = (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title = (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ivLocation = (ImageView) convertView.findViewById(R.id.iv_demand_location);
            holder.ivCollection= (ImageView) convertView.findViewById(R.id.iv_demand_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TradeData infoData=datas.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        setImage();
        holder.ivHead.setImageResource(R.drawable.btg_icon_checkmark);
        holder.name.setText(infoData.getUser().getName());
        holder.date.setText(sdf.format(infoData.getWhenCreated()));
        holder.distance.setText("2千米");
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getDescription());
        holder.ivLocation.setImageResource(R.drawable.ic_location_48dp);
        holder.ivCollection.setImageResource(R.drawable.ic_collect_have_48dp);
        holder.ivCollection.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_demand_collection:
                holder.ivCollection= (ImageView) v.findViewById(R.id.iv_demand_collection);
                if (flag){
                    holder.ivCollection.setImageResource(R.drawable.ic_collect_have_48dp);
                    flag=false;
                }else {
                    holder.ivCollection.setImageResource(R.drawable.ic_collect_nothave_48dp);
                    flag=true;
                }

                break;
        }
    }
    public void setImage(){
        String imageUrl="";
        for (int i=0;i<datas.size();i++){
            if (datas.get(i).equals("image")){
                imageUrl=datas.get(i).getImages();
            }
        }
//        Picasso.with(context).load(imageUrl).into(holder.ivHead);
    }


    public class ViewHolder {
        ImageView ivCollection;
        ImageView ivHead;
        TextView name;
        TextView date;
        TextView distance;
        TextView title;
        TextView content;
        ImageView ivLocation;
    }

}
