package ru.andr.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private final int IS_TRUE = 1;
    private final int IS_FALSE = 0;

    public TextView textName;
    public AppCompatImageView mIsFavoriteStatus;
    private @DrawableRes
    int[] mImageFavorite;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.item_list_note);
        mIsFavoriteStatus = itemView.findViewById(R.id.item_list_is_favorite);
    }

    public void populate(NotesSource noteSource, int position) {

        Note note = noteSource.getItemAt(position);
        mImageFavorite = noteSource.getImageFavoriteStatus();
        textName.setText(note.getName());

        if (note.isFavorite())
            mIsFavoriteStatus.setImageResource(mImageFavorite[IS_TRUE]);
        else
            mIsFavoriteStatus.setImageResource(mImageFavorite[IS_FALSE]);
    }
}
