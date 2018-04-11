package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.RxBus;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.data.source.AppRepository;
import com.example.jingbiaozhen.stylishmusicdemo.event.FavoriteChangeEvent;
import com.example.jingbiaozhen.stylishmusicdemo.event.PlayListCreatedEvent;
import com.example.jingbiaozhen.stylishmusicdemo.event.PlayListNowEvent;
import com.example.jingbiaozhen.stylishmusicdemo.event.PlayListUpdatedEvent;
import com.example.jingbiaozhen.stylishmusicdemo.player.OnItemClickListener;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseFragment;
import com.example.jingbiaozhen.stylishmusicdemo.ui.common.DefaultDividerDecoration;
import com.example.jingbiaozhen.stylishmusicdemo.ui.detail.PlayListDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/*
 * Created by jingbiaozhen on 2018/1/25.
 * 播放列表Fragment
 **/

public class PlayListFragment extends BaseFragment
        implements PlayListContract.View, EditPlayListDialogFragment.Callback, PlayListAdapter.AddPlayListCallback
{
    @BindView(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    private ProgressBar mProgressBar;

    private PlayListAdapter mAdapter;

    private PlayListContract.Presenter mPresenter;

    private int mEditIndex;

    private int mDeleteIndex;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(view);
        mAdapter = new PlayListAdapter(getActivity(), null);
        mAdapter.setItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                PlayList playList = mAdapter.getItem(position);
                startActivity(PlayListDetailsActivity.launchIntentForPlayList(getActivity(), playList));
            }
        });
        mAdapter.setAddPlayListCallback(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DefaultDividerDecoration());
        new PlayListPresenter(AppRepository.getInstance(), this).subscribe();

    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mPresenter.unSubscribe();

    }

    @Override
    protected Subscription subscribeEvents()
    {
        return RxBus.getInstance().toObservable().observeOn(AndroidSchedulers.mainThread()).doOnNext(
                new Action1<Object>()
                {
                    @Override
                    public void call(Object o)
                    {
                        if (o instanceof PlayListCreatedEvent)
                        {
                            onPlayListCreatedEvent((PlayListCreatedEvent) o);

                        }
                        else if (o instanceof FavoriteChangeEvent)
                        {
                            onFavoriteChangeEvent((FavoriteChangeEvent) o);

                        }
                        else if (o instanceof PlayListUpdatedEvent)
                        {
                            onPlayListUpdatedEvent((PlayListUpdatedEvent) o);

                        }

                    }
                }).subscribe(RxBus.defaultSubscriber());
    }

    private void onPlayListCreatedEvent(PlayListCreatedEvent o)
    {
        mAdapter.getData().add(o.mPlayList);
        mAdapter.notifyDataSetChanged();
        mAdapter.updateFooterView();

    }

    private void onFavoriteChangeEvent(FavoriteChangeEvent o)
    {
        mPresenter.loadPlaylist();

    }

    private void onPlayListUpdatedEvent(PlayListUpdatedEvent o)
    {

        mPresenter.loadPlaylist();
    }

    @Override
    public void setPresenter(PlayListContract.Presenter presenter)
    {
        mPresenter = presenter;
    }

    @Override
    public void showLoading()
    {
        mProgressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideLoading()
    {
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void handleError(Throwable error)
    {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPlayListsLoaded(List<PlayList> playLists)
    {
        mAdapter.setData(playLists);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPlayListCreated(PlayList playList)
    {
        mAdapter.getData().add(playList);
        mAdapter.notifyItemInserted(mAdapter.getData().size() - 1);
        mAdapter.updateFooterView();

    }

    @Override
    public void onPlayListEdited(PlayList playList)
    {
        mAdapter.getData().set(mEditIndex, playList);
        mAdapter.notifyItemChanged(mEditIndex);
        mAdapter.updateFooterView();

    }

    @Override
    public void onPlayListDeleted(PlayList playList)
    {
        mAdapter.getData().remove(mDeleteIndex);
        mAdapter.notifyItemRemoved(mDeleteIndex);
        mAdapter.updateFooterView();

    }

    @Override
    public void action(View actionView, final int position)
    {
        final PlayList playList = mAdapter.getItem(position);
        PopupMenu actionMenu = new PopupMenu(getActivity(), actionView, Gravity.END | Gravity.BOTTOM);
        actionMenu.inflate(R.menu.play_list_action);
        if (playList.isFavorite())
        {
            actionMenu.getMenu().findItem(R.id.item_menu_rename).setVisible(false);
            actionMenu.getMenu().findItem(R.id.item_menu_delete).setVisible(false);
        }
        actionMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {

            @Override
            public boolean onMenuItemClick(MenuItem item)
            {

                switch (item.getItemId())
                {
                    case R.id.item_menu_play_now:
                        PlayListNowEvent playListNowEvent = new PlayListNowEvent(playList, 0);
                        RxBus.getInstance().post(playListNowEvent);

                        break;
                    case R.id.item_menu_rename:
                        mEditIndex = position;
                        EditPlayListDialogFragment.editPlayList(playList).setCallback(PlayListFragment.this).show(
                                getFragmentManager().beginTransaction(), "EditPlayList");

                        break;
                    case R.id.item_menu_delete:
                        mDeleteIndex = position;
                        mPresenter.deletePlayList(playList);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        actionMenu.show();

    }

    @Override
    public void onAddPlayList()
    {
        EditPlayListDialogFragment.createPlayList().setCallback(PlayListFragment.this).show(
                getFragmentManager().beginTransaction(), "CreatePlayList");

    }

    // Create or Edit Play List Callbacks
    @Override
    public void onCreated(PlayList playList)
    {
        mPresenter.createPlaylist(playList);
    }

    @Override
    public void onEdited(PlayList playList)
    {
        mPresenter.editPlaylist(playList);
    }
}
