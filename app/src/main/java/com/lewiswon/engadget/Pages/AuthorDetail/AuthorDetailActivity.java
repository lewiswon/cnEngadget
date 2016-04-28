package com.lewiswon.engadget.Pages.AuthorDetail;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewiswon.engadget.Pages.BaseActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.ScreenUtils;
import com.lewiswon.engadget.data.Author;

public class AuthorDetailActivity extends BaseActivity implements ViewContract.View {
    Presenter mPresenter;
    ProgressBar  progressBar;
    String url;
    public static void open(Context context, String url) {
        Intent intent = new Intent(context, AuthorDetailActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
        if (!getIntent().hasExtra("url")) {
            finish();
        }
        url=getIntent().getStringExtra("url");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        progressBar= (ProgressBar) findViewById(R.id.progressbar);
        getSupportActionBar().setTitle("Author's Profile");
        mPresenter = new Presenter(this);
        mPresenter.getAuthor(url);

    }

    @Override
    public void loadData(Author author) {
        if (author == null) {
            showMessage("获取信息失败");
            return;
        }
        if (progressBar != null) {

            progressBar.setVisibility(View.INVISIBLE);
        }
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        TextView name = (TextView) findViewById(R.id.nametextview);
        ImageView weibo = (ImageView) findViewById(R.id.weibo);
        TextView email = (TextView) findViewById(R.id.email);
        TextView profile = (TextView) findViewById(R.id.profile);
        String value;
        if ((value = author.getName()) != null && name != null) name.setText(value.toUpperCase());
        if (author.getWeibo() != null && weibo != null) weibo.setVisibility(View.VISIBLE);
        if ((value = author.getEmail()) != null && email != null) email.setText(value);
        if ((value = author.getProfile()) != null && profile != null) profile.setText(value);
        if ((value=author.getHeadpic()) != null && imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            params.height = (int) (ScreenUtils.getScreenWidth(this) / 1.25);
            if (Build.VERSION.SDK_INT>=21){
                setImageUpAPI21(value,imageView);
            }else{
                setImageBelowAPI21(value,imageView);
            }

        }
    }
    private void setImageBelowAPI21(String url,ImageView  imageView){
        Glide.with(this).load(url).into(imageView).onLoadFailed(null,getResources().getDrawable(R.mipmap.broken_image));
    }
    @TargetApi(21)
    private void setImageUpAPI21(String url,ImageView imageView){
        Glide.with(this).load(url).into(imageView).onLoadFailed(null,getResources().getDrawable(R.mipmap.broken_image,getTheme()));
    }
    @Override
    public void showMessage(String message) {
        progressBar.setVisibility(View.INVISIBLE);
        Snackbar  snackbar=Snackbar.make(progressBar,message,Snackbar.LENGTH_LONG).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getAuthor(url);
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        snackbar.show();
    }
}
