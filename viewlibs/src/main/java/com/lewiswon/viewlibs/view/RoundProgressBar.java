package com.lewiswon.viewlibs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.lewiswon.viewlibs.R;

/**
 * Created by lewis on 16/5/26.
 */
public class RoundProgressBar extends HorizontalStyleProgressBar {
    private int mRadius=dp2px(30);
    private int mMaxPaintWidth;
    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mReachHeight= (int) (mUnreachHeight*2.5f);
        TypedArray typedArray=context.obtainStyledAttributes(R.styleable.RoundProgressBar);
        mRadius= (int) typedArray.getDimension(R.styleable.RoundProgressBar_radius,mRadius);
        typedArray.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mMaxPaintWidth=Math.max(mReachHeight,mUnreachHeight);
        int expect=mRadius*2+mMaxPaintWidth+getPaddingRight()+getPaddingLeft();
        int width=resolveSize(expect,widthMeasureSpec);
        int height=resolveSize(expect,heightMeasureSpec);
        int realWidth=Math.min(width,height);
        mRadius=(realWidth-getPaddingLeft()-getPaddingRight()-mMaxPaintWidth)/2;
        setMeasuredDimension(realWidth,realWidth);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        String text=getProgress()+"%";
        float textWidth=mPaint.measureText(text);
        float textHeight=(mPaint.descent()+mPaint.ascent())/2;

        canvas.save();

        canvas.translate(getPaddingLeft()+mMaxPaintWidth/2,getPaddingTop()+mMaxPaintWidth/2);
        mPaint.setStyle(Paint.Style.STROKE);

        //draw unreach bar
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachHeight);
        canvas.drawCircle(mRadius,mRadius,mRadius,mPaint);

          //draw reach bar
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachHeight);
        float angle=getProgress()*1.0f/getMax()*360;
        canvas.drawArc(new RectF(0,0,mRadius*2,mRadius*2),0,angle,false,mPaint);

        //draw Text
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawText(text,mRadius-textWidth/2,mRadius-textHeight,mPaint);
        canvas.restore();
    }
}
