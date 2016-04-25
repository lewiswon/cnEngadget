package com.lewiswon.engadget.Pages.PostDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.lewiswon.engadget.Pages.BaseActivity;
import com.lewiswon.engadget.R;

/**
 * Created by Lordway on 16/4/25.
 */
public class PostDetailActivity extends BaseActivity {
    private WebView  webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        webView= (WebView) findViewById(R.id.webview);
        if (getIntent().hasExtra("url")){
            String url=getIntent().getStringExtra("url");
            webView.loadUrl(url);

        }
    }

    /**
     *
     * @param context
     * @param url post detail url
     */
    public static void open(Context context, String url){
        if (context==null||url==null||url.equals("")){
            return;
        }
        Intent  intent=new Intent(context,PostDetailActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
}
