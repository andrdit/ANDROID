package ru.andr.notes;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public interface NotesSource {
    ArrayList<Note> getNotes();
    Note getItemAt(int idx);
    int getItemCount();
    @DrawableRes int[] getImageFavoriteStatus();
    void add(@NonNull Note note);
    void remove(int position);
    void clearAll();
}
