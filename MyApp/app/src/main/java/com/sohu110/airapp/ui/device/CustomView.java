package com.sohu110.airapp.ui.device;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.sohu110.airapp.R;

/**
 * Created by Aaron on 2016/5/27.
 */
public class CustomView extends View {

    private float mBorderWidth;
    private int mBorderColor;

    private Paint mPaint;

    private RectF mBounds;
    private float width;
    private float height;
    float radius;
    float smallLength;
    float largeLength;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(
                        attrs,
                        R.styleable.CustomView
                        , 0, 0);

        try{
            mBorderColor = typedArray.getColor(R.styleable.CustomView_border_color,0xff000000);
            mBorderWidth = typedArray.getDimension(R.styleable.CustomView_border_width,2);
        }finally {
            typedArray.recycle();
        }

        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBounds = new RectF(getLeft(),getTop(),getRight(),getBottom());

        width = mBounds.right - mBounds.left;
        height = mBounds.bottom - mBounds.top;

        if(width<height){
            radius = width/4;
        }else{
            radius = height/4;
        }

        smallLength =10;
        largeLength=20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff000000);
        mPaint.setColor(0x66555555);
        canvas.drawRoundRect(new RectF(mBounds.centerX()-(float)0.9*width/2, mBounds.centerY() - (float)0.9*height/2, mBounds.centerX() + (float)0.9*width/2, mBounds.centerY() + (float)0.9*height/2), 30, 30, mPaint);
        mPaint.setColor(mBorderColor);
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(),radius,mPaint);
        float start_x,start_y;
        float end_x,end_y;
        for(int i=0;i<60;++i){
            start_x= radius *(float)Math.cos(Math.PI/180 * i * 6);
            start_y= radius *(float)Math.sin(Math.PI/180 * i * 6);
            if(i%5==0){
                end_x = start_x+largeLength*(float)Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y+largeLength*(float)Math.sin(Math.PI/180 * i * 6);
            }else{
                end_x = start_x+smallLength*(float)Math.cos(Math.PI/180 * i * 6);
                end_y = start_y+smallLength*(float)Math.sin(Math.PI/180 * i * 6);
            }
            start_x+=mBounds.centerX();
            end_x+=mBounds.centerX();
            start_y+=mBounds.centerY();
            end_y+=mBounds.centerY();
            canvas.drawLine(start_x, start_y, end_x, end_y, mPaint);
        }
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(),20,mPaint);
        canvas.rotate(60,mBounds.centerX(),mBounds.centerY());
        canvas.drawLine(mBounds.centerX(),mBounds.centerY(),mBounds.centerX(),mBounds.centerY()-radius,mPaint);
    }
}
