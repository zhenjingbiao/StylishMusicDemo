package com.example.jingbiaozhen.stylishmusicdemo.event;
/*
 * Created by jingbiaozhen on 2018/2/6.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;

public class PlayListNowEvent
{
    public PlayList mPlayList;

    public int mIndex;

    public PlayListNowEvent(PlayList playList, int index)
    {
        mPlayList = playList;
        mIndex = index;
    }
}
