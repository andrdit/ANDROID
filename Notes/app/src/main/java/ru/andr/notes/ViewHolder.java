package ru.andr.notes;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final int IS_TRUE = 1;
    private final int IS_FALSE = 0;

    public TextView textTask;
    public TextView textName;
    public TextView textDateCreate;
    public TextView textTimeCreate;
    public TextView textShortDescription;
    public AppCompatImageView mIsFavoriteStatus;
    CheckBox mCheckBoxCompleted;
    SwitchMaterial mSwitchCanceled;

    private @DrawableRes
    int[] mImageFavorite;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textTask = itemView.findViewById(R.id.item_list_note_task);
        textName = itemView.findViewById(R.id.item_list_note_name);
        textDateCreate = itemView.findViewById(R.id.item_list_note_date);
        textTimeCreate = itemView.findViewById(R.id.item_list_note_time);
        mIsFavoriteStatus = itemView.findViewById(R.id.item_list_is_favorite);
        textShortDescription  =itemView.findViewById(R.id.item_list_note_short_description);

        mCheckBoxCompleted = itemView.findViewById(R.id.item_list_note_completed);
        mSwitchCanceled    = itemView.findViewById(R.id.item_list_note_canceled);
    }

    public void populate(FragmentNoteList fragment, NotesSource noteSource, int position) {

        Note note = noteSource.getItemAt(position);
        mImageFavorite = noteSource.getImageFavoriteStatus();
        textTask.setText(note.getTask());
        textName.setText(note.getName());
        textDateCreate.setText(note.getDateCreate());
        textTimeCreate.setText(note.getTimeCreate());
        textShortDescription.setText(note.getDescription().substring(0,note.getDescription().length() / 2) + "...");

        mSwitchCanceled.setChecked(note.isCanceled());
        mCheckBoxCompleted.setChecked(note.isCompleted());

        if (note.isFavorite())
            mIsFavoriteStatus.setImageResource(mImageFavorite[IS_TRUE]);
        else
            mIsFavoriteStatus.setImageResource(mImageFavorite[IS_FALSE]);

        textName.setOnLongClickListener(v->{
            fragment.setLastSelectedItemNoteList(getLayoutPosition());
            return false;
        });

        fragment.registerForContextMenu(textName);
    }

    public void clear(Fragment fragment){
        textName.setOnLongClickListener(null);
        fragment.unregisterForContextMenu(textName);
    }
}
