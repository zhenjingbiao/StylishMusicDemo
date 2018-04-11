package com.example.jingbiaozhen.stylishmusicdemo.player;
/*
 * Created by jingbiaozhen on 2018/2/9.
 *
 * 音乐播放器
 **/

import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

public class Player implements IPlayback, MediaPlayer.OnCompletionListener
{
    private static final String TAG = "Player";

    private static Player mInstance;

    private PlayList mPlayList;

    private MediaPlayer mPlayer;

    private List<Callback> mCallbacks = new ArrayList<>(2);

    private boolean isPaused;

    private Player()
    {
        mPlayer = new MediaPlayer();
        mPlayList = new PlayList();
        mPlayer.setOnCompletionListener(this);
    }

    public static Player getInstance()
    {
        if (mInstance == null)
        {
            synchronized (Player.class)
            {
                if (mInstance == null)
                {
                    mInstance = new Player();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        Song next = null;
        if (mPlayList.getPlayMode() == PlayMode.LIST && mPlayList.getPlayingIndex() == mPlayList.getNumOfSongs() - 1)
        {

        }
        else if (mPlayList.getPlayMode() == PlayMode.SINGLE)
        {
            next = mPlayList.getCurrentSong();
        }
        else
        {
            if (mPlayList.hasNext(true))
            {
                next = mPlayList.next();
                play();
            }
        }
        notifyComplete(next);
    }

    private void notifyComplete(Song next)
    {
        for (Callback callback : mCallbacks)
        {
            callback.onComplete(next);
        }
    }

    @Override
    public void setPlayList(PlayList playList)
    {
        if (playList == null)
        {
            playList = new PlayList();
        }
        mPlayList = playList;
    }

    @Override
    public boolean play()
    {
        if (isPaused)
        {
            mPlayer.start();
            notifyPlayStatusChanged(true);
            return true;
        }
        if (mPlayList.prepare())
        {
            Song song = mPlayList.getCurrentSong();
            try
            {
                mPlayer.reset();
                mPlayer.setDataSource(song.getPath());
                mPlayer.prepare();
                mPlayer.start();
                notifyPlayStatusChanged(true);
            }
            catch (Exception e)
            {
                Log.e(TAG, "play: ", e);
                notifyPlayStatusChanged(false);
                return false;
            }
            return true;
        }
        return false;
    }

    private void notifyPlayStatusChanged(boolean isPlaying)
    {
        for (Callback callback : mCallbacks)
        {
            callback.onPlayStatusChanged(isPlaying);
        }
    }

    @Override
    public boolean play(PlayList playList)
    {
        if (playList == null)
        {
            return false;
        }
        isPaused = false;
        setPlayList(playList);
        return play();
    }

    @Override
    public boolean play(PlayList list, int startIndex)
    {
        if (list == null || startIndex < 0 || startIndex > list.getNumOfSongs())
        {
            return false;
        }
        isPaused = false;
        list.setPlayingIndex(startIndex);
        setPlayList(list);
        return play();
    }

    @Override
    public boolean play(Song song)
    {
        if (song == null)
        {
            return false;
        }
        isPaused = false;
        mPlayList.getSongs().clear();
        mPlayList.getSongs().add(song);
        return play();
    }

    @Override
    public boolean playNext()
    {
        isPaused = false;
        if (mPlayList.hasNext(false))
        {
            Song song = mPlayList.next();
            play();
            notifyPlayNext(song);
            return true;
        }
        return false;
    }

    private void notifyPlayNext(Song song)
    {
        for (Callback callback : mCallbacks)
        {
            callback.onSwtchNext(song);
        }
    }

    private void notifyPlayLast(Song song)
    {
        for (Callback callback : mCallbacks)
        {
            callback.onSwitchLast(song);
        }
    }

    @Override
    public boolean playLast()
    {
        isPaused = false;
        if (mPlayList.hasLast())
        {
            Song song = mPlayList.last();
            play();
            notifyPlayLast(song);
            return true;
        }
        return false;
    }

    @Override
    public boolean pause()
    {
        if (isPlaying())
        {
            mPlayer.pause();
            isPaused = true;
            notifyPlayStatusChanged(false);
            return true;

        }
        return false;
    }

    @Override
    public boolean isPlaying()
    {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress()
    {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public Song getPlayingSong()
    {
        return mPlayList.getCurrentSong();
    }

    @Override
    public boolean seekTo(int progress)
    {
        if (mPlayList.getSongs().isEmpty())
        {
            return false;
        }
        Song song = mPlayList.getCurrentSong();
        if (song != null)
        {
            if (progress >= song.getDuration())
            {
                onCompletion(mPlayer);
            }
            else
            {
                mPlayer.seekTo(progress);
            }
            return true;
        }
        return false;
    }

    @Override
    public void setPlayMode(PlayMode playMode)
    {
        mPlayList.setPlayMode(playMode);

    }

    @Override
    public void registerCallback(Callback callback)
    {
        mCallbacks.add(callback);

    }

    @Override
    public void unRegisterCallback(Callback callback)
    {
        mCallbacks.remove(callback);

    }

    @Override
    public void removeCallbacks()
    {
        mCallbacks.clear();
    }

    @Override
    public void releasePlayer()
    {
        mPlayList = null;
        mPlayer.reset();
        mPlayer.release();
        mPlayer = null;
        mInstance = null;

    }
}
