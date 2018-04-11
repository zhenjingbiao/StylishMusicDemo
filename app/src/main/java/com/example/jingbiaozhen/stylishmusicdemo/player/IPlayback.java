package com.example.jingbiaozhen.stylishmusicdemo.player;
/*
 * Created by jingbiaozhen on 2018/2/8.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

public interface IPlayback
{
    void setPlayList(PlayList playList);

    boolean play();

    boolean play(PlayList playList);

    boolean play(PlayList list, int startIndex);

    boolean play(Song song);

    boolean playNext();

    boolean playLast();

    boolean pause();

    boolean isPlaying();

    int getProgress();

    Song getPlayingSong();

    boolean seekTo(int progress);

    void setPlayMode(PlayMode playMode);

    void registerCallback(Callback callback);

    void unRegisterCallback(Callback callback);

    void removeCallbacks();

    void releasePlayer();

    interface Callback
    {
        void onSwitchLast(Song last);

        void onSwtchNext(Song next);

        void onComplete(Song next);

        void onPlayStatusChanged(boolean isPlaying);

    }

}
