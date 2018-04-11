package com.example.jingbiaozhen.stylishmusicdemo.data.model;
/*
 * Created by jingbiaozhen on 2018/1/26.
 **/

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.MapCollection;
import com.litesuits.orm.db.annotation.Mapping;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.annotation.Unique;
import com.litesuits.orm.db.enums.AssignType;
import com.litesuits.orm.db.enums.Relation;

@Table("folder")
public class Folder implements Parcelable
{
    public static final String COLUMN_NAME = "name";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;

    @Column(COLUMN_NAME)
    private String name;

    @Unique
    private String path;

    private int numOfSongs;

    @MapCollection(ArrayList.class)
    @Mapping(Relation.ManyToMany)
    private List<Song> mSongs = new ArrayList<>();

    private Date createdAt;

    public Folder()
    {

    }

    public Folder(String name, String path)
    {
        this.name = name;
        this.path = path;
    }

    protected Folder(Parcel in)
    {
        readFolderFromParcel(in);
    }

    private void readFolderFromParcel(Parcel in)
    {
        this.id = in.readInt();
        this.name = in.readString();
        this.path = in.readString();
        this.numOfSongs = in.readInt();
        this.mSongs = in.createTypedArrayList(Song.CREATOR);
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
    }

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

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public int getNumOfSongs()
    {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs)
    {
        this.numOfSongs = numOfSongs;
    }

    public List<Song> getSongs()
    {
        return mSongs;
    }

    public void setSongs(List<Song> songs)
    {
        mSongs = songs;
    }

    public Date getCreateAt()
    {
        return createdAt;
    }

    public void setCreateAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public static final Creator<Folder> CREATOR = new Creator<Folder>()
    {
        @Override
        public Folder createFromParcel(Parcel in)
        {
            return new Folder(in);
        }

        @Override
        public Folder[] newArray(int size)
        {
            return new Folder[size];
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
        dest.writeString(this.path);
        dest.writeInt(this.numOfSongs);
        dest.writeTypedList(this.mSongs);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
    }
}
