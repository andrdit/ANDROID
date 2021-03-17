package ru.andr.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Note mCurrentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, new FragmentNoteList());
        fragmentTransaction.commit();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if(savedInstanceState != null) {
                mCurrentNote = savedInstanceState.getParcelable(FragmentDescription.ARG_NOTE);
                if(mCurrentNote == null)
                    return;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.note_description, FragmentDescription.newInstance(mCurrentNote));
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        Fragment currentFragment = fragmentList.get(fragmentList.size() - 1);
        if(currentFragment instanceof FragmentDescription){
            Note mCurrentNote =  ((FragmentDescription) currentFragment).getmNote();
            outState.putParcelable(FragmentDescription.ARG_NOTE, mCurrentNote);
        }

    }

}