package com.sxau.agriculture.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 位图采样的util
 * @author 高海龙
 */
public class BitmapUtil {
    /**
     * 位图重新采样
     *
     * @param res       加载资源
     * @param resid     位图的id
     * @param reqWidth  要求的宽度
     * @param reqHeight 要求的高度
     * @return
     */
    public static Bitmap decodedBitmapFromResource(Resources res, int resid, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resid, options);
        options.inSampleSize = calculateInsampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;//把图片加载到内存
        return BitmapFactory.decodeResource(res, resid, options);
    }

    /**
     * 计算位图的采样比例
     *
     * @param options   采样比例大小
     * @param reqWidth  要求的宽度
     * @param reqHeight 要求的高度
     * @return
     */
    private static int calculateInsampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //获取位图原宽高
        int w = options.outWidth;
        int h = options.outHeight;
        int inSampleSize = 1;//设定比例为1
        if (w > reqWidth || h > reqHeight) {
            if (w > h) {//按小的比例来缩放
                inSampleSize = Math.round((float) h / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) w / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

}
