package com.example.jingbiaozhen.stylishmusicdemo.event;
/*
 * Created by jingbiaozhen on 2018/2/6.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

public class FavoriteChangeEvent
{
    public Song mSong;

    public FavoriteChangeEvent(Song song)
    {
        mSong = song;
    }
}
