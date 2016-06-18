package com.sxau.agriculture.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jaeger.ninegridimageview.GridImageView;

import java.util.List;

/**
 * 九宫格的adapter
 *
 * @author Yawen_Li on 2016/6/18.
 *
 */
public abstract class NineGridImageViewAdapter<String> {

    protected abstract void onDisplayImage(Context context, ImageView imageView, String t);

    protected void onItemImageClick(Context context, int index, List<String> list) {

    }

    protected ImageView generateImageView(Context context) {
        GridImageView imageView = new GridImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}