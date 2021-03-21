package ru.andr.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class Note implements Parcelable {

    private int mIndex;
    private String mName;
    private String mDescription;
    private String mDateCreate;
    private boolean mIsFavorite;
    private boolean mIsCompleted;
    private boolean mIsCanceled;

    public Note(int index, String name, String description, String dateCreate, int isFavorite, boolean isCompleted, boolean isCanceled ){
        mIndex = index;
        mName = name;
        mDescription = description;
        mDateCreate = dateCreate;
        if(isFavorite == 1)
            mIsFavorite = true;
        mIsCompleted = isCompleted;
        mIsCanceled = isCanceled;

    }

    protected Note(Parcel in) {
        mIndex = in.readInt();
        mName = in.readString();
        mDescription = in.readString();
        mDateCreate = in.readString();
        mIsFavorite = in.readByte() != 0;
        mIsCompleted = in.readByte() != 0;
        mIsCanceled = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIndex);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mDateCreate);
        dest.writeByte((byte) (mIsFavorite ? 1 : 0));
        dest.writeByte((byte) (mIsCompleted ? 1 : 0));
        dest.writeByte((byte) (mIsCanceled ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public int getIndex() {
        return mIndex;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getDateCreate() {
        return mDateCreate;
    }

    public boolean isFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }
}
