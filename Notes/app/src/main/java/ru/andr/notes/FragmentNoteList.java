package ru.andr.notes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentNoteList extends Fragment {

    private int mCurrentNoteIdx = -1;
    private Note mCurrentNote = null;
    private ArrayList<Note> mNotes;

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

        RecyclerView recyclerView = viewGroup.findViewById(R.id.recycler_view_lines);

        initRecyclerView(recyclerView, inflater);

        return viewGroup;

    }

    private void initRecyclerView(RecyclerView recyclerView, LayoutInflater inflater) {

        // Эта установка служит для повышения производительности системы
        recyclerView.setHasFixedSize(true);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        ViewHolderAdapter viewHolderAdapter = new ViewHolderAdapter(inflater, mNotes);
        recyclerView.setAdapter(viewHolderAdapter);

        // Установим слушателя
        viewHolderAdapter.setOnClickListener((v, position) -> {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_note_list_container, FragmentDescription.newInstance(mNotes.get(position)));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                showToRight(mNotes.get(position));
            }
        });
    }

    private void setCurrentNote(Note note) {
        mCurrentNote = note;
    }

    private void showToRight(Note note) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_description, FragmentDescription.newInstance(note));
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
        inflater.inflate(R.menu.item_menu_change, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change) {
            Toast.makeText(getContext(), "Change note", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.item_list_note);
        }
    }

    private interface OnClickListener {
        void onItemClick(View v, int position);
    }

    private static class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
        private final LayoutInflater mInflater;
        private ArrayList<Note> mNotes;

        private OnClickListener mOnClickListener;

        public ViewHolderAdapter(LayoutInflater inflater, ArrayList<Note> notes) {
            mInflater = inflater;
            mNotes = notes;
        }

        public void setOnClickListener(OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = mInflater.inflate(R.layout.item_list_note, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.text.setText(mNotes.get(position).getName());
            holder.text.setOnClickListener(v -> {
                if (mOnClickListener != null) {
                    mOnClickListener.onItemClick(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mNotes.size();
        }
    }
}
