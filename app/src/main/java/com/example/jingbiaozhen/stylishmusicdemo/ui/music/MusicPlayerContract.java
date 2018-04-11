package com.example.jingbiaozhen.stylishmusicdemo.ui.music;
/*
 * Created by jingbiaozhen on 2018/2/8.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;
import com.example.jingbiaozhen.stylishmusicdemo.player.PlayMode;
import com.example.jingbiaozhen.stylishmusicdemo.player.PlaybackService;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BasePresenter;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseView;

interface MusicPlayerContract
{
    interface View extends BaseView<Presenter>
    {
        void handleError(Throwable throwable);

        void onPlaybackServiceBound(PlaybackService service);

        void onPlayServiceUnbound();

        void onSongSetAsFavorite(Song song);

        void onSongUpdated(Song song);

        void updatePlayMode(PlayMode playMode);

        void updatePlayToggle(boolean play);

        void updateFavoriteToggle(boolean favorite);

    }

    interface Presenter extends BasePresenter
    {
        void retrieveLastPlayMode();

        void setSongAsFavorite(Song song, boolean isFavorite);

        void bindPlaybackService();

        void unbindPlaybackService();

    }
}
