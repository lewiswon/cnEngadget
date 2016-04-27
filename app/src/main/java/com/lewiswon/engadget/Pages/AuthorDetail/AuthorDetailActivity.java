package com.lewiswon.engadget.Pages.AuthorDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lewiswon.engadget.Pages.BaseActivity;
import com.lewiswon.engadget.R;
import com.lewiswon.engadget.data.Author;

/**
 * Created by Lordway on 16/4/26.
 */
public class AuthorDetailActivity extends BaseActivity{

    public static void open(Context context, Author author){
        Intent  intent=new Intent(context,AuthorDetailActivity.class);
        intent.putExtra("author",author);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
    }
}
