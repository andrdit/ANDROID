package ru.andr.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FragmentNoteList extends Fragment {

    private int mCurrentNoteIdx = -1;
    private Note mCurrentNote = null;
    private List<Note> mNotes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_note_list, container, false);

        setHasOptionsMenu(true);

        int[] indexNote = getResources().getIntArray(R.array.index_note);
        String[] nameNote = getResources().getStringArray(R.array.name_note);
        String[] descriptionNote = getResources().getStringArray(R.array.description_note);
        String[] createDateNote = getResources().getStringArray(R.array.createDate_note);

        initNotes(indexNote, nameNote, descriptionNote, createDateNote);

        for (Note note : mNotes) {
            TextView textViewNote = new TextView(getContext());
            textViewNote.setText(note.getmName());
            textViewNote.setTextSize(40);
            textViewNote.setOnClickListener(v -> {
                setCurrentNote(note);
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragments_container, FragmentDescription.newInstance(note));
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    showToRight(note);
                }
            });
            viewGroup.addView(textViewNote);
        }

        return viewGroup;
    }

    private void setCurrentNote(Note note) {
        mCurrentNote = note;
    }

    private void showToRight(Note note) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.note_description, FragmentDescription.newInstance(note));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initNotes(int[] indexNote, String[] nameNote, String[] descriptionNote, String[] createDateNote) {
        mNotes = new ArrayList();

        for (int index : indexNote) {
            Note note = new Note(index, nameNote[index], descriptionNote[index], createDateNote[index]);
            mNotes.add(note);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        bundle.putParcelable(FragmentDescription.ARG_NOTE, mCurrentNote);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);

        if (bundle != null) {
            mCurrentNote = bundle.getParcelable(FragmentDescription.ARG_NOTE);
            if (mCurrentNote != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                showToRight(mCurrentNote);
            }

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //добавим пункт меню из фрагмента в ToolBar
        inflater.inflate(R.menu.main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change) {
            Toast.makeText(getContext(), "Change note", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
