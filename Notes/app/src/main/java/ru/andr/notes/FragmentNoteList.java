package ru.andr.notes;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentNoteList extends Fragment {

    //private int mCurrentNoteIdx = -1;
    //private Note mCurrentNote = null;
    //private ArrayList<Note> mNotes;
    private ViewHolderAdapter mViewHolderAdapter;
    private NotesSourceImpl mNoteSourceImpl;
    private RecyclerView mRecyclerView;
    private int mLastSelectedItemNoteList = -1;

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

        mRecyclerView = viewGroup.findViewById(R.id.recycler_view_lines);

        initRecyclerView(inflater);

        return viewGroup;

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initRecyclerView(LayoutInflater inflater) {

        // Эта установка служит для повышения производительности системы
        mRecyclerView.setHasFixedSize(true);

        //добавим разделитель
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.decoration_item_list_notes_divider));
        mRecyclerView.addItemDecoration(divider);

        // Будем работать со встроенным менеджером
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);

        // Установим адаптер
        mNoteSourceImpl = NotesSourceImpl.getInstance(getResources()); //new NotesSourceImpl(getResources()); - переделали на синглтон !!!
        mViewHolderAdapter = new ViewHolderAdapter(this, mNoteSourceImpl);
        mRecyclerView.setAdapter(mViewHolderAdapter);

        // Установим слушателя
        mViewHolderAdapter.setOnClickListener((v, note) -> {
            if (v.getId() == R.id.item_list_is_favorite) {
                note.setIsFavorite(!note.isFavorite());
                mViewHolderAdapter.notifyDataSetChanged();
                return;
            }
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_note_list_container, FragmentDescription.newInstance(note));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
            } else {
                showToRight(note);
            }
        });
    }

//    private void setCurrentNote(Note note) {
//        mCurrentNote = note;
//    }

    private void showToRight(Note note) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_description, FragmentDescription.newInstance(note));
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        // bundle.putParcelable(FragmentDescription.ARG_NOTE, mNotes);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);

//        if (bundle != null) {
//            mCurrentNote = bundle.getParcelable(FragmentDescription.ARG_NOTE);
//            if (mCurrentNote != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                showToRight(mCurrentNote);
//            }
//
//        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.context_menu_note_list, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        if (FragmentNoteListItemSelected(id)) {
            return true;
        }
        else {
            return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // //добавим пункты меню из фрагмента в ToolBar
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        if (FragmentNoteListItemSelected(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean FragmentNoteListItemSelected(int id) {
        switch (id) {
            case R.id.action_settings:
                //addFragment(new SettingsFragment());
                Toast.makeText(getContext(), "Go to settings fragment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                //addFragment(new MainFragment());
                Toast.makeText(getContext(), "Go to favorite fragment notes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                int position = mNoteSourceImpl.getItemCount();
                int indexForName = position + 1;
                mNoteSourceImpl.add
                        (new Note(position, "Note", "I am new, edit me!!! (Note " + indexForName + ")", "need edit",
                                "undefined", "undefined", 0, false, false));
                mViewHolderAdapter.notifyItemInserted(position);
                mRecyclerView.scrollToPosition(position);
                return true;
            case R.id.action_clear_list:
//                mViewHolderAdapter.mNoteSource.clearAll(); // это тоже работает, когда адаптер статичный вложенный класс - такое правильно делать?
                mNoteSourceImpl.clearAll();
                mViewHolderAdapter.notifyDataSetChanged();
                return true;
            case  R.id.action_edit_context_menu:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.fragment_note_list_container, FragmentEditNoteList.newInstance(mNoteSourceImpl.getItemAt(mLastSelectedItemNoteList)));
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    //showToRight(note);
                }
                break;
            case  R.id.action_delete_context_menu:
                mNoteSourceImpl.remove(mLastSelectedItemNoteList);
                //mViewHolderAdapter.notifyDataSetChanged();
                mViewHolderAdapter.notifyItemRemoved(mLastSelectedItemNoteList);
                break;
        }
        return false;
    }

    public interface OnClickListener {
        void onItemClick(View v, Note note);
    }

    void setLastSelectedItemNoteList(int current){
        mLastSelectedItemNoteList = current;
    }


}
