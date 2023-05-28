package org.koishi.launcher.h2co3.application;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import org.koishi.launcher.h2co3.R;

public class H2CO3Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //这里做你想做的事
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            boolean spIsAuth = sharedPreferences.getBoolean("material_you", true);
            if (spIsAuth) {
                setTheme(R.style.Theme_Boat_H2CO3_DynamicColors);
            } else {
                setTheme(R.style.Theme_Boat_H2CO3);
            }
        } else {
            setTheme(R.style.Theme_Boat_H2CO3);
        }

    }
}
