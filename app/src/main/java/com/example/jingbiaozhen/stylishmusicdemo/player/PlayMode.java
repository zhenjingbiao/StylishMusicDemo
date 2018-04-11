package com.example.jingbiaozhen.stylishmusicdemo.player;
/*
 * Created by jingbiaozhen on 2018/1/25.
 * 播放模式,单曲循环，循环，列表循环，随机播放
 **/

public enum PlayMode
{
    SINGLE, LOOP, LIST, SHUFFLE;
    public static PlayMode getDefult()
    {
        return LOOP;
    }

    public static PlayMode switchNextMode(PlayMode current)
    {
        if (current == null){
            return getDefult();
        }
        switch (current)
        {
            case LOOP:
                return LIST;
            case LIST:
                return SHUFFLE;
            case SHUFFLE:
                return SINGLE;
            case SINGLE:
                return LOOP;
        }
        return getDefult();
    }

}
