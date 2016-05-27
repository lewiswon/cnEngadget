package com.lewiswon.android_mooc;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lewiswon.android_mooc.bean.User;
import com.lewiswon.android_mooc.databinding.ActivityRetrofitBinding;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 05/20/2016.
 */
public class RetrofitActivity extends BaseActivity {
    TextView contentTv;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_retrofit);
        User user = new User("Lewis", "Luo");
        ActivityRetrofitBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_retrofit);
        binding.setUser(user);
        contentTv = (TextView) findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    public void onClick(View view) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/").build();
        GithubService githubService = retrofit.create(GithubService.class);
        final retrofit2.Call<ResponseBody> repos = githubService.listRepos("lewiswon");
        progressBar.setVisibility(View.VISIBLE);
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    contentTv.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
