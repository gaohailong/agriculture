package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;

import java.util.zip.Inflater;

/**
 * Created by Administrator on 2016/4/9.
 */
public class InfoDemandAdapter extends BaseAdapter{
    private Context context;
    private InfoData datas[];

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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
public class ViewHolder{
    ImageButton ibHead;
    TextView name;
    TextView date;
    TextView distance;
    TextView title;
    TextView content;
    ImageButton ibDingwei;
}
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.fragment_info_demand,null);
            holder=new ViewHolder();
            holder.ibHead= (ImageButton) convertView.findViewById(R.id.ib_demand_head);
            holder.name= (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date= (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance= (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title= (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content= (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ibDingwei= (ImageButton) convertView.findViewById(R.id.ib_demand_dingwei);
            convertView.setTag(holder);
        }else {
                holder= (ViewHolder) convertView.getTag();
        }

        InfoData infoData=datas[position];
        holder.ibHead.setImageResource(infoData.getIbHead());
        holder.name.setText(infoData.getName());
        holder.date.setText(infoData.getDate());
        holder.distance.setText(infoData.getDistance());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getContent());
        holder.ibDingwei.setImageResource(infoData.getIbDingwei());

        return convertView;
    }
}
