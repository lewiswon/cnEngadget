package com.lewiswon.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by lewis on 16/6/30.
 */
public class StateLayout extends FrameLayout {
    private View mLoadingView;
    private LinearLayout mErrorView;
    private TextView mMessageView;
    private Button mActionBtn;
    private ProgressBar mProgressView;
    private CallBack  mCallBack;
    private int mLoadingAlign;
    public StateLayout(Context context) {
        super(context);

    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obstainStyledAttrs(attrs);
    }

    private void obstainStyledAttrs(AttributeSet attrs){
        TypedArray typedArray=getContext().obtainStyledAttributes(attrs,R.styleable.StateLayout);
        mLoadingAlign=typedArray.getInt(R.styleable.StateLayout_loading_align,0);
        typedArray.recycle();
    }


    public void loadComplete() {
      attachLoadingView(mLoadingView,false);
    }
    public void setMessage(String message){
        setViewVisiable(mProgressView,false);
        setViewVisiable(mErrorView,true);
        setMessage(mMessageView,message);
    }
    public void setCallBack(CallBack callBack){
        this.mCallBack=callBack;
    }
    public void startLoading(){
        attachLoadingView(mLoadingView,true);
        setViewVisiable(mProgressView,true);
        setViewVisiable(mErrorView,false);
    }
    private void setMessage(TextView view,String message){
        if (view==null)return;
        if (message==null||message.isEmpty())return;
        view.setText(message);
    }
    private void attachLoadingView(View view,boolean attach){
        if (view==null)return;
        if (attach){
            if (view.getParent()==null){
                this.addView(view);
            }
        }
        if (!attach){
            if (view.getParent()!=null){
                this.removeView(view);
            }
        }
    }
    private void setViewVisiable(View view,boolean visiable){
        if(view==null)return;
        if (visiable)view.setVisibility(View.VISIBLE);
        if (!visiable)view.setVisibility(View.INVISIBLE);
    }

    private void initLoading(){
        int loadingViewId=R.layout.layout_state_center;
        if (mLoadingAlign==1)loadingViewId=R.layout.layout_state_top;
        if (mLoadingAlign==2)loadingViewId=R.layout.layout_state_bottom;

        mLoadingView = LayoutInflater.from(getContext()).inflate(loadingViewId, this, false);
        mErrorView= (LinearLayout) mLoadingView.findViewById(R.id.error);
        mMessageView= (TextView) mLoadingView.findViewById(R.id.message);
        mActionBtn= (Button) mLoadingView.findViewById(R.id.action);
        mProgressView= (ProgressBar) mLoadingView.findViewById(R.id.progress);
        mActionBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallBack!=null){
                    startLoading();
                    mCallBack.retry();
                }
            }
        });
        this.addView(mLoadingView);
        startLoading();
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode())
        initLoading();

    }
    public interface CallBack{
        void retry();
    }
}
