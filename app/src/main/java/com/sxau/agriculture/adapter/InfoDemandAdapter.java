package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.InfoData;

/**
 * Created by Administrator on 2016/4/9.
 */
public class InfoDemandAdapter extends BaseAdapter implements View.OnClickListener{
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.fragment_info_demand, null);
            holder = new ViewHolder();
            holder.ibHead = (ImageButton) convertView.findViewById(R.id.ib_demand_head);
            holder.name = (TextView) convertView.findViewById(R.id.tv_demand_name);
            holder.date = (TextView) convertView.findViewById(R.id.tv_demand_date);
            holder.distance = (TextView) convertView.findViewById(R.id.tv_demand_distance);
            holder.title = (TextView) convertView.findViewById(R.id.tv_demand_title);
            holder.content = (TextView) convertView.findViewById(R.id.tv_demand_content);
            holder.ibDingwei = (ImageButton) convertView.findViewById(R.id.ib_demand_dingwei);
            holder.ibCollection= (ImageButton) convertView.findViewById(R.id.ib_demand_collection);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InfoData infoData = datas[position];
        holder.ibHead.setImageResource(infoData.getIbHead());
        holder.name.setText(infoData.getName());
        holder.date.setText(infoData.getDate());
        holder.distance.setText(infoData.getDistance());
        holder.title.setText(infoData.getTitle());
        holder.content.setText(infoData.getContent());
        holder.ibDingwei.setImageResource(infoData.getIbDingwei());
        holder.ibCollection.setImageResource(infoData.getIbCollection());

        ImageButton ibDemandCollection= (ImageButton) convertView.findViewById(R.id.ib_demand_collection);
        ibDemandCollection.setOnClickListener(this);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_demand_collection:
                Toast.makeText(context,"111",Toast.LENGTH_SHORT).show();
holder.         ibCollection.setImageResource(R.drawable.collection);
                break;
        }
    }

    public class ViewHolder {
        ImageButton ibCollection;
        ImageButton ibHead;
        TextView name;
        TextView date;
        TextView distance;
        TextView title;
        TextView content;
        ImageButton ibDingwei;
    }
}
