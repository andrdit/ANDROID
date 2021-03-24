package ru.andr.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentEditNoteList extends Fragment {

    public static final String ARG_NOTE = "FragmentEditNoteList.note";
    private Note mNote;

    public FragmentEditNoteList() {
        // Required empty public constructor
    }

    public static FragmentEditNoteList newInstance(Note note) {
        FragmentEditNoteList fragment = new FragmentEditNoteList();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
     //   fragment.setRetainInstance(true);
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
        return inflater.inflate(R.layout.fragment_edit_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvNameNote = view.findViewById(R.id.name_note);
        TextView tvCreateDateNote = view.findViewById(R.id.createDate_note);
        TextView tvDescriptionNote = view.findViewById(R.id.description_note);

        tvNameNote.setText(mNote.getName());
        tvCreateDateNote.setText(mNote.getDateCreate());
        tvDescriptionNote.setText(mNote.getDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle){
        super.onSaveInstanceState(bundle);

        bundle.putParcelable(FragmentEditNoteList.ARG_NOTE, mNote);
    }

}