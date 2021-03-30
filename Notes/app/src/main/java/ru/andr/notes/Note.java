package ru.andr.notes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class Note implements Parcelable {

    private int mIndex;
    private String mTask;
    private String mName;
    private String mDescription;
    private String mDateCreate;
    private String mTimeCreate;
    private boolean mIsFavorite;
    private boolean mIsCompleted;
    private boolean mIsCanceled;

    public Note(int index, String task, String name, String description, String dateCreate, String timeCreate, int isFavorite, boolean isCompleted, boolean isCanceled ){
        mIndex = index;
        mTask = task;
        mName = name;
        mDescription = description;
        mDateCreate = dateCreate;
        mTimeCreate = timeCreate;
        if(isFavorite == 1)
            mIsFavorite = true;
        mIsCompleted = isCompleted;
        mIsCanceled = isCanceled;

    }

    protected Note(Parcel in) {
        mIndex = in.readInt();
        mTask = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mDateCreate = in.readString();
        mTimeCreate = in.readString();
        mIsFavorite = in.readByte() != 0;
        mIsCompleted = in.readByte() != 0;
        mIsCanceled = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mIndex);
        dest.writeString(mTask);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mDateCreate);
        dest.writeString(mTimeCreate);
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

    public String getTask() {
        return mTask;
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

    public String getTimeCreate() {
        return mTimeCreate;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public boolean isCanceled() {
        return mIsCanceled;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setTask(String task) {
        mTask = task;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setDateCreate(String dateCreate) {
        mDateCreate = dateCreate;
    }

    public void setTimeCreate(String timeCreate) {
        mTimeCreate = timeCreate;
    }


    public void setIsCompleted(boolean isCompleted) {
        mIsCompleted = isCompleted;
    }

    public void setIsCanceled(boolean isCanceled) {
        mIsCanceled = isCanceled;
    }
}
