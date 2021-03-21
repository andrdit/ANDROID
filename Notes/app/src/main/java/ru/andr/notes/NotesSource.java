package ru.andr.notes;

import androidx.annotation.DrawableRes;

import java.util.ArrayList;

public interface NotesSource {
    ArrayList<Note> getNotes();
    Note getItemAt(int idx);
    int getItemCount();
    @DrawableRes int[] getImageFavoriteStatus();
}
