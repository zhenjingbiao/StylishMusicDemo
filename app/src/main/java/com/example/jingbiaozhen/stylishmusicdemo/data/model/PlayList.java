package com.example.jingbiaozhen.stylishmusicdemo.data.model;
/*
 * Created by jingbiaozhen on 2018/1/25.
 * 播放列表的Bean
 * 
 **/

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.jingbiaozhen.stylishmusicdemo.player.PlayMode;
import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.MapCollection;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

public class PlayList implements Parcelable
{

    public static final String COLUMN_FAVORITE = "favorite";

    private static final int NO_POSITION_INDEX = -1;

    // int id,String name,int numOfSong,boolean favorite,Date
    // createAt,updateAt,List<Song>mSongs,PlayMode mPlayMode
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    private String name;

    private int numOfSongs;

    @Column(COLUMN_FAVORITE)
    private boolean favorite;

    private Date createAt;

    private Date updateAt;

    @MapCollection(ArrayList.class)
    @Mapping(Relation.OneToMany)
    private List<Song> mSongs = new ArrayList<>();

    @Ignore
    private int playingIndex = -1;

    private PlayMode mPlayMode = PlayMode.LOOP;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getNumOfSongs()
    {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs)
    {
        this.numOfSongs = numOfSongs;
    }

    public boolean isFavorite()
    {
        return favorite;
    }

    public void setFavorite(boolean favorite)
    {
        this.favorite = favorite;
    }

    public Date getCreateAt()
    {
        return createAt;
    }

    public void setCreateAt(Date createAt)
    {
        this.createAt = createAt;
    }

    public Date getUpdateAt()
    {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt)
    {
        this.updateAt = updateAt;
    }

    public List<Song> getSongs()
    {
        if (mSongs == null)
        {
            mSongs = new ArrayList<>();
        }
        return mSongs;
    }

    public void setSongs(List<Song> songs)
    {
        if (songs == null)
        {
            songs = new ArrayList<>();
        }
        mSongs = songs;
    }

    public int getPlayingIndex()
    {
        return playingIndex;
    }

    public void setPlayingIndex(int playingIndex)
    {
        this.playingIndex = playingIndex;
    }

    public PlayMode getPlayMode()
    {
        return mPlayMode;
    }

    public void setPlayMode(PlayMode playMode)
    {
        mPlayMode = playMode;
    }

    public PlayList()
    {

    }

    public PlayList(Song song)
    {
        mSongs.add(song);
        numOfSongs = 1;
    }

    protected PlayList(Parcel in)
    {
        readFromParcel(in);
    }

    public static final Creator<PlayList> CREATOR = new Creator<PlayList>()
    {
        @Override
        public PlayList createFromParcel(Parcel in)
        {
            return new PlayList(in);
        }

        @Override
        public PlayList[] newArray(int size)
        {
            return new PlayList[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.numOfSongs);
        dest.writeByte(this.favorite ? (byte) 1 : (byte) 0);
        dest.writeLong(this.createAt != null ? createAt.getTime() : -1);
        dest.writeLong(this.updateAt != null ? updateAt.getTime() : -1);
        dest.writeTypedList(this.mSongs);
        dest.writeInt(this.playingIndex);
        dest.writeInt(this.mPlayMode == null ? -1 : this.mPlayMode.ordinal());

    }

    public void readFromParcel(Parcel in)
    {
        this.id = in.readInt();
        this.name = in.readString();
        this.numOfSongs = in.readInt();
        this.favorite = in.readByte() != 0;
        long tmpCreateAt = in.readLong();
        this.createAt = tmpCreateAt == -1 ? null : new Date(tmpCreateAt);
        long tmpUpdateAt = in.readLong();
        this.updateAt = tmpUpdateAt == -1 ? null : new Date(tmpUpdateAt);
        this.mSongs = in.createTypedArrayList(Song.CREATOR);
        this.playingIndex = in.readInt();
        int tmpPlayMode = in.readInt();
        this.mPlayMode = tmpPlayMode == -1 ? null : PlayMode.values()[tmpPlayMode];

    }

    public int getItemCount()
    {
        return mSongs == null ? 0 : mSongs.size();
    }

    public void addSong(Song song)
    {
        if (song == null)
        {
            return;
        }
        mSongs.add(song);
        numOfSongs = mSongs.size();
    }

    public void addSong(Song song, int index)
    {
        if (song != null)
        {
            mSongs.add(index, song);
            numOfSongs = mSongs.size();
        }
    }

    public void addSong(List<Song> songs, int index)
    {
        if (songs != null && !songs.isEmpty())
        {
            mSongs.addAll(index, songs);
            numOfSongs = mSongs.size();
        }
    }

    public boolean removeSong(Song song)
    {
        if (song == null)
        {
            return false;
        }
        int index;
        if ((index = mSongs.indexOf(song)) != -1)
        {
            if (mSongs.remove(index) != null)
            {
                numOfSongs = mSongs.size();
                return true;
            }
        }
        else
        {
            for (Iterator<Song> iterator = mSongs.iterator(); iterator.hasNext();)
            {
                Song item = iterator.next();
                if (song.getPath().equals(item.getPath()))
                {
                    iterator.remove();
                    numOfSongs = mSongs.size();
                    return true;
                }
            }

        }
        return false;
    }

    public boolean prepare()
    {
        if (!mSongs.isEmpty())
        {
            if (playingIndex == NO_POSITION_INDEX)
            {
                playingIndex = 0;
            }
            return true;
        }
        return false;
    }

    public Song getCurrentSong()
    {
        if (playingIndex != NO_POSITION_INDEX)
        {
            return mSongs.get(playingIndex);
        }
        return null;
    }

    public boolean hasLast()
    {
        return mSongs != null && mSongs.size() != 0;
    }

    public boolean hasNext(boolean fromComplete)
    {
        if (mSongs.isEmpty())
        {
            return false;
        }
        if (fromComplete)
        {
            if (mPlayMode == PlayMode.LIST && playingIndex + 1 >= mSongs.size())
            {
                return false;
            }
        }
        return true;

    }

    /**
     * 获取上一首歌
     */
    public Song last()
    {
        switch (mPlayMode)
        {
            case LIST:
            case LOOP:
            case SINGLE:
                int newPlayIndex = playingIndex - 1;
                if (newPlayIndex < 0)
                {
                    newPlayIndex = mSongs.size() - 1;
                }
                playingIndex = newPlayIndex;
                break;
            case SHUFFLE:
                playingIndex = randomPlayIndex();
                break;

        }
        return mSongs.get(playingIndex);
    }

    public Song next()
    {
        switch (mPlayMode)
        {
            case LIST:
            case LOOP:
            case SINGLE:
                int newPlayIndex = playingIndex + 1;
                if (newPlayIndex >= mSongs.size())
                {
                    newPlayIndex = 0;
                }
                playingIndex = newPlayIndex;
                break;
            case SHUFFLE:
                playingIndex = randomPlayIndex();
                break;

        }
        return mSongs.get(playingIndex);
    }

    /**
     * 随机循环的索引值，如果随机后跟当前一样，再随机一次
     */
    private int randomPlayIndex()
    {

        int randomIndex = new Random(mSongs.size()).nextInt();
        if (mSongs.size() > 1 && randomIndex == playingIndex)
        {
            randomIndex = randomPlayIndex();
        }
        return randomIndex;

    }

    public static PlayList fromFolder(Folder folder)
    {
        PlayList playList = new PlayList();
        playList.setName(folder.getName());
        playList.setSongs(folder.getSongs());
        playList.setNumOfSongs(folder.getNumOfSongs());
        return playList;

    }

}
