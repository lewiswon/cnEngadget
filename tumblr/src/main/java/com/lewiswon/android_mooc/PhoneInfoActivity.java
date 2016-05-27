package com.lewiswon.android_mooc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.Socket;

public class PhoneInfoActivity extends AppCompatActivity {
    private TextView textview;
    private EditText editText;
    private MessageThread messageThread;
    private MessageHandler messageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent("growing.d6df4d6548b50449://growing/oauth2/token?loginToken=b928844135bf4512b2d235ec39012392&circleType=web&source=qrcode"));
            }
        });

        textview = (TextView) findViewById(R.id.textview);
        editText = (EditText) findViewById(R.id.edittext);
        messageHandler = new MessageHandler(this);
        messageThread = new MessageThread(messageHandler);
        messageThread.start();
    }

    public void send(View v) {
        String message = editText.getText().toString().trim();
        messageThread.sendMessage(message);
    }

    static class MessageThread extends Thread {
        Socket mSocket;
        BufferedInputStream bufferedInputStream;
        BufferedWriter bufferedWriter;
        BufferedReader bufferedReader;
        MessageHandler messageHandler;

        public MessageThread(MessageHandler h) {
            this.messageHandler = h;
        }

        @Override
        public void run() {
            if (mSocket == null) {
                try {
                    mSocket = new Socket("192.168.1.83", 9898);
                    bufferedReader = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(mSocket.getOutputStream()));
                    String message;
                    if ((message = bufferedReader.readLine()) != null) {
                        messageHandler.obtainMessage(0, message).sendToTarget();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            try {
                bufferedWriter.write(message + "\n");
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static class MessageHandler extends Handler {
        WeakReference<PhoneInfoActivity> activityWR;

        public MessageHandler(PhoneInfoActivity activity) {
            activityWR = new WeakReference<PhoneInfoActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PhoneInfoActivity activity = activityWR.get();
            activity.showResult((String) msg.obj);
        }
    }

    public void showResult(String result) {
        String text = textview.getText().toString() + "\n" + result;
        textview.setText(text);
    }
}
