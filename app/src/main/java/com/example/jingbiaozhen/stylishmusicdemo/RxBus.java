package com.example.jingbiaozhen.stylishmusicdemo;
/*
 * Created by jingbiaozhen on 2018/2/6.
 * 由RxJava支持的EventBus
 * 有一点必须注意，当一次错误发生后，新事件将不能被接收，因为subscription 已经被中断
 * 在onError
 **/

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;

public class RxBus
{
    private static final String TAG = "RxBus";

    private static volatile RxBus sInstance;

    public static RxBus getInstance()
    {
        if (sInstance == null)
        {
            synchronized (RxBus.class)
            {
                if (sInstance == null)
                {
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    private PublishSubject<Object> mEventBus = PublishSubject.create();

    public void post(Object event)
    {
        mEventBus.onNext(event);
    }

    public Observable<Object> toObservable()
    {
        return mEventBus;
    }

    public static Subscriber<Object> defaultSubscriber()
    {
        return new Subscriber<Object>()
        {
            @Override
            public void onCompleted()
            {
                Log.d(TAG, "onCompleted: Duty off");

            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(TAG, "onError: what is this,please solve this as soon as possible");

            }

            @Override
            public void onNext(Object o)
            {
                Log.d(TAG, "onNext: new event received " + o);

            }
        };

    }

}
