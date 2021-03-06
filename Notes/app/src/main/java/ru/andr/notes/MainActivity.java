package ru.andr.notes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
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
        addFragment(savedInstanceState, new FragmentNoteList());


    }

    private void addFragment(Bundle savedInstanceState, Fragment fragment) {
        //Получить менеджер фрагментов
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Открыть транзакцию
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_note_list_container, fragment);
        fragmentTransaction.commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (savedInstanceState != null) {
                mCurrentNote = savedInstanceState.getParcelable(FragmentDescription.ARG_NOTE);
                if (mCurrentNote == null)
                    return;
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_description, FragmentDescription.newInstance(mCurrentNote));
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
                if (navigateDrawer(id)) {
                    drawer.closeDrawer(GravityCompat.START);
                    return true;
                }
                return false;
            }
        });
    }

    private boolean navigateDrawer(int id) {
        switch (id) {
            case R.id.action_settings:
                //addFragment(new SettingsFragment());
                Toast.makeText(MainActivity.this, "Go to settings fragment", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_favorite:
                //addFragment(new MainFragment());
                Toast.makeText(MainActivity.this, "Go to favorite fragment notes", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                //addFragment(new FavoriteFragment());
                Toast.makeText(MainActivity.this, "About application", Toast.LENGTH_SHORT).show();
                return true;
        }
           return false;
    }

}
