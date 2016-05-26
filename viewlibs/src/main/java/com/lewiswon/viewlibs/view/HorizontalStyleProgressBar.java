package com.lewiswon.viewlibs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.lewiswon.viewlibs.R;

/**
 *
 * horizontal progress bar
 * Created by lewis on 16/5/26.
 */
public class HorizontalStyleProgressBar extends ProgressBar {
    private static final int DEFAULT_TEXT_SIZE=10;//sp
    private static final int DEFAULT_TEXT_COLOR=0xFFC00D1;
    private static final int DEFAULT_UNREACH_COLOR=0xFFD3D6DA;
    private static final int DEFAULT_UNREACH_HEIGHT=2;//dp
    private static final int DEFAULT_REACH_COLOR=DEFAULT_TEXT_COLOR;
    private static final int DEFAULT_REACH_HEIGHT=2;//dp
    private static final int DEFAULT_TEXT_OFFSET=10;//dp

    protected int mTextSize=sp2px(DEFAULT_TEXT_SIZE);
    protected int mTextColor=DEFAULT_TEXT_COLOR;
    protected int mUnreachColor=DEFAULT_UNREACH_COLOR;
    protected int mUnreachHeight=dp2px(DEFAULT_UNREACH_HEIGHT);
    protected int mReachColor=DEFAULT_REACH_COLOR;
    protected int mReachHeight=dp2px(DEFAULT_REACH_HEIGHT);
    protected int mTextOffset=dp2px(DEFAULT_TEXT_OFFSET);

    protected Paint mPaint=new Paint();

    private  int mRealWidth;

    public HorizontalStyleProgressBar(Context context) {
        super(context);
    }

    public HorizontalStyleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public HorizontalStyleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.obtainStyleAttrs(attrs);
    }

    private void obtainStyleAttrs(AttributeSet attrs){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalStyleProgressBar);
        mTextSize= (int) typedArray.getDimension(R.styleable.HorizontalStyleProgressBar_progress_text_size,mTextSize);
        mTextColor=typedArray.getColor(R.styleable.HorizontalStyleProgressBar_progress_text_color,mTextColor);
        mUnreachColor= (int) typedArray.getColor(R.styleable.HorizontalStyleProgressBar_progress_unreach_color,mUnreachColor);
        mUnreachHeight= (int) typedArray.getDimension(R.styleable.HorizontalStyleProgressBar_progress_unreach_height,mUnreachHeight);
        mReachColor=typedArray.getColor(R.styleable.HorizontalStyleProgressBar_progress_reach_color,mReachColor);
        mReachHeight= (int) typedArray.getDimension(R.styleable.HorizontalStyleProgressBar_progress_reach_height,mReachHeight);
        mTextOffset= (int) typedArray.getDimension(R.styleable.HorizontalStyleProgressBar_progress_text_offset,mTextOffset);
        typedArray.recycle();
        mPaint.setTextSize(mTextSize);
    }


    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthVal=MeasureSpec.getSize(widthMeasureSpec);
        int height=measureHeight(heightMeasureSpec);
        setMeasuredDimension(widthVal,height);
        mRealWidth=getMeasuredWidth()-getPaddingLeft()-getPaddingRight();
    }
    private int measureHeight(int heightMeasureSpec){
        int result=0;
        int mode=MeasureSpec.getMode(heightMeasureSpec);
        int size=MeasureSpec.getSize(heightMeasureSpec);
        if (mode==MeasureSpec.EXACTLY){
            result=size;
        }else{
            int textHeight= (int) (mPaint.descent()-mPaint.ascent());
            result=getPaddingTop()+getPaddingBottom()+Math.max(Math.max(mReachHeight,mUnreachHeight),Math.abs(textHeight));
            if (mode==MeasureSpec.AT_MOST){
                result=Math.min(result,size);
            }
        }

        return result;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft(),getHeight()/2);

//        mPaint.setAntiAlias(true);

        boolean noNeedUnReach=false;

        String text=getProgress()+"%";

        int textWitdth= (int) mPaint.measureText(text);

        float radio=getProgress()*1.0f/getMax();

        float progressX=radio*mRealWidth;

        if (progressX+textWitdth>mRealWidth){

            progressX=mReachColor-textWitdth;

            noNeedUnReach=true;
        }

        float endX=progressX-mTextOffset/2;
        //draw reach height
        if (endX>0){
            mPaint.setColor(mReachColor);
            mPaint.setStrokeWidth(mReachHeight);
            canvas.drawLine(0,0,endX,0,mPaint);
        }

        //draw text
        mPaint.setColor(mTextColor);
        int y= (int) (-(mPaint.descent()+mPaint.ascent())/2);
        canvas.drawText(text,progressX,y,mPaint);

        //draw unreach bar
        if (!noNeedUnReach){
            float start=progressX+mTextOffset/2+textWitdth;
            mPaint.setColor(mUnreachColor);
            mPaint.setStrokeWidth(mUnreachHeight);
            canvas.drawLine(start,0,mRealWidth,0,mPaint);
        }

        canvas.restore();
    }

    protected int dp2px(int dpVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpVal,getResources().getDisplayMetrics());
    }
    protected int sp2px(int spVal){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spVal,getResources().getDisplayMetrics());
    }
}
