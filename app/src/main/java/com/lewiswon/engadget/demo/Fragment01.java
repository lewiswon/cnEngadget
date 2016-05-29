package com.lewiswon.engadget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by lewis on 16/5/29.
 */
public class Fragment01 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Button  button=new Button(getActivity());
        button.setText("fragment01");
        return button;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((Button)view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxBUS  rxBUS=((MainActivity)getActivity()).getBUS();
                rxBUS.send(new Fragment01Tap());
            }
        });
    }
    public static class Fragment01Tap{}
}
