package com.sxau.agriculture.view.activity;

/**
 * 展示图片的Activity
 * @author  Yawen_Li on 2016/6/27.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.sxau.agriculture.agriculture.R;

public class DisplayPicActivity extends BaseActivity implements ViewSwitcher.ViewFactory {

    //声明一个图片切换器
    private ImageSwitcher isImages;
    //记录当前显示的第几张图片，是一个数组的索引
    private int currentPosition;
    private String imagesData;
    int[] images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaypic);
        //初始化切换器
        isImages = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        //给切换器加一个factory 可以把它看成是一个容器
        isImages.setFactory((ViewSwitcher.ViewFactory) this);
        //当前显示的图片的索引就是从intent传过来的附加信息
        currentPosition = getIntent().getIntExtra("position",0);
        imagesData = getIntent().getStringExtra("");
        final AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(2000);
        isImages.setAnimation(animation);
        animation.startNow();
        //给图片切换器设置图片
        isImages.setImageResource(images[currentPosition]);
    }
    /**
     * 显示上一张图片
     * 给图片索引做一个--的动作，判断当当前值小于0时，给它提示当前为第一张。
     */
    public void displayPrevious(View view){
        currentPosition--;
        if(currentPosition<0){
            currentPosition = 0;
        }
        isImages.setImageResource(images[currentPosition]);
    }

    /**
     * 显示下一张图片
     * 给图片索引做一个++操作，判断当 当前值大于最后一张时，提示它为最后一张
     */
    public void displayNext(View view){
        currentPosition++;
        if(currentPosition>images.length-1){
            currentPosition = images.length-1;
        }
        isImages.setImageResource(images[currentPosition]);
    }

    /**完成ViewFactory接口的时候，必须要重写的方法makeView
     * 他的作用是返回来一个ImageView对象，同来显示选中的图片。
     * 我们需要给他设置一些属性，例如，设置布局等等。
     */
    @Override
    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(8,8,8,8);
        return imageView;
    }

    float downX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            downX = event.getX();
        }else {
            if (action == MotionEvent.ACTION_UP) if (event.getX() > downX + 50 && currentPosition != 0) {
                //右滑→
                isImages.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left2right_in));
                isImages.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left2right_out));
                displayPrevious(null);
            } else if (event.getX() + 50 < downX && currentPosition != images.length-1) {
                //左滑←
                isImages.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right2left_in));
                isImages.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right2left_out));
                displayNext(null);
            }
        }
        return super.onTouchEvent(event);
    }
}

