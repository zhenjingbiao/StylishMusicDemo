package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/1/31.
 **/

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.ui.widget.CharacterDrawable;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class ViewUtils
{
    public static void setLightStatusBar(@NonNull View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int flag = view.getSystemUiVisibility();
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flag);
        }

    }

    public static void clearLightStatusBar(@NonNull View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            int flag = view.getSystemUiVisibility();
            flag &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flag);
        }
    }

    public static CharacterDrawable generateAlbumDrawable(Context context, String albumName)
    {
        if (context == null || albumName == null)
        {
            return null;
        }
        return new CharacterDrawable.Builder()
                .setCharacter(albumName.length() == 0 ? ' ' : albumName.charAt(0))
                .setBackgroundColor(ContextCompat.getColor(context, R.color.mp_characterView_background))
                .setCharacterTextColor(ContextCompat.getColor(context, R.color.mp_characterView_textColor))
                .setCharacterPadding(context.getResources().getDimensionPixelSize(R.dimen.mp_characterView_padding))
                .build();
    }
}
