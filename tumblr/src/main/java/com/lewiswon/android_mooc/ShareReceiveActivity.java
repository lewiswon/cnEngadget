package com.lewiswon.android_mooc;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 05/27/2016.
 */
public class ShareReceiveActivity extends AppCompatActivity {
    private String url;
    private TextView originalTv, videoLinkTv, statusTv;
    private ProgressBar loadingPb, progressPb;
    Observable<String> observable = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            StringBuilder builder1 = new StringBuilder("begain");

            try {
                Document document = Jsoup.connect(url).get();
                builder1.append("\n==========================\n");
//                    Element videoElement = document.select("video").first();
                Element frame = document.select("div.tumblr_video_container").first().select("iframe").first();
                String src = frame.attr("src");
                builder1.append("frame is " + frame.toString());
//                    subscriber.onNext(frame.toString());
                Document document1 = Jsoup.connect(src).get();
                String source = document1.select("video").first().select("source").first().attr("src");
                Log.i("document1", source);
                subscriber.onNext(source);
                subscriber.onCompleted();
//                    builder1.append(videoElement==null?"video is null":videoElement.toString()+"");
                builder1.append("\n==========================\n");
//                    Element source = document.select("source").first();
//                    builder1.append(source==null?"source element is null":source.toString());
                builder1.append("\n==========================\n");
//                    String videoUrl = source.attr("src");
//                    builder.append(videoUrl==null?"videoUrl element is null":videoUrl.toString());
                builder1.append("\n==========================\n");
                builder1.append(document.toString());
//                    String videoType = source.attr("type");
//                    builder.append(videoType==null?"videoType element is null":videoType.toString());
//                    subscriber.onNext(builder1.toString());
//                    subscriber.onCompleted();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    });
    Subscriber<String> subscribe = new Subscriber<String>() {
        @Override
        public void onStart() {
            super.onStart();
            loadingPb.setVisibility(View.VISIBLE);
            statusTv.setText("开始解析地址");
        }

        @Override
        public void onCompleted() {
            statusTv.setText("解析地址完成");
            loadingPb.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onError(Throwable e) {
            loadingPb.setVisibility(View.INVISIBLE);
            statusTv.setText("解析地址失败");
            e.printStackTrace();
        }

        @Override
        public void onNext(String s) {
            download(s);
            videoLinkTv.setText(s);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_receive);
        originalTv = (TextView) findViewById(R.id.original);
        videoLinkTv = (TextView) findViewById(R.id.textView);
        loadingPb = (ProgressBar) findViewById(R.id.progress);
        progressPb = (ProgressBar) findViewById(R.id.download_progress);
        statusTv = (TextView) findViewById(R.id.status);
        resolvIntent();
    }

    private void resolvIntent() {
        try {
            ShareCompat.IntentReader intentReader = ShareCompat.IntentReader.from(this);
            url = intentReader.getText().toString();
            originalTv.setText(url);
        } catch (Exception e) {

        }

    }

    public void parse(View view) {
        if (url == null) return;
        Subscription subscription = observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(subscribe);
    }

    private void download(String url) {
        String fileName = url.split("/")[url.split("/").length - 2] + ".mp4";
        statusTv.setText("开始下载");
        FileDownloader.getImpl().create(url).setPath("storage/emulated/0/tumblr/" + fileName).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.i("progress", soFarBytes + "");
                progressPb.setProgress((int) (100 * (soFarBytes * 1.0f / totalBytes)));
                statusTv.setText("正在下载" + (int) (100 * (soFarBytes * 1.0f / totalBytes)) + "%");
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.i("download complete", "complete");
                statusTv.setText("下载完成 \n" + task.getPath());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                e.printStackTrace();
                statusTv.setText("下载失败");
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        }).start();
    }
}
