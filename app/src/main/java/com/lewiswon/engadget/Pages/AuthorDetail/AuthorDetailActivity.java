package com.lewiswon.engadget.Pages.AuthorDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewiswon.engadget.Pages.BaseActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.data.Author;

public class AuthorDetailActivity extends BaseActivity implements ViewContract.View{
    private Presenter  mPresenter;
    public static void open(Context context, String url){
        Intent  intent=new Intent(context,AuthorDetailActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
        if (!getIntent().hasExtra("url")){
            finish();
        }
        mPresenter=new Presenter(this);
        mPresenter.getAuthor(getIntent().getStringExtra("url"));
    }

    @Override
    public void loadData(Author author) {
        if (author==null){
            showMessage("获取信息失败");
            return;
        }
        ImageView  imageView= (ImageView) findViewById(R.id.imageView);
        TextView    name= (TextView) findViewById(R.id.nametextview);
        TextView  weibo=(TextView)findViewById(R.id.weibo);
        TextView  email=(TextView)findViewById(R.id.email);
        TextView  profile=(TextView)findViewById(R.id.profile);
        if (author.getName()!=null)name.setText(author.getName());
        if (author.getWeibo()!=null)weibo.setText(author.getWeibo());
        if (author.getEmail()!=null)email.setText(author.getEmail());
        if (author.getProfile()!=null)profile.setText(author.getProfile());
        if (author.getHeadpic()!=null) Glide.with(this).load(author.getHeadpic()).into(imageView);
    }

    @Override
    public void showMessage(String message) {

    }
}
