package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/2/2.
 **/

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Folder;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;

public class DBUtils
{
    public static PlayList generateFavoritePlayList(Context context)
    {
        PlayList playList = new PlayList();
        playList.setName(context.getString(R.string.play_list_favorite));
        playList.setFavorite(true);
        return playList;
    }

    public static List<Folder> generateDefultFolders()
    {
        List<Folder> folders = new ArrayList<>(3);
        File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File musicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
        folders.add(FileUtils.folderFromDir(downloadDir));
        folders.add(FileUtils.folderFromDir(musicDir));
        return folders;

    }

}
