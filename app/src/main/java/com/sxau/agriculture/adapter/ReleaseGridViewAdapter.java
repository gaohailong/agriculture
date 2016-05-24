package com.sxau.agriculture.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;

import java.util.List;

/**
 * 发布供求界面照片的Adapter
 * @author 田帅
 */
public class ReleaseGridViewAdapter extends BaseAdapter{
    private Context context;
    private List<String> datas;

    public ReleaseGridViewAdapter(Context context,List<String> datas) {
        this.context = context;
        this.datas=datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getMaxPosition(){
        return datas.size()-1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        String path=datas.get(position);
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_release_photo_item,null);
            holder=new ViewHolder();
            holder.iv_photo= (ImageView) convertView.findViewById(R.id.iv_photo);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (datas.get(position).equals("111111")){
            holder.iv_photo.setImageResource(R.drawable.img_photo_select);
        }else{
            Picasso.with(context).load(datas.get(position)).into(holder.iv_photo);

        }

        return convertView;
    }
    public class ViewHolder{
        public ImageView iv_photo;
    }
}
