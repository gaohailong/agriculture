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

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.InfoData;

import java.util.List;

/**
 * 信息专区ListView的Adapter
 * @author 田帅
 */
public class InfoDemandAdapter extends BaseAdapter implements View.OnClickListener{
    private Context context;
    private List<InfoData> datas;
    ViewHolder holder;
    private boolean flag=true;

    public InfoDemandAdapter(Context context, List<InfoData> datas) {
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
            convertView = inflater.inflate(R.layout.fragment_info_demand, null);
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

        InfoData infoData=datas.get(position);
        holder.ivHead.setImageResource(infoData.getIvHead());
        holder.name.setText(infoData.getName());
        holder.date.setText(infoData.getDate());
        holder.distance.setText(infoData.getDistance());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getContent());
        holder.ivLocation.setImageResource(infoData.getIvLocation());
        holder.ivCollection.setImageResource(infoData.getLvCollection());
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
