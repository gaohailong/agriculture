package com.sxau.agriculture.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.widgets.RoundImageView;

/**
 * 顶部标题栏的Util
 *
 * @author 高海龙
 */
public class TopBarUtil extends RelativeLayout {
    //文本
    private TextView textView;
    //左边的图片
    private RoundImageView leftRoundImage;//自定义圆角图片
    private ImageView leftImageView;//普通图片
    //右边的图片
    private ImageView rightImageView;
    private int rightImageWidth;
    private int rightImageHeight;
    //标题栏显示
    private float titleTextSize;
    private int titleTextColor;
    private String titleContent;
    //布局
    private LayoutParams leftParams, rightParams, titleParams;
    //监听
    private TopbarClickListner listener;

    public interface TopbarClickListner {
        //左边圆形的图片
        void onClickLeftRoundImage();

        //左边普通图片
        void onClickLeftImage();

        //右边的图片
        void onClickRightImage();
    }

    /**
     * 设置监听方法
     *
     * @param listener
     */
    public void setOnTopbarClickListener(TopbarClickListner listener) {
        this.listener = listener;
    }

    public TopBarUtil(final Context context, AttributeSet attrs) {
        super(context, attrs);
        //style中定义属性的获取
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Topbar);
        //中间的标题
        titleContent = ta.getString(R.styleable.Topbar_titleContent);
        titleTextSize = ta.getDimension(R.styleable.Topbar_titleTextSize, 0);
        titleTextColor = ta.getColor(R.styleable.Topbar_titleTextColorDefine, 0);

        //右边的图片
        rightImageWidth = (int) ta.getDimension(R.styleable.Topbar_rightImageWidth, 0);
        rightImageHeight = (int) ta.getDimension(R.styleable.Topbar_rightImageHeight, 0);
        ta.recycle();

        //左边的图片
        leftRoundImage = new RoundImageView(context, attrs);
        leftImageView = new ImageView(context, attrs);
        //中间的文本
        textView = new TextView(context);
        //右边的图片
        rightImageView = new ImageView(context);


        setBackgroundColor(getResources().getColor(R.color.mainColor));
        //设置文本
        textView.setTextColor(titleTextColor);
        textView.setText(titleContent);
        textView.setTextSize(titleTextSize);
        textView.setGravity(Gravity.CENTER);

        //设置右边的图片
        rightImageView.setScaleType(ImageView.ScaleType.CENTER);
        rightImageView.setMaxWidth(rightImageWidth);
        rightImageView.setMaxHeight(rightImageHeight);
        //左边圆形图片
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE); //true是一个常量
        addView(leftRoundImage, leftParams);
        //左边正常图片
        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(leftImageView, leftParams);
        //中间的标题
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(textView, titleParams);
        //右边的图片
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(rightImageView, rightParams);

        leftRoundImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLeftRoundImage();
            }
        });

        leftImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickLeftImage();
            }
        });

        rightImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickRightImage();
            }
        });

        //默认全部设置为不可见
        leftImageView.setVisibility(View.GONE);
        leftRoundImage.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        rightImageView.setVisibility(View.GONE);
        //TODO 将图片换做网络图片
        Bitmap leftRoundBitmapImage= BitmapUtil.decodedBitmapFromResource(getResources(),R.mipmap.img_default_user_portrait_150px,45,45);
        Picasso.with(context).load(R.mipmap.img_default_user_portrait_150px)
                .placeholder(R.mipmap.img_default_user_portrait_150px).resize(50, 50).centerCrop().into(leftRoundImage);
    }

    //设置左边圆角图片是否可见
    public void setLeftRoundImageIsVisible(boolean flag) {
        if (flag) {
            leftRoundImage.setVisibility(View.VISIBLE);
        } else {
            leftRoundImage.setVisibility(View.GONE);
        }
    }

    //设置左边普通图片是否可见
    public void setLeftImageIsVisible(boolean flag) {
        if (flag) {
            leftImageView.setVisibility(View.VISIBLE);
        } else {
            leftImageView.setVisibility(View.GONE);
        }
    }

    //设置标题是否可见
    public void setTitleIsVisible(boolean flag) {
        if (flag) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    //设置有右边普通图片是否可见
    public void setRightImageIsVisible(boolean flag) {
        if (flag) {
            rightImageView.setVisibility(View.VISIBLE);
        } else {
            rightImageView.setVisibility(View.GONE);
        }
    }

    //设置左边圆形图片路径位置
    public void setLeftRoundImage(int id) {
        if (id > 0) {
            leftRoundImage.setImageResource(id);
        } else {
            leftRoundImage.setVisibility(View.GONE);
        }
    }

    //设置左边圆形图片位图
/*    public void setLeftRoundBitmapImage(Bitmap bitmap) {
        if (bitmap != null) {
            leftRoundImage.setImageBitmap(bitmap);
        } else {
            leftRoundImage.setVisibility(View.GONE);
        }
    }*/

    //设置左边普通图片路径位置
    public void setLeftImage(int id) {
        if (id > 0) {
            leftImageView.setImageResource(id);
        } else {
            leftImageView.setVisibility(View.GONE);
        }
    }

    //设置文本内容
    public void setContent(String text) {
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    //设置右边图片路径位置
    public void setRightImage(int id) {
        if (id > 0) {
            rightImageView.setImageResource(id);
        } else {
            rightImageView.setVisibility(View.GONE);
        }
    }
}