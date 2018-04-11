package com.example.jingbiaozhen.stylishmusicdemo.event;
/*
 * Created by jingbiaozhen on 2018/2/6.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;

public class PlayListUpdatedEvent
{
    PlayList mPlayList;

    public PlayListUpdatedEvent(PlayList playList)
    {
        mPlayList = playList;
    }
}
