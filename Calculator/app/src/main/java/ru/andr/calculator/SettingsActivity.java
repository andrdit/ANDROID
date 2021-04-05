package ru.andr.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private boolean mIsNightMode;
    private SwitchMaterial mSwitchOnOffNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mIsNightMode = getIsNightMode();

        mSwitchOnOffNightMode = findViewById(R.id.mSwitchSettings);

        onOffNightMode(mIsNightMode);

        mSwitchOnOffNightMode.setChecked(mIsNightMode);

        mSwitchOnOffNightMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (getIsNightMode() != isChecked) {
                saveDayNightMode(isChecked);
                mIsNightMode = isChecked;
                recreate();
            }
        });

    }

    private boolean getIsNightMode() {
        // Работаем через специальный класс сохранения и чтения настроек
        SharedPreferences sharedPref = getSharedPreferences(CommonPreferences.NameSharedPreference, MODE_PRIVATE);

        return sharedPref.getBoolean(CommonPreferences.KEY_DAY_NIGHT_MODE, false);
    }

    private void onOffNightMode(boolean isNightMode) {

        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    // Сохранение настроек
    private void saveDayNightMode(boolean isNightMode) {
        SharedPreferences sharedPref = getSharedPreferences(CommonPreferences.NameSharedPreference, MODE_PRIVATE);
        // Настройки сохраняются посредством специального класса editor.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(CommonPreferences.KEY_DAY_NIGHT_MODE, isNightMode);
        editor.apply();
    }


    @Override
    protected void onStop() {
        super.onStop();
        saveDayNightMode(mIsNightMode);
    }
}