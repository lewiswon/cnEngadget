package com.lewiswon.engadget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.lewiswon.engadget.Pages.HomePostList.PostListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new PostListFragment()).commit();
    }
}
