package com.lewiswon.viewlibs;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lewiswon.viewlibs.view.HorizontalStyleProgressBar;
import com.lewiswon.viewlibs.view.RoundProgressBar;

public class MainActivity extends AppCompatActivity {
    private final int MSG_UPDATE=10001;
    private int progress=0;
    private HorizontalStyleProgressBar progressBar;
    private RoundProgressBar  progressBarR;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress=progressBar.getProgress();
            progressBar.setProgress(++progress);
            progressBarR.setProgress(++progress);
            if (progress>=100){
               mHandler.removeMessages(MSG_UPDATE);
            }
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE,50);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar= (HorizontalStyleProgressBar) findViewById(R.id.progress);
        progressBarR= (RoundProgressBar) findViewById(R.id.progressR);
        mHandler.sendEmptyMessage(0);
    }
}
