package com.lewiswon.engadget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lewiswon.engadget.R;

import rx.functions.Action1;

/**
 * Created by lewis on 16/5/29.
 */
public class Fragment03 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout  linearLayout=new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView  textView=new TextView(getContext());
        textView.setId(R.id.textview);
        linearLayout.addView(textView);
        Button  button=new Button(getContext());
        button.setId(R.id.button);
        linearLayout.addView(button);
        return linearLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final TextView  textView= (TextView) view.findViewById(R.id.textview);
        Button  button= (Button) view.findViewById(R.id.button);
        final RxBUS rxBUS=((MainActivity)getActivity()).getBUS();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxBUS.send(new Fragment03Tap());
            }
        });
        rxBUS.toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                String messsage="";
                    if (o instanceof Fragment01.Fragment01Tap ){
                        messsage="tap from fragment01";
                    } else if(o instanceof Fragment03Tap){
                        messsage="tap from fragment03";
                    }
                textView.setText(messsage);
                textView.setAlpha(1f);
                ViewCompat.animate(textView).alpha(-1f).setDuration(3*1000).start();
            }
        });
    }

    public static class Fragment03Tap{}
}
