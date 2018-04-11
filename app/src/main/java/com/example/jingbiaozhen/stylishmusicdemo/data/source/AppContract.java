package com.example.jingbiaozhen.stylishmusicdemo.data.source;
/*
 * Created by jingbiaozhen on 2018/2/2.
 *
 **/

import java.util.List;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Folder;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

import rx.Observable;

/*package*/ interface AppContract {

    /*playlist*/

    Observable<List<PlayList>> playLists();

    List<PlayList> cachedPlayLists();

    Observable<PlayList>create(PlayList playList);

    Observable<PlayList>update(PlayList playList);

    Observable<PlayList>delete(PlayList playList);


    //Folder

    Observable<List<Folder>>folders();

    Observable<Folder>create(Folder folder);

    Observable<List<Folder>>create(List<Folder> folders);

    Observable<Folder>update(Folder folder);

    Observable<Folder>delete(Folder folder);

    //Song

    Observable<List<Song>>insert(List<Song>songs);

    Observable<Song>update(Song song);

    Observable<Song>setSongAsFavorite(Song song,boolean isFavorite);

}
