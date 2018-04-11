package com.example.jingbiaozhen.stylishmusicdemo.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/*
 * Created by jingbiaozhen on 2018/1/25.
 * 添加订阅列表
 **/

public class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    private CompositeSubscription mSubscriptions;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addSubscription(subscribeEvents());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mSubscriptions != null) {
            mSubscriptions.clear();
        }

    }

    private void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }
        if(mSubscriptions==null){
            mSubscriptions=new CompositeSubscription();
        }
        mSubscriptions.add(subscription);

    }

    protected Subscription subscribeEvents() {
        return null;
    }
}
