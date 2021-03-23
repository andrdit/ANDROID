package ru.andr.notes;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class NotesSourceImpl implements NotesSource, Parcelable {

    private ArrayList<Note> mNotes;
    private @DrawableRes int[] mImageFavorite;

    public NotesSourceImpl(Resources resources) {

        int[] indexNote = resources.getIntArray(R.array.index_note);
        String[] nameNote = resources.getStringArray(R.array.name_note);
        String[] descriptionNote = resources.getStringArray(R.array.description_note);
        String[] createDateNote = resources.getStringArray(R.array.createDate_note);
        int[] isFavorite = resources.getIntArray(R.array.is_favorite_note);

        mNotes = new ArrayList();
        for (int index : indexNote) {
            Note note = new Note(index, nameNote[index], descriptionNote[index], createDateNote[index], isFavorite[index],false,false);
            mNotes.add(note);
        }

        TypedArray imagesStatus = resources.obtainTypedArray(R.array.statusImageIsFavorite);
        mImageFavorite = new int[imagesStatus.length()];
        for (int i = 0; i < mImageFavorite.length; i++) {
            mImageFavorite[i] = imagesStatus.getResourceId(i,-1);
        }
        imagesStatus.recycle();
    }

    protected NotesSourceImpl(Parcel in) {
        mNotes = in.createTypedArrayList(Note.CREATOR);
        mImageFavorite = in.createIntArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mNotes);
        dest.writeIntArray(mImageFavorite);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotesSourceImpl> CREATOR = new Creator<NotesSourceImpl>() {
        @Override
        public NotesSourceImpl createFromParcel(Parcel in) {
            return new NotesSourceImpl(in);
        }

        @Override
        public NotesSourceImpl[] newArray(int size) {
            return new NotesSourceImpl[size];
        }
    };

    @Override
    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    @Override
    public Note getItemAt(int idx) {
        return mNotes.get(idx);
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    @Override
    public int[] getImageFavoriteStatus() {
        return mImageFavorite;
    }

    @Override
    public void add(@NonNull Note note) {
        mNotes.add(note);
    }

    @Override
    public void clearAll() {
        mNotes.clear();
    }
}
