package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;
/*
 * Created by jingbiaozhen on 2018/1/28.
 * 播放列表条目View
 **/

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.adapter.IAdapterView;
import com.example.jingbiaozhen.stylishmusicdemo.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayListItemView extends RelativeLayout implements IAdapterView<PlayList>
{

    @BindView(R.id.imageView_album)
    ImageView imageViewAlbum;

    @BindView(R.id.text_view_name)
    TextView textViewName;

    @BindView(R.id.text_view_info)
    TextView textViewInfo;

    @BindView(R.id.layout_action)
    View buttonAction;

    public PlayListItemView(Context context)
    {
        super(context);
        View.inflate(context, R.layout.item_play_list, this);
        ButterKnife.bind(this);
    }

    @Override
    public void bind(PlayList item, int position)
    {
        if (item.isFavorite())
        {
            imageViewAlbum.setImageResource(R.drawable.ic_favorite_yes);
        }
        else
        {
            imageViewAlbum.setImageDrawable(ViewUtils.generateAlbumDrawable(getContext(), item.getName()));
        }
        textViewName.setText(item.getName());
        textViewInfo.setText(getResources().getQuantityString(R.plurals.mp_play_list_items_formatter,
                item.getItemCount(), item.getItemCount()));

    }
}
