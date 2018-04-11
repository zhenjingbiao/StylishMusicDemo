package com.example.jingbiaozhen.stylishmusicdemo.data.source;
/*
 * Created by jingbiaozhen on 2018/2/2.
 **/

import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Folder;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;
import com.example.jingbiaozhen.stylishmusicdemo.utils.DBUtils;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.model.ConflictAlgorithm;

import rx.Observable;
import rx.Subscriber;

public class AppLocalDataSource implements AppContract
{

    private static final String TAG = "AppLocalDataSource";

    private Context mContext;

    private LiteOrm mLiteOrm;

    public AppLocalDataSource(Context context, LiteOrm liteOrm)
    {
        mContext = context;
        mLiteOrm = liteOrm;
    }

    @Override
    public Observable<List<PlayList>> playLists()
    {
        return Observable.create(new Observable.OnSubscribe<List<PlayList>>()
        {
            @Override
            public void call(Subscriber<? super List<PlayList>> subscriber)
            {
                List<PlayList> playLists = mLiteOrm.query(PlayList.class);
                if (playLists.isEmpty())
                {
                    PlayList playList = DBUtils.generateFavoritePlayList(mContext);
                    long result = mLiteOrm.save(playList);
                    Log.d(TAG, "Create a default playlist with" + (result == 1 ? "success" : "failure"));
                    playLists.add(playList);
                }
                subscriber.onNext(playLists);
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public List<PlayList> cachedPlayLists()
    {
        return null;
    }

    @Override
    public Observable<PlayList> create(final PlayList playList)
    {
        return Observable.create(new Observable.OnSubscribe<PlayList>()
        {
            @Override
            public void call(Subscriber<? super PlayList> subscriber)
            {
                Date date = new Date();
                playList.setCreateAt(date);
                playList.setUpdateAt(date);
                long result = mLiteOrm.save(playList);
                if (result > 0)
                {
                    subscriber.onNext(playList);
                }
                else
                {
                    subscriber.onError(new Exception("Create playlist failed"));
                }
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public Observable<PlayList> update(final PlayList playList)
    {
        return Observable.create(new Observable.OnSubscribe<PlayList>()
        {
            @Override
            public void call(Subscriber<? super PlayList> subscriber)
            {
                playList.setUpdateAt(new Date());
                long result = mLiteOrm.save(playList);
                if (result > 0)
                {
                    subscriber.onNext(playList);
                }
                else
                {
                    subscriber.onError(new Exception("Create playlist failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<PlayList> delete(final PlayList playList)
    {
        return Observable.create(new Observable.OnSubscribe<PlayList>()
        {
            @Override
            public void call(Subscriber<? super PlayList> subscriber)
            {
                long result = mLiteOrm.delete(playList);
                if (result > 0)
                {
                    subscriber.onNext(playList);
                }
                else
                {
                    subscriber.onError(new Exception("Delete playlist failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Folder>> folders()
    {

        return Observable.create(new Observable.OnSubscribe<List<Folder>>()
        {
            @Override
            public void call(Subscriber<? super List<Folder>> subscriber)
            {
                if (PreferenceManager.isFirstQueryFolders(mContext))
                {
                    List<Folder> folders = DBUtils.generateDefultFolders();
                    long result = mLiteOrm.save(folders);
                    Log.d(TAG, "Create default folder effected " + result + "row");
                    PreferenceManager.reportFirstQueryFolders(mContext);
                }

                List<Folder> folders = mLiteOrm.query(
                        QueryBuilder.create(Folder.class).appendOrderAscBy(Folder.COLUMN_NAME));
                subscriber.onNext(folders);
                subscriber.onCompleted();
            }

        });
    }

    @Override
    public Observable<Folder> create(final Folder folder)
    {
        return Observable.create(new Observable.OnSubscribe<Folder>()
        {
            @Override
            public void call(Subscriber<? super Folder> subscriber)
            {
                folder.setCreateAt(new Date());
                long result = mLiteOrm.save(folder);
                if (result > 0)
                {
                    subscriber.onNext(folder);
                }
                else
                {
                    subscriber.onError(new Exception("Create folder failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Folder>> create(final List<Folder> folders)
    {
        return Observable.create(new Observable.OnSubscribe<List<Folder>>()
        {
            @Override
            public void call(Subscriber<? super List<Folder>> subscriber)
            {
                Date now = new Date();
                for (Folder folder : folders)
                {
                    folder.setCreateAt(now);
                }
                long result = mLiteOrm.save(folders);
                if (result > 0)
                {
                    List<Folder> allNewFolders = mLiteOrm.query(
                            QueryBuilder.create(Folder.class).appendOrderAscBy(Folder.COLUMN_NAME));
                    subscriber.onNext(allNewFolders);
                }
                else
                {
                    subscriber.onError(new Exception("Create folders failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Folder> update(final Folder folder)
    {
        return Observable.create(new Observable.OnSubscribe<Folder>()
        {
            @Override
            public void call(Subscriber<? super Folder> subscriber)
            {
                mLiteOrm.delete(folder);
                long result = mLiteOrm.save(folder);
                if (result > 0)
                {
                    subscriber.onNext(folder);
                }
                else
                {
                    subscriber.onError(new Exception("Update folder failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Folder> delete(final Folder folder)
    {
        return Observable.create(new Observable.OnSubscribe<Folder>()
        {
            @Override
            public void call(Subscriber<? super Folder> subscriber)
            {
                long result = mLiteOrm.delete(folder);
                if (result > 0)
                {
                    subscriber.onNext(folder);
                }
                else
                {
                    subscriber.onError(new Exception("Delete folder failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<List<Song>> insert(final List<Song> songs)
    {
        return Observable.create(new Observable.OnSubscribe<List<Song>>()
        {
            @Override
            public void call(Subscriber<? super List<Song>> subscriber)
            {
                for (Song song : songs)
                {
                    mLiteOrm.insert(song, ConflictAlgorithm.Abort);
                }
                List<Song> allSongs = mLiteOrm.query(Song.class);
                File file;
                for (Iterator<Song> iterator = allSongs.iterator(); iterator.hasNext();)
                {
                    Song song = iterator.next();
                    file = new File(song.getPath());
                    boolean exists = file.exists();
                    if (!exists)
                    {
                        iterator.remove();
                        mLiteOrm.delete(song);
                    }
                }
                subscriber.onNext(allSongs);
                subscriber.onCompleted();

            }
        });
    }

    @Override
    public Observable<Song> update(final Song song)
    {
        return Observable.create(new Observable.OnSubscribe<Song>()
        {
            @Override
            public void call(Subscriber<? super Song> subscriber)
            {
                long result = mLiteOrm.update(song);
                if (result > 0)
                {
                    subscriber.onNext(song);
                }
                else
                {
                    subscriber.onError(new Exception("Update song failed"));
                }
                subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Song> setSongAsFavorite(final Song song, final boolean isFavorite)
    {
        return Observable.create(new Observable.OnSubscribe<Song>()
        {
            @Override
            public void call(Subscriber<? super Song> subscriber)
            {
                List<PlayList> playLists = mLiteOrm.query(QueryBuilder.create(PlayList.class).whereEquals(
                        PlayList.COLUMN_FAVORITE, String.valueOf(true)));
                if (playLists.isEmpty())
                {
                    PlayList defaultFavorite = DBUtils.generateFavoritePlayList(mContext);
                    playLists.add(defaultFavorite);
                }
                PlayList favorite = playLists.get(0);
                song.setFavorite(isFavorite);
                favorite.setUpdateAt(new Date());
                if (isFavorite)
                {
                    favorite.addSong(song);
                }
                else
                {
                    favorite.removeSong(song);
                }
                mLiteOrm.insert(song, ConflictAlgorithm.Replace);
                long result = mLiteOrm.insert(favorite, ConflictAlgorithm.Replace);
                if (result > 0)
                {
                    subscriber.onNext(song);
                }
                else
                {
                    if (isFavorite)
                    {
                        subscriber.onError(new Exception("Set song as favorite failed"));
                    }
                    else
                    {
                        subscriber.onError(new Exception("Set song as unFavorite failed"));
                    }
                }
                subscriber.onCompleted();

            }
        });
    }
}
