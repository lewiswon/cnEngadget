package com.lewiswon.engadget.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.lewiswon.engadget.R;

import rx.functions.Action1;

/**
 * Created by lewis on 16/5/29.
 */
public class MainActivity  extends AppCompatActivity {
    private Fragment01  fragment01;
    private Fragment02 fragment02;
    private Fragment03  fragment03;
    private RxBUS  rxBUS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        setupListener();
        fragment01=new Fragment01();
        fragment02=new Fragment02();
        fragment03=new Fragment03();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_01,fragment01).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_02,fragment02).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_03,fragment03).commit();
    }

    private void setupListener() {
        getBUS().toObserverable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                if (o instanceof Fragment01.Fragment01Tap){
                    TextView  textView= (TextView) findViewById(R.id.textview);
                    textView.setText("tap from fragment01");
                    textView.setAlpha(1f);
                    ViewCompat.animate(textView).alpha(-1f).setDuration(3*1000).start();
                }else{
                    Log.i("tap","from fragment 03");
                }
            }
        });
    }

    public RxBUS  getBUS(){
        if (rxBUS==null){
            rxBUS=new RxBUS();
        }
        return rxBUS;
    }

}
