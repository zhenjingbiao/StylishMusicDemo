package com.example.jingbiaozhen.stylishmusicdemo;

import java.util.List;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;

import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseActivity;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseFragment;
import com.example.jingbiaozhen.stylishmusicdemo.ui.main.MainPagerAdapter;
import com.example.jingbiaozhen.stylishmusicdemo.ui.music.MusicPlayerFragment;
import com.example.jingbiaozhen.stylishmusicdemo.ui.playlist.PlayListFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity
{

    private static final int DEFAULT_PAGE_INDEX = 2;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    @BindViews({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    List<RadioButton> mRadioButtons;

    private String[] mTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mTitles=getResources().getStringArray(R.array.main_titles);
        BaseFragment []fragments=new BaseFragment[mTitles.length];
        fragments[0]=new PlayListFragment();
        fragments[1]=new MusicPlayerFragment();
        //fragments[2]=new LoacalFilesFragment();
       // fragments[3]=new SettingsFragment();
        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),mTitles,fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(adapter.getCount()-1);
        mViewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.margin_large));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mRadioButtons.get(position).setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioButtons.get(DEFAULT_PAGE_INDEX);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
    @OnCheckedChanged({R.id.radio_button_play_list, R.id.radio_button_music, R.id.radio_button_local_files, R.id.radio_button_settings})
    public void onRadioButtonChecked(RadioButton button,boolean isCheck){
        if(isCheck){
            onItemChecked(mRadioButtons.indexOf(button));
        }
    }

    private void onItemChecked(int i) {
        mViewPager.setCurrentItem(i);
        mToolbar.setTitle(mTitles[i]);
    }


}
