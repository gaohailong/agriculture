package com.sxau.agriculture.titlebar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by gaohailong on 2016/4/8.
 */
public class ImageViewPictuer extends View {
    public ImageViewPictuer(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p=new Paint();
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.default_user_portrait);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2, bitmap.getWidth() / 2, p);
   /*     Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap1);
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2, bitmap.getWidth() / 2, paint);
        imageAware.setImageBitmap(bitmap1);
*/
     /*   Paint p=new Paint();
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.default_user_portrait);
        //canvas.drawBitmap(bitmap,0,0,p);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getWidth() / 2, bitmap.getWidth() / 2, p);*/
    }
}
