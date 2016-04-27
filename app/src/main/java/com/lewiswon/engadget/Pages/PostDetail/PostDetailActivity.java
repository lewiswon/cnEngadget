package com.lewiswon.engadget.Pages.PostDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.lewiswon.engadget.Pages.BaseActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.data.PostDetail;

/**
 * Created by Lordway on 16/4/25.
 */
public class PostDetailActivity extends BaseActivity implements ViewContract.View{
    private WebView  webView;
    private Presenter  mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        mPresenter=new Presenter(this);
        if (getIntent().hasExtra("url")){
            mPresenter.getDetail(getIntent().getStringExtra("url"));
        }
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void loadDetail(PostDetail postDetail) {
        if (webView==null){
            webView= (WebView) findViewById(R.id.webview);
        }
        webView.loadDataWithBaseURL(null,postDetail.getContent(),"text/html","utf8",null);
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
