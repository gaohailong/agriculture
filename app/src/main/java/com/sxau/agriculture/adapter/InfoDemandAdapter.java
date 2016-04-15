package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.InfoData;

/**
 * Created by Administrator on 2016/4/9.
 */
public class InfoDemandAdapter extends BaseAdapter {
    private Context context;
    private InfoData datas[];
    ViewHolder holder;

    public InfoDemandAdapter(Context context, InfoData[] datas) {
        this.context = context;
        this.datas = datas;
    }



    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_info_demand, null);
            holder = new ViewHolder();
            holder.ivHead= (ImageView) convertView.findViewById(R.id.rv_info_head);
            holder.name = (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title = (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ivLocation = (ImageView) convertView.findViewById(R.id.iv_demand_location);
            holder.ivCollection= (ImageView) convertView.findViewById(R.id.ib_demand_collection);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InfoData infoData = datas[position];
        holder.ivHead.setImageResource(infoData.getIvHead());
        holder.name.setText(infoData.getName());
        holder.date.setText(infoData.getDate());
        holder.distance.setText(infoData.getDistance());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getContent());
        holder.ivLocation.setImageResource(infoData.getIvLocation());
        holder.ivCollection.setTag(position);
        holder.ivCollection.setImageResource(infoData.getLvCollection());
        holder.ivCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((int)holder.ivCollection.getTag() == position) {
                holder.ivCollection.setImageResource(R.drawable.ic_search_48dp);
                Toast.makeText(context,datas[position]+"",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }



//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.ib_demand_collection:
//                Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
//                holder.ibCollection.setImageResource(R.drawable.ic_search_48dp);
////                ImageView img=new ImageView(context);
////                img.setImageResource(R.drawable.ic_search_48dp);
//
//                break;
//        }
//    }

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
//    public void ChangeCollection(int position){
//        Toast.makeText(context,position,Toast.LENGTH_SHORT).show();
//    }
}
