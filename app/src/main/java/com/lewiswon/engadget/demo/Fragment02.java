package com.lewiswon.engadget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import rx.functions.Action1;

/**
 * Created by lewis on 16/5/29.
 */
public class Fragment02 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(getActivity());

        return textView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView textView= (TextView) view;
        RxBUS rxBUS=((MainActivity)getActivity()).getBUS();
        rxBUS.toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof Fragment01.Fragment01Tap){
                    textView.setText("fragment 01 tap");
                    textView.setVisibility(View.VISIBLE);
                    textView.setAlpha(1f);
                    ViewCompat.animate(textView).alpha(-1f).setDuration(2000).start();
                }else if (o instanceof Fragment03.Fragment03Tap){
                    Snackbar.make(textView,"fragment03tap",Snackbar.LENGTH_LONG).show();
                }
            }

        });
    }
}
