package com.example.jingbiaozhen.stylishmusicdemo.ui.detail;
/*
 * Created by jingbiaozhen on 2018/2/1.
 * 点击播放列表进入的播放详情页
 **/

import android.content.Context;
import android.content.Intent;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Folder;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseActivity;

public class PlayListDetailsActivity extends BaseActivity {
    private static final String TAG = "PlayListDetailsActivity";
    public static final String EXTRA_FOLDER = "extraFolder";
    public static final String EXTRA_PLAY_LIST = "extraPlayList";

    public static Intent launchIntentForPlayList(Context context, PlayList playList){
        Intent intent=new Intent(context,PlayListDetailsActivity.class);
        intent.putExtra(EXTRA_PLAY_LIST,playList);
        return intent;

    }

    public static Intent launchIntentForFolder(Context context, Folder folder){
        Intent intent=new Intent(context,PlayListDetailsActivity.class);
        intent.putExtra(EXTRA_FOLDER,folder);
        return intent;
    }

}
