package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/2/11.
 * 获取音乐专辑封面图
 **/

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

public class AlbumUtils
{
    private static final String TAG = "AlbumUtils";

    public static Bitmap parseAlbum(Song song)
    {
        return parseAlbum(new File(song.getPath()));
    }

    public static Bitmap parseAlbum(File file)
    {
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        try
        {
            metadataRetriever.setDataSource(file.getAbsolutePath());
        }
        catch (Exception e)
        {
            Log.e(TAG, "parseAlbum: ", e);
        }
        byte[] albumData = metadataRetriever.getEmbeddedPicture();
        if (albumData != null)
        {
            return BitmapFactory.decodeByteArray(albumData, 0, albumData.length);
        }
        return null;

    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap)
    {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int color = 0xff424242;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;

    }

}
