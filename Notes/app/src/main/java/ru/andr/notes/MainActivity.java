package ru.andr.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Note mCurrentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //readSettings();
        initView();

        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments_container, new FragmentNoteList());
        fragmentTransaction.commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (savedInstanceState != null) {
                mCurrentNote = savedInstanceState.getParcelable(FragmentDescription.ARG_NOTE);
                if (mCurrentNote == null)
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> fragmentList = fragmentManager.getFragments();
        Fragment currentFragment = fragmentList.get(fragmentList.size() - 1);
        if (currentFragment instanceof FragmentDescription) {
            Note mCurrentNote = ((FragmentDescription) currentFragment).getmNote();
            outState.putParcelable(FragmentDescription.ARG_NOTE, mCurrentNote);
        }

    }

    private void initView() {
        initToolbar();
//        initButtonMain();
//        initButtonFavorite();
//        initButtonSettings();
//        initButtonBack();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Здесь определяем меню приложения (активити)
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.action_search); // поиск пункта меню поиска
        SearchView searchText = (SearchView) search.getActionView(); // строка поиска
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            // реагирует на конец ввода поиска
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                return true;
            }

            // реагирует на нажатие каждой клавиши
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                //addFragment(new SettingsFragment());
                Toast.makeText(MainActivity.this, "Go to settings fragment", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_favorite:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "Go to favorite fragment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "Add note", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "Delete note", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
