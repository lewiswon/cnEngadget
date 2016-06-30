package com.lewiswon.statelayout;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private StateLayout stateLayout;
    private final int LODING=100,ERROR=101;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LODING:
                    stateLayout.loadComplete();
                    break;
                case ERROR:
                    stateLayout.setMessage("连接超时");
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stateLayout= (StateLayout) findViewById(R.id.state_layout);
        stateLayout.setCallBack(new StateLayout.CallBack() {
            @Override
            public void retry() {
                handler.sendEmptyMessageDelayed(LODING,2*1000);
            }
        });
        handler.sendEmptyMessageDelayed(LODING,5*1000);

    }

    public void load(View view){
        switch (view.getId()){
            case R.id.loading:
                stateLayout.startLoading();
                handler.sendEmptyMessageDelayed(LODING,5*1000);
                break;
            case R.id.error:
                stateLayout.startLoading();
                handler.sendEmptyMessageDelayed(ERROR,10*1000);
                break;
        }

    }
}
