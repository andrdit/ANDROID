package ru.andr.notes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FragmentDescription extends Fragment {

    public static final String ARG_NOTE = "FragmentDescription.note";

    private Note mNote;

    public FragmentDescription() {
        // Required empty public constructor
    }

    public static FragmentDescription newInstance(Note note) {
        FragmentDescription fragment = new FragmentDescription();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
    //    fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNote = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvNameNote = view.findViewById(R.id.name_note);
        TextView tvCreateDateNote = view.findViewById(R.id.createDate_note);
        TextView tvDescriptionNote = view.findViewById(R.id.description_note);

        tvNameNote.setText(mNote.getmName());
        tvCreateDateNote.setText(mNote.getmDateCreate());
        tvDescriptionNote.setText(mNote.getmDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putParcelable(FragmentDescription.ARG_NOTE, mNote);
    }

    public interface changeStateOfFragment{
        void saveInstanceOfFragment(Note note);
    }

    public Note getmNote(){
        return mNote;
    }


}