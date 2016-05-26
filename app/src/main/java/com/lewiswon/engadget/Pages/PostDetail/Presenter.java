package com.lewiswon.engadget.Pages.PostDetail;

import android.util.Log;

import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.NetWorkUtils;
import com.lewiswon.engadget.data.PostDetail;
import com.lewiswon.engadget.data.Source.PostDetailDataSource;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lordway on 16/4/26.
 */
public class Presenter implements ViewContract.Action{
    private ViewContract.View  mView;
    public Presenter(ViewContract.View view){
        this.mView=view;
    }
    @Override
    public void getDetail(final String url) {
        if (!NetWorkUtils.isAvainable(mView.context())){
            mView.showMessage(mView.context().getString(R.string.network_not_connected));
            return;
        }
        Observable<PostDetail>  observable=Observable.create(new Observable.OnSubscribe<PostDetail>() {
            @Override
            public void call(final Subscriber<? super PostDetail> subscriber) {
                new PostDetailDataSource().getDetail(url, new PostDetailDataSource.Listener() {
                    @Override
                    public void onSuccess(PostDetail postDetail) {
                        subscriber.onNext(postDetail);
                    }

                    @Override
                    public void onError(String error) {
                        subscriber.onError(new Throwable(error));
                    }
                });
            }
        });
        Subscriber<PostDetail>  subscriber=new Subscriber<PostDetail>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("error",e.getMessage());
            }

            @Override
            public void onNext(PostDetail postDetail) {
                    mView.loadDetail(postDetail);
            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }


}
