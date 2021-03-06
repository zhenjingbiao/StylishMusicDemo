package com.example.jingbiaozhen.stylishmusicdemo.player;
/*
 * Created by jingbiaozhen on 2018/2/8.
 **/

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.jingbiaozhen.stylishmusicdemo.MainActivity;
import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;
import com.example.jingbiaozhen.stylishmusicdemo.utils.AlbumUtils;

public class PlaybackService extends Service implements IPlayback, IPlayback.Callback
{

    private static final String ACTION_PLAY_TOGGLE = "action_play_toggle";

    private static final String ACTION_PLAY_NEXT = "action_play_next";

    private static final String ACTION_PLAY_LAST = "action_play_last";

    private static final String ACTION_STOP_SERVICE = "action_stop_service";

    private static final int NOTIFICATION_ID = 1;

    private Player mPlayer;

    private Binder mBinder = new LocalBinder();

    private RemoteViews mBigContentView, mSmallContentView;

    public class LocalBinder extends Binder
    {
        public PlaybackService getService()
        {
            return PlaybackService.this;
        }
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        mPlayer = Player.getInstance();
        mPlayer.registerCallback(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (intent != null)
        {
            String action = intent.getAction();
            if (ACTION_PLAY_TOGGLE.equals(action))
            {
                if (isPlaying())
                {
                    pause();
                }
                else
                {
                    play();
                }

            }
            else if (ACTION_PLAY_NEXT.equals(action))
            {
                playNext();
            }
            else if (ACTION_PLAY_LAST.equals(action))
            {
                playLast();
            }
            else if (ACTION_STOP_SERVICE.equals(action))
            {
                if (isPlaying())
                {
                    pause();
                }
                stopForeground(true);
                unRegisterCallback(this);
            }

        }

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public boolean stopService(Intent name)
    {
        stopForeground(true);
        unRegisterCallback(this);
        return super.stopService(name);
    }

    @Override
    public void setPlayList(PlayList playList)
    {
        mPlayer.setPlayList(playList);

    }

    @Override
    public boolean play()
    {
        return mPlayer.play();
    }

    @Override
    public boolean play(PlayList playList)
    {
        return mPlayer.play(playList);
    }

    @Override
    public boolean play(PlayList list, int startIndex)
    {
        return mPlayer.play(list, startIndex);
    }

    @Override
    public boolean play(Song song)
    {
        return mPlayer.play(song);
    }

    @Override
    public boolean playNext()
    {
        return mPlayer.playNext();
    }

    @Override
    public boolean playLast()
    {
        return mPlayer.playLast();
    }

    @Override
    public boolean pause()
    {
        return mPlayer.pause();
    }

    @Override
    public boolean isPlaying()
    {
        return mPlayer.isPlaying();
    }

    @Override
    public int getProgress()
    {
        return mPlayer.getProgress();
    }

    @Override
    public Song getPlayingSong()
    {
        return mPlayer.getPlayingSong();
    }

    @Override
    public boolean seekTo(int progress)
    {
        return mPlayer.seekTo(progress);
    }

    @Override
    public void setPlayMode(PlayMode playMode)
    {
        mPlayer.setPlayMode(playMode);

    }

    @Override
    public void registerCallback(Callback callback)
    {
        mPlayer.registerCallback(callback);

    }

    @Override
    public void unRegisterCallback(Callback callback)
    {
        mPlayer.unRegisterCallback(callback);

    }

    @Override
    public void removeCallbacks()
    {
        mPlayer.removeCallbacks();

    }

    @Override
    public void releasePlayer()
    {
        mPlayer.releasePlayer();

    }

    @Override
    public void onSwitchLast(Song last)
    {
        showNotification();
    }

    private void showNotification()
    {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this).setSmallIcon(
                R.drawable.ic_notification_app_logo).setWhen(System.currentTimeMillis()).setContentIntent(
                        contentIntent).setCustomContentView(getSmallContentView()).setCustomBigContentView(
                                getBigContentView()).setPriority(NotificationCompat.PRIORITY_MAX).setOngoing(
                                        true).build();
        startForeground(NOTIFICATION_ID, notification);

    }

    private RemoteViews getBigContentView()
    {
        if (mBigContentView == null)
        {
            mBigContentView = new RemoteViews(getPackageName(), R.layout.remote_view_music_player);
            setUpRemoteView(mBigContentView);
        }
        updateRemoteViews(mBigContentView);

        return mBigContentView;
    }

    private void updateRemoteViews(RemoteViews bigContentView)
    {
        Song currentSong = mPlayer.getPlayingSong();
        if (currentSong != null)
        {
            bigContentView.setTextViewText(R.id.text_view_name, currentSong.getDisplayName());
            bigContentView.setTextViewText(R.id.text_view_artist, currentSong.getArtist());
        }
        bigContentView.setImageViewResource(R.id.image_view_play_toggle,
                isPlaying() ? R.drawable.ic_remote_view_pause : R.drawable.ic_remote_view_play);
        Bitmap album = AlbumUtils.parseAlbum(currentSong);
        if (album == null)
        {
            bigContentView.setImageViewResource(R.id.image_view_album, R.mipmap.ic_launcher);
        }
        else
        {
            bigContentView.setImageViewBitmap(R.id.image_view_album, album);
        }

    }

    private void setUpRemoteView(RemoteViews remoteView)
    {

        remoteView.setImageViewResource(R.id.image_view_close, R.drawable.ic_remote_view_close);
        remoteView.setImageViewResource(R.id.image_view_play_last, R.drawable.ic_remote_view_play_last);
        remoteView.setImageViewResource(R.id.image_view_play_next, R.drawable.ic_remote_view_play_next);

        remoteView.setOnClickPendingIntent(R.id.button_close, getPendingIntent(ACTION_STOP_SERVICE));
        remoteView.setOnClickPendingIntent(R.id.button_play_last, getPendingIntent(ACTION_PLAY_LAST));
        remoteView.setOnClickPendingIntent(R.id.button_play_next, getPendingIntent(ACTION_STOP_SERVICE));
        remoteView.setOnClickPendingIntent(R.id.button_close, getPendingIntent(ACTION_STOP_SERVICE));

    }

    private PendingIntent getPendingIntent(String action)
    {
        return PendingIntent.getService(this, 0, new Intent(action), 0);
    }

    private RemoteViews getSmallContentView()
    {
        if (mSmallContentView == null)
        {
            mSmallContentView = new RemoteViews(getPackageName(), R.layout.remote_view_music_player_small);
            setUpRemoteView(mSmallContentView);
        }
        updateRemoteViews(mSmallContentView);
        return mSmallContentView;
    }

    @Override
    public void onSwtchNext(Song next)
    {
        showNotification();
    }

    @Override
    public void onComplete(Song next)
    {
        showNotification();
    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying)
    {
        showNotification();
    }
}
