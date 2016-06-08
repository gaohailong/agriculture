package com.sxau.agriculture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.LogUtil;

import java.util.List;

/**
 * 图片选择器选择的adapter
 * @author 高海龙
 */
public class SelectPhotoAdapter extends RecyclerView.Adapter<SelectPhotoAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mLayoutInflater;
    private List<String> result;
    private final static String TAG = "SelectPhotoAdapter";

    public SelectPhotoAdapter(Context context, List<String> result) {
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLayoutInflater.inflate(R.layout.item_selector_image, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context)
                .load(result.get(position))
                .centerCrop()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
