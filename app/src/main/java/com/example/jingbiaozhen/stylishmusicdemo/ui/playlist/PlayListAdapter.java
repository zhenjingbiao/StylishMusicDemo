package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;
/*
 * Created by jingbiaozhen on 2018/1/28.
 * 播放列表适配器，ListAdapter+PlayListAdapter
 **/

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.ui.common.AbstractFooterAdapter;

public class PlayListAdapter extends AbstractFooterAdapter<PlayList, PlayListItemView>
{
    private Context mContext;

    private AddPlayListCallback mAddPlayListListCallback;

    private View mFooterView;

    private TextView textViewSummary;

    public PlayListAdapter(Context context, List<PlayList> data)
    {
        super(context, data);
        mContext = context;
        registerAdapterDataObserver(new RecyclerView.AdapterDataObserver()
        {
            @Override
            public void onChanged()
            {
                updateFooterView();
            }
        });
    }

    @Override
    protected PlayListItemView createView(Context context)
    {
        return new PlayListItemView(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        final RecyclerView.ViewHolder holder = super.onCreateViewHolder(parent, viewType);
        if (holder.itemView instanceof PlayListItemView)
        {
            final PlayListItemView itemView = (PlayListItemView) holder.itemView;

            itemView.buttonAction.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int position = holder.getAdapterPosition();
                    if (mAddPlayListListCallback != null)
                    {
                        mAddPlayListListCallback.action(itemView.buttonAction, position);
                    }

                }
            });

        }
        return holder;

    }

    @Override
    protected View createFooterView()
    {
        if (mFooterView == null)
        {
            mFooterView = View.inflate(mContext, R.layout.item_play_list_footer, null);
            View layoutAndPlaylist = mFooterView.findViewById(R.id.layout_add_playlist);
            layoutAndPlaylist.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mAddPlayListListCallback.onAddPlayList();
                }
            });
            textViewSummary = mFooterView.findViewById(R.id.text_view_summary);

        }
        updateFooterView();

        return mFooterView;
    }

    @Override
    protected boolean isFooterEnable()
    {
        return true;
    }

    public void updateFooterView()
    {
        if (textViewSummary == null)
        {
            return;
        }
        int itemCount = getItemCount() - 1;
        if (itemCount > 1)
        {
            textViewSummary.setVisibility(View.VISIBLE);
            textViewSummary.setText(mContext.getString(R.string.play_list_footer_end_summary_formatter, itemCount));

        }
        else
        {
            textViewSummary.setVisibility(View.GONE);
        }

    }

    public void setAddPlayListCallback(AddPlayListCallback callback)
    {
        mAddPlayListListCallback = callback;
    }

    interface AddPlayListCallback
    {
        void action(View actionView, int position);

        void onAddPlayList();
    }

}
