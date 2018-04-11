package com.example.jingbiaozhen.stylishmusicdemo.ui.playlist;
/*
 * Created by jingbiaozhen on 2018/1/26.
 **/

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jingbiaozhen.stylishmusicdemo.R;
import com.example.jingbiaozhen.stylishmusicdemo.data.model.PlayList;
import com.example.jingbiaozhen.stylishmusicdemo.ui.base.BaseDialogFragment;

public class EditPlayListDialogFragment extends BaseDialogFragment implements Dialog.OnShowListener
{

    public static final String AGUMENT_PLAY_LIST = "play_list";

    private PlayList mPlayList;

    private boolean isEditMode;

    private EditText editTextName;

    private Callback mCallback;

    public static EditPlayListDialogFragment newInstance()
    {
        return newInstance(null);
    }

    public static EditPlayListDialogFragment newInstance(PlayList playList)
    {
        EditPlayListDialogFragment fragment = new EditPlayListDialogFragment();
        if (playList != null)
        {
            Bundle bundle = new Bundle();
            bundle.putParcelable(AGUMENT_PLAY_LIST, playList);
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            mPlayList = bundle.getParcelable(AGUMENT_PLAY_LIST);
        }

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = new AlertDialog.Builder(getContext()).setTitle(getTitle()).setView(
                R.layout.dialog_create_or_edit_playlist).setNegativeButton(R.string.mp_cancel, null).setPositiveButton(
                        R.string.mp_Confirm, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                onConfirm();
                            }
                        }).create();
        dialog.setOnShowListener(this);

        return dialog;
    }

    private void onConfirm()
    {
        if (mCallback != null)
        {
            PlayList playList = mPlayList;
            if (playList == null)
            {
                playList = new PlayList();
            }
            playList.setName(editTextName.getText().toString());
            if (isEditMode)
            {
                mCallback.onEdited(playList);
            }
            else
            {
                mCallback.onCreated(playList);
            }

        }

    }

    public EditPlayListDialogFragment setCallback(Callback callback)
    {
        mCallback = callback;
        return this;
    }

    @Override
    public void onShow(DialogInterface dialog)
    {
        resizeDialogSize();
        if (editTextName == null)
        {
            editTextName = getDialog().findViewById(R.id.edit_text);
            editTextName.setOnEditorActionListener(new TextView.OnEditorActionListener()
            {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
                {
                    if (actionId == EditorInfo.IME_ACTION_DONE)
                    {
                        if (editTextName.length() > 0)
                        {
                            onConfirm();
                        }
                        return true;
                    }

                    return false;
                }
            });
        }
        if (isEditMode())
        {
            editTextName.setText(mPlayList.getName());
        }
        editTextName.requestFocus();
        editTextName.setSelection(editTextName.length());

    }

    public String getTitle()
    {
        return getContext().getString(isEditMode ? R.string.play_list_edit : R.string.play_list_create);
    }

    public boolean isEditMode()
    {
        return mPlayList != null;
    }

    public static EditPlayListDialogFragment createPlayList()
    {
        return newInstance();
    }

    public static EditPlayListDialogFragment editPlayList(PlayList playList)
    {
        return newInstance(playList);
    }

    public interface Callback
    {
        void onCreated(PlayList playList);

        void onEdited(PlayList playList);
    }
}
