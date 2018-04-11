package com.example.jingbiaozhen.stylishmusicdemo.ui.music;
/*
 * Created by jingbiaozhen on 2018/2/11.
 * 音乐播放界面
 **/

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;
import com.example.jingbiaozhen.stylishmusicdemo.player.IPlayback;
import com.example.jingbiaozhen.stylishmusicdemo.player.PlayMode;
import com.example.jingbiaozhen.stylishmusicdemo.player.PlaybackService;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseFragment;
import com.example.jingbiaozhen.stylishmusicdemo.ui.widget.ShadowImageView;
import com.example.jingbiaozhen.stylishmusicdemo.utils.AlbumUtils;
import com.example.jingbiaozhen.stylishmusicdemo.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicPlayerFragment extends BaseFragment implements MusicPlayerContract.View, IPlayback.Callback
{
    private static final long PROGRESS_UPDATE_INTERVAL = 1000;

    @BindView(R.id.image_view_album)
    ShadowImageView imageViewAlbum;

    @BindView(R.id.text_view_name)
    TextView textViewName;

    @BindView(R.id.text_view_artist)
    TextView textViewArtist;

    @BindView(R.id.text_view_progress)
    TextView textViewProgress;

    @BindView(R.id.text_view_duration)
    TextView textViewDuration;

    @BindView(R.id.seek_bar)
    SeekBar seekBarProgress;

    @BindView(R.id.button_play_mode_toggle)
    ImageView buttonPlayModeToggle;

    @BindView(R.id.button_play_toggle)
    ImageView buttonPlayToggle;

    @BindView(R.id.button_favorite_toggle)
    ImageView buttonFavoriteToggle;

    private IPlayback mPlayer;

    private Handler mHandler = new Handler();

    private MusicPlayerContract.Presenter mPresenter;

    private Runnable mProgressCallback = new Runnable()
    {
        @Override
        public void run()
        {
            if (!isDetached())
            {
                if (mPlayer.isPlaying())
                {
                    int progress = (int) (seekBarProgress.getMax()
                            * ((float) mPlayer.getProgress() / getCurrentSongDuration()));
                    updateProgressTextWithProgress(progress);
                    if (progress >= 0 && progress <= seekBarProgress.getMax())
                    {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N)
                        {
                            seekBarProgress.setProgress(progress, true);
                        }
                        else
                        {
                            seekBarProgress.setProgress(progress);
                        }
                        mHandler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
                    }

                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //fromUser标示是用户触发了这个行为
                if (fromUser)
                {
                    updateProgressTextWithProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                mHandler.removeCallbacks(mProgressCallback);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                seekTo(getDuration(seekBar.getProgress()));
                if (mPlayer.isPlaying())
                {
                    mHandler.removeCallbacks(mProgressCallback);
                    mHandler.post(mProgressCallback);
                }

            }
        });
    }

    private void seekTo(int duration)
    {
        mPlayer.seekTo(duration);
    }

    private void updateProgressTextWithProgress(int progress)
    {
        int time = getDuration(progress);
        textViewDuration.setText(TimeUtils.formDuration(time));

    }

    private int getDuration(int progress)
    {

        return (int) (getCurrentSongDuration() * ((float) (progress / seekBarProgress.getMax())));
    }

    private int getCurrentSongDuration()
    {
        Song song = mPlayer.getPlayingSong();
        int duration = 0;
        if (song != null)
        {
            duration = song.getDuration();
        }
        return duration;
    }

    @Override
    public void setPresenter(MusicPlayerContract.Presenter presenter)
    {
        mPresenter = presenter;

    }

    @Override
    public void handleError(Throwable throwable)
    {
        Toast.makeText(getActivity(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPlaybackServiceBound(PlaybackService service)
    {
        mPlayer = service;
        mPlayer.registerCallback(this);

    }

    @Override
    public void onPlayServiceUnbound()
    {
        mPlayer.unRegisterCallback(this);
        mPlayer = null;

    }

    @Override
    public void onSongSetAsFavorite(Song song)
    {
        buttonFavoriteToggle.setEnabled(true);
        updateFavoriteToggle(song.isFavorite());

    }

    @Override
    public void onSongUpdated(Song song)
    {
        if (song == null)
        {
            imageViewAlbum.cancelRotateAnimation();
            buttonPlayToggle.setImageResource(R.drawable.ic_play);
            seekBarProgress.setProgress(0);
            updateProgressTextWithProgress(0);
            seekTo(0);
            mHandler.removeCallbacks(mProgressCallback);
            return;
        }
        textViewName.setText(song.getDisplayName());
        textViewArtist.setText(song.getArtist());
        buttonFavoriteToggle.setImageResource(
                song.isFavorite() ? R.drawable.ic_favorite_yes : R.drawable.ic_favorite_no);

        textViewProgress.setText(TimeUtils.formDuration(song.getDuration()));
        Bitmap bitmap= AlbumUtils.parseAlbum(song);
        if (bitmap == null) {
            imageViewAlbum.setImageResource(R.drawable.default_record_album);
        }else {
            imageViewAlbum.setImageBitmap(AlbumUtils.getCroppedBitmap(bitmap));
        }
        imageViewAlbum.pauseRotateAnimation();
        mHandler.removeCallbacks(mProgressCallback);
        if(mPlayer.isPlaying()){
            imageViewAlbum.startRotateAnimation();
            mHandler.post(mProgressCallback);
            buttonPlayToggle.setImageResource(R.drawable.ic_pause);
        }


    }

    @Override
    public void updatePlayMode(PlayMode playMode)
    {
        if (playMode == null) {
            playMode=PlayMode.getDefult();
        }
        switch (playMode){
            case SHUFFLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_shuffle);
                break;
            case SINGLE:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_single);
                break;
            case LOOP:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_loop);
                break;
            case LIST:
                buttonPlayModeToggle.setImageResource(R.drawable.ic_play_mode_list);
                break;
        }

    }

    @Override
    public void updatePlayToggle(boolean play)
    {
        buttonPlayToggle.setImageResource(play?R.drawable.ic_pause:R.drawable.ic_play);

    }

    @Override
    public void updateFavoriteToggle(boolean favorite)
    {
        buttonFavoriteToggle.setImageResource(favorite?R.drawable.ic_favorite_yes:R.drawable.ic_favorite_no);

    }

    @Override
    public void onSwitchLast(Song last)
    {
        onSongUpdated(last);
    }

    @Override
    public void onSwtchNext(Song next)
    {
        onSongUpdated(next);

    }

    @Override
    public void onComplete(Song next)
    {
        onSongUpdated(next);

    }

    @Override
    public void onPlayStatusChanged(boolean isPlaying)
    {
        updatePlayToggle(isPlaying);
        if(isPlaying){
            imageViewAlbum.resumeRotateAnimation();
            mHandler.removeCallbacks(mProgressCallback);
            mHandler.post(mProgressCallback);

        }else {
            imageViewAlbum.pauseRotateAnimation();
            mHandler.removeCallbacks(mProgressCallback);
        }

    }
}
