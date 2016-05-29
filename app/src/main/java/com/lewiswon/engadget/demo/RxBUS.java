package com.lewiswon.engadget.demo;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by lewis on 16/5/28.
 */
public class RxBUS {
    private final Subject<Object,Object> _bus=new SerializedSubject<>(PublishSubject.create());

    public void send(Object o){
        _bus.onNext(o);
    }
    public Observable<Object> toObserverable(){
        return _bus;
    }
    public boolean hasObservers(){
        return _bus.hasObservers();
    }
}
