package com.lewiswon.engadget.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lordway on 16/4/25.
 */
public class RecyclerTouchListener  implements RecyclerView.OnItemTouchListener {
    private Listener  listener;
    private GestureDetector gestureDetector;
    public RecyclerTouchListener(Context context, final RecyclerView recyclerView, Listener l){
        gestureDetector=new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child.isClickable())return false;
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View  child=recyclerView.findChildViewUnder(e.getX(),e.getY());
                if (child!=null&&listener!=null){
                    listener.onLongClick(child,recyclerView.getChildAdapterPosition(child));
                }
            }
        });
        this.listener=l;

    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View  child=rv.findChildViewUnder(e.getX(),e.getY());
        if (child!=null&&listener!=null&&gestureDetector.onTouchEvent(e)){

            listener.onClick(child,rv.getChildAdapterPosition(child));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface Listener{
        void onClick(View view,int postion);
        void onLongClick(View view,int position);

    }
}
