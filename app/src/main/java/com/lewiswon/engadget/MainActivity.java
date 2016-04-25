package com.lewiswon.engadget;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lewiswon.engadget.Pages.HomePostList.PostListFragment;

public class MainActivity extends AppCompatActivity {
    private PostListFragment  postListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postListFragment=new PostListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,postListFragment).commit();
    }
}
