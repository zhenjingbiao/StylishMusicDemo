package com.example.jingbiaozhen.stylishmusicdemo.event;
/*
 * Created by jingbiaozhen on 2018/2/6.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;

public class PlayListCreatedEvent
{
    public PlayList mPlayList;

    public PlayListCreatedEvent(PlayList playList)
    {
        mPlayList = playList;
    }
}
