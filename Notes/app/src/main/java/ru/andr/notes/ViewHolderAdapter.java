package ru.andr.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final FragmentNoteList mFragment;
    private final LayoutInflater mInflater;
    private NotesSource mNoteSource;

    private FragmentNoteList.OnClickListener mOnClickListener;

    public ViewHolderAdapter(FragmentNoteList fragment, NotesSource notesSource) {
        mFragment = fragment;
        mInflater = mFragment.getLayoutInflater();
        mNoteSource = notesSource;
    }

    public void setOnClickListener(FragmentNoteList.OnClickListener onClickListener) {
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

        holder.populate(mFragment, mNoteSource, position);
        holder.textName.setOnClickListener(v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, mNoteSource.getItemAt(position));
            }
        });

        holder.mIsFavoriteStatus.setOnClickListener(v -> {
            if (mOnClickListener != null) {
                mOnClickListener.onItemClick(v, mNoteSource.getItemAt(position));
            }
        });

        //mFragment.registerForContextMenu(holder.itemView);
        // Обработчик нажатий на картинке
//        holder.textName.setOnLongClickListener(v -> {
//                v.showContextMenu();
//                return true;
//        });
    }

    @Override
    public int getItemCount() {
        return mNoteSource.getItemCount();
    }

}
