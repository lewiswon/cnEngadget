package com.lewiswon.engadget.Pages.HomePostList;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lewiswon.engadget.data.Post;
import com.lewiswon.engadget.data.Source.API;
import com.lewiswon.engadget.data.Source.PostDataSource;
import com.lewiswon.engadget.data.Source.PostDetailDataSource;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lordway on 16/4/25.
 */
public class Presenter implements ViewContract.Action {
    private ViewContract.View  mView;
    private PostDataSource  postDataSource;
    private PostDataSource.Listener listener;
    public Presenter(ViewContract.View view){
    this.mView=view;
    }

    @Override
    public void getPosts(int page) {
        String suffix="/page/"+page;
        if(page==1||page==0) suffix="";
        final String url="http://cn.engadget.com"+suffix;
        if (postDataSource==null)postDataSource=new PostDataSource();
        if (listener==null)listener=new PostDataSource.Listener() {
            @Override
            public void onSucess(String result) {
                Gson  gson=new Gson();
                Log.i("sucess",result);
                API<ArrayList<Post>> results=gson.fromJson(result,new TypeToken<API<ArrayList<Post>>>(){}.getType());
                subscriber.onNext(results.getData());
            }

            @Override
            public void onError(String error) {
                subscriber.onError(new Throwable(error));
            }
        };

        Observable<ArrayList<Post>> observable=Observable.create(new Observable.OnSubscribe<ArrayList<Post>>() {
            @Override
            public void call(final Subscriber<? super ArrayList<Post>> subscriber) {
                postDataSource.getPosts(url, new PostDataSource.Listener() {
                    @Override
                    public void onSucess(String result) {
                        Gson  gson=new Gson();
                        Log.i("sucess",result);
                        API<ArrayList<Post>> results=gson.fromJson(result,new TypeToken<API<ArrayList<Post>>>(){}.getType());
                        subscriber.onNext(results.getData());
                    }

                    @Override
                    public void onError(String error) {
                        subscriber.onError(new Throwable(error));
                    }
                });
            }
        });
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    private Subscriber<ArrayList<Post>>  subscriber=new Subscriber<ArrayList<Post>>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
                mView.showMessage(e.getMessage());
        }

        @Override
        public void onNext(ArrayList<Post> s) {
                mView.loadPosts(s);
        }
    };
}
