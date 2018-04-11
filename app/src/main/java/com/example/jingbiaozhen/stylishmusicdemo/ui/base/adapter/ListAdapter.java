package com.example.jingbiaozhen.stylishmusicdemo.ui.base.adapter;
/*
 * Created by jingbiaozhen on 2018/1/27.
 * 常用的列表的适配器
 **/

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbiaozhen.stylishmusicdemo.player.OnItemClickListener;
import com.example.jingbiaozhen.stylishmusicdemo.player.OnItemLongClickListener;

public abstract class ListAdapter<T, V extends IAdapterView> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private Context mContext;

    private List<T> mData;

    private OnItemClickListener mItemClickListener;

    private OnItemLongClickListener mItemLongClickListener;

    private int mLastItemClickPosition;

    public ListAdapter(Context context, List<T> data)
    {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = (View) createView(mContext);
        final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(itemView)
        {
        };
        if (mItemClickListener != null)
        {
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        mLastItemClickPosition = position;
                        mItemClickListener.onItemClick(position);
                    }
                }
            });

        }
        if (mItemLongClickListener != null)
        {
            itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        mItemLongClickListener.onItemClick(position);
                    }

                    return false;
                }
            });
        }

        return holder;
    }

    protected abstract V createView(Context context);


    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        IAdapterView itemView = (V) holder.itemView;
        itemView.bind(getItem(position), position);

    }

    public T getItem(int position)
    {
        return mData.get(position);
    }

    @Override
    public int getItemCount()
    {
        if (mData == null)
        {
            return 0;
        }
        return mData.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public List<T> getData()
    {
        return mData;
    }

    public void setData(List<T> data)
    {
        mData = data;
    }

    public void addData(List<T> data)
    {
        if (mData == null)
        {
            mData = data;
        }
        else
        {
            mData.addAll(data);
        }
    }
    public void clear(){
        if(mData!=null){
            mData.clear();
        }
    }

    public OnItemClickListener getItemClickListener() {
        return mItemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public OnItemLongClickListener getItemLongClickListener() {
        return mItemLongClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    public void setLastItemClickPosition(int lastItemClickPosition) {
        mLastItemClickPosition = lastItemClickPosition;
    }
}
