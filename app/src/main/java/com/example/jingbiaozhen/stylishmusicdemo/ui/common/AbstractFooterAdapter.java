package com.example.jingbiaozhen.stylishmusicdemo.ui.common;
/*
 * Created by jingbiaozhen on 2018/1/27.
 * 脚布局适配器
 **/

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingbiaozhen.stylishmusicdemo.ui.base.adapter.IAdapterView;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.adapter.ListAdapter;

public abstract class AbstractFooterAdapter<T, V extends IAdapterView> extends ListAdapter<T, V>
{

    protected static final int VIEW_TYPE_ITEM = 1;

    protected static final int VIEW_TYPE_FOOTER = 2;

    public AbstractFooterAdapter(Context context, List<T> data)
    {
        super(context, data);
    }

    @Override
    protected V createView(Context context)
    {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_FOOTER)
        {
            return new RecyclerView.ViewHolder(createFooterView())
            {
            };
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (getItemViewType(position) == VIEW_TYPE_ITEM)
        {
            super.onBindViewHolder(holder, position);
        }
    }

    /**
     * 默认不使用脚布局
     *
     * @return 是否使用
     * @author jingbiaozhen
     */
    protected boolean isFooterEnable()
    {
        return false;
    }

    /**
     * 由子类重写创建脚布局的方法
     */
    protected View createFooterView()
    {
        return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isFooterEnable() && position == getItemCount() - 1)
        {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount()
    {
        int itemCount = super.getItemCount();
        if (isFooterEnable())
        {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public T getItem(int position)
    {
        if (getItemViewType(position) == VIEW_TYPE_FOOTER)
        {
            return null;
        }
        return super.getItem(position);
    }
}
