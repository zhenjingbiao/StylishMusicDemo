package com.example.jingbiaozhen.stylishmusicdemo.ui.main;
/*
 * Created by jingbiaozhen on 2018/2/7.
 **/

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseFragment;

public class MainPagerAdapter extends FragmentPagerAdapter
{
    private String[] mTitles;

    private BaseFragment[] mFragments;

    public MainPagerAdapter(FragmentManager fm, String[] titles, BaseFragment[] fragments)
    {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position)
    {
        return mFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return mTitles[position];
    }

    @Override
    public int getCount()
    {
        if (mTitles != null)
        {
            return mTitles.length;
        }
        return 0;
    }
}
