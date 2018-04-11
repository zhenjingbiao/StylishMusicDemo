package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/2/2.
 **/

import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import com.example.jingbiaozhen.stylishmusicdemo.data.model.Folder;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.Song;

public class FileUtils
{

    private static final String UNKNOWN = "unknown";

    public static String readableFileSize(long size)
    {
        if (size <= 0)
        {
            return "0";
        }
        String[] units = new String[] {"b", "kb", "M", "G", "T"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.##").format(size / Math.pow(1024, digitGroups)) + "" + units[digitGroups];

    }

    public static boolean isMusic(File file)
    {

        final String REGEX = "(.*/)*.+\\.(mp3|m4a|ogg|wav|aac)$";
        return file.getName().matches(REGEX);
    }

    public static boolean isLyric(File file)
    {
        return file.getName().toLowerCase().endsWith(".lrc");
    }

    public static List<Song> musicFiles(File dir)
    {
        List<Song> songs = new ArrayList<>();

        if (dir != null && dir.isDirectory())
        {
            File[] files = dir.listFiles(new FileFilter()
            {
                @Override
                public boolean accept(File pathname)
                {

                    return pathname.isFile() && isMusic(pathname);
                }
            });

            for (File file : files)
            {
                Song song = fileToMusic(file);
                if (song != null)
                {
                    songs.add(song);
                }
            }

        }
        if (songs.size() > 1)
        {
            Collections.sort(songs, new Comparator<Song>()
            {
                @Override
                public int compare(Song left, Song right)
                {
                    return left.getTitle().compareTo(right.getTitle());
                }
            });
        }
        return songs;
    }

    private static Song fileToMusic(File file)
    {
        if (file.length() == 0)
        {
            return null;
        }
        MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
        metadataRetriever.setDataSource(file.getAbsolutePath());

        String keyDuration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        if (keyDuration != null && !keyDuration.matches("\\d+"))
        {
            return null;
        }
        int duration;
        duration = Integer.parseInt(keyDuration);
        String title = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_TITLE, file.getName());
        String displayName = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_TITLE,
                file.getName());
        String artist = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_ARTIST, UNKNOWN);
        String album = extractMetadata(metadataRetriever, MediaMetadataRetriever.METADATA_KEY_ALBUM, UNKNOWN);
        Song song = new Song();
        song.setAlbum(album);
        song.setArtist(artist);
        song.setDisplayName(displayName);
        song.setTitle(title);
        song.setSize((int) file.length());
        song.setDuration(duration);
        song.setPath(file.getAbsolutePath());

        return song;
    }

    private static String extractMetadata(MediaMetadataRetriever metadataRetriever, int metadataKey, String defultName)
    {
        String value = metadataRetriever.extractMetadata(metadataKey);
        if (TextUtils.isEmpty(value))
        {
            value = defultName;
        }
        return value;
    }

    public static Folder folderFromDir(File dir)
    {
        Folder folder = new Folder(dir.getName(), dir.getAbsolutePath());
        List<Song> songs = musicFiles(dir);
        folder.setNumOfSongs(songs.size());
        folder.setSongs(songs);
        return folder;

    }

}
