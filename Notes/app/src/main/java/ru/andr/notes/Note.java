package ru.andr.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String mName;
    private String mDescription;
    private String mDateCreate;

    public Note(String name, String description, String dateCreate ){
        mName = name;
        mDescription = description;
        mDateCreate = dateCreate;
    }

    protected Note(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mDateCreate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mDateCreate);
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
}
