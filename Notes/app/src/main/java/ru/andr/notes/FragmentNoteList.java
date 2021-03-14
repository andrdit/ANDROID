package ru.andr.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class FragmentNoteList extends Fragment {

    private int mCurrentNoteIdx = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_note_list, container, false);
        String[] notes = getResources().getStringArray(R.array.name_note);

        int idx = 0;
        for (String note:notes){
            final int index = idx;
            TextView textViewNote = new TextView(getContext());
            textViewNote.setText(note);
            textViewNote.setTextSize(40);
            textViewNote.setOnClickListener(v -> {
                setCurrentNoteIdx(index);
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                  //  openAnotherActivity(index);
                }else{
                  //  showToRight(index);
                }
            });
            viewGroup.addView(textViewNote);
            idx++;
        }

        return viewGroup;
    }

    private void setCurrentNoteIdx(int noteIdx) {
        mCurrentNoteIdx = noteIdx;
    }
}