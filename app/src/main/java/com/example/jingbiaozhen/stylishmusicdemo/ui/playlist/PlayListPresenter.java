package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;
/*
 * Created by jingbiaozhen on 2018/2/1.
 **/

import java.util.List;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.source.AppRepository;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class PlayListPresenter implements PlayListContract.Presenter
{

    private PlayListContract.View mView;

    private AppRepository mAppRepository;

    private CompositeSubscription mSubscription;

    public PlayListPresenter(AppRepository appRepository, PlayListContract.View view)
    {
        mView = view;
        mAppRepository = appRepository;
        mSubscription = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe()
    {
        loadPlaylist();
    }

    @Override
    public void unSubscribe()
    {
        mView = null;
        mSubscription.clear();

    }

    @Override
    public void loadPlaylist()
    {
        Subscription subscription = mAppRepository.playLists().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<PlayList>>()
                {
                    @Override
                    public void onStart()
                    {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted()
                    {
                        mView.hideLoading();

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.hideLoading();
                        mView.handleError(e);

                    }

                    @Override
                    public void onNext(List<PlayList> playLists)
                    {
                        mView.onPlayListsLoaded(playLists);
                    }
                });
        mSubscription.add(subscription);

    }

    @Override
    public void createPlaylist(PlayList playList)
    {
        Subscription subscription = mAppRepository.create(playList).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<PlayList>()
                {
                    @Override
                    public void onStart()
                    {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted()
                    {
                        mView.hideLoading();

                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList)
                    {

                        mView.onPlayListCreated(playList);
                    }
                });
        mSubscription.add(subscription);

    }

    @Override
    public void editPlaylist(PlayList playList)
    {
        Subscription subscription = mAppRepository.update(playList).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<PlayList>()
                {
                    @Override
                    public void onStart()
                    {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted()
                    {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e)
                    {

                        mView.hideLoading();
                        mView.handleError(e);
                    }

                    @Override
                    public void onNext(PlayList playList)
                    {
                        mView.onPlayListEdited(playList);

                    }
                });
        mSubscription.add(subscription);

    }

    @Override
    public void deletePlayList(PlayList playList)
    {
        Subscription subscription = mAppRepository.delete(playList).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(new Subscriber<PlayList>()
                {
                    @Override
                    public void onStart()
                    {
                        mView.showLoading();
                    }

                    @Override
                    public void onCompleted()
                    {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        mView.hideLoading();
                        mView.handleError(e);

                    }

                    @Override
                    public void onNext(PlayList playList)
                    {
                        mView.onPlayListDeleted(playList);

                    }
                });
        mSubscription.add(subscription);

    }
}
