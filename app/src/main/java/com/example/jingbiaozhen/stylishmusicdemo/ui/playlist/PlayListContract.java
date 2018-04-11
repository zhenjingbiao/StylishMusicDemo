package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;
/*
 * Created by jingbiaozhen on 2018/1/25.
 * 播放列表协议
 **/

import java.util.List;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BasePresenter;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseView;

public interface PlayListContract
{
    interface View extends BaseView<Presenter>
    {
        void showLoading();

        void hideLoading();

        void handleError(Throwable error);

        void onPlayListsLoaded(List<PlayList> playLists);

        void onPlayListCreated(PlayList playList);

        void onPlayListEdited(PlayList playList);

        void onPlayListDeleted(PlayList playList);

    }

    interface Presenter extends BasePresenter
    {
        void loadPlaylist();

        void createPlaylist(PlayList playList);

        void editPlaylist(PlayList playList);

        void deletePlayList(PlayList playList);
    }

}
