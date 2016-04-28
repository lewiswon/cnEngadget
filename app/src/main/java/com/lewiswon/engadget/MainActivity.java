package com.lewiswon.engadget;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.lewiswon.engadget.Pages.HomePostList.PostListFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private PostListFragment  postListFragment;
    private long currentTime=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        postListFragment=new PostListFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.container,postListFragment).commit();
        Toolbar  toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout  drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle  toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.about,R.string.share);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView  navigationView=(NavigationView)findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        if(toolbar!=null)
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    if (currentTime==-1){
                        currentTime=System.currentTimeMillis();
                    }else if(System.currentTimeMillis()-currentTime<500){
                        currentTime=-1;
                        if (postListFragment!=null)postListFragment.back2Top();
                        return true;
                    }else {
                        currentTime=-1;
                    }
                }
                return false;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        String title=null;
        switch (item.getItemId()){
            case R.id.news:
                title=item.getTitle().toString();
                break;
            case R.id.review:
                title=item.getTitle().toString();
                break;
            case R.id.gallery:
                title=item.getTitle().toString();
                break;
            case R.id.video:
                title=item.getTitle().toString();
                break;


        }
        if (title!=null&&getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }
        DrawerLayout  drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout  drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }
    }
}
