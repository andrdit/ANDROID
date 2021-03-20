package ru.andr.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    // регистрация drawer
    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // Обработка навигационного меню
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
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

        if (navigateFragment(id)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean navigateFragment(int id) {
        switch (id) {
            case R.id.action_settings:
                //addFragment(new SettingsFragment());
                Toast.makeText(MainActivity.this, "Go to settings fragment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                //addFragment(new MainFragment());
                Toast.makeText(MainActivity.this, "Go to favorite fragment notes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_add:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "Add note", Toast.LENGTH_SHORT).show();
            case R.id.action_delete:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "Delete note", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "About application", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

}
