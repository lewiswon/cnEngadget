package com.lewiswon.engadget.Pages.AuthorDetail;

import com.lewiswon.engadget.R;
import com.lewiswon.engadget.Utils.NetWorkUtils;
import com.lewiswon.engadget.data.Author;
import com.lewiswon.engadget.data.Source.AuthorDataSource;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Lordway on 16/4/27.
 */
public class Presenter implements ViewContract.Action{
    private ViewContract.View mView;
    private AuthorDataSource  authorDataSource;
    public Presenter(ViewContract.View view){
        this.mView=view;
    }
    @Override
    public void getAuthor(final String url){
        if (!NetWorkUtils.isAvainable(mView.context())){
            mView.showMessage(mView.context().getString(R.string.network_not_connected));
            return;
        }
        Observable<Author> observable=Observable.create(new Observable.OnSubscribe<Author>() {
            @Override
            public void call(final Subscriber<? super Author> subscriber) {
                if (authorDataSource==null){
                    authorDataSource=new AuthorDataSource();
                }
               authorDataSource.getAuthor(url, new AuthorDataSource.Listener() {
                    @Override
                    public void onSucess(Author author) {
                        subscriber.onNext(author);
                    }

                    @Override
                    public void onError(String error) {
                        subscriber.onError(new Throwable(error));
                    }
                });
            }
        });
        Subscriber<Author>  subscriber=new Subscriber<Author>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                    mView.showMessage(e.getMessage());
            }

            @Override
            public void onNext(Author author) {
                    mView.loadData(author);
            }
        };
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }
}
