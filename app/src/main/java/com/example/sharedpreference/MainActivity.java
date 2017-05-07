package com.example.sharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String SETTINGS_PLAYER = "settings_player";
    private static final String SETTINGS_ITEM_SPEED = "settings_item_speed";
    private static final String SETTINGS_ITEM_VOLUME = "settings_item_volume";
    private static final String SETTINGS_ITEM_LANGUAGE = "settings_item_language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPutSettings = (Button) findViewById(R.id.btn_put_settings);
        btnPutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putSettingItem(SETTINGS_ITEM_SPEED, "fast");
                putSettingItem(SETTINGS_ITEM_VOLUME, "20");
                putSettingItem(SETTINGS_ITEM_LANGUAGE, "KOR");
            }
        });

        Button btnGetSettings = (Button) findViewById(R.id.btn_get_settings);
        btnGetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speed = getSettingItem(SETTINGS_ITEM_SPEED);
                String volume = getSettingItem(SETTINGS_ITEM_VOLUME);
                String lang = getSettingItem(SETTINGS_ITEM_LANGUAGE);
                Log.d(TAG, "Speed: " + speed + ", Volume: "+volume + ", Language: " + lang);
            }
        });

        Button btnRemoveSettings = (Button) findViewById(R.id.btn_remove_settings);
        btnRemoveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSettingItem(SETTINGS_ITEM_SPEED);
                removeSettingItem(SETTINGS_ITEM_VOLUME);
                removeSettingItem(SETTINGS_ITEM_LANGUAGE);
            }
        });

        Button btnClearSettings = (Button) findViewById(R.id.btn_clear_settings);
        btnClearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSettingAll();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void putSettingItem(String item, String value) {
        Log.d(TAG, "Put " + item +" (value : " + value + " ) to " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(item, value);
        editor.apply();
    }

    private String getSettingItem(String item) {
        Log.d(TAG, "Get " + item + " from " + SETTINGS_PLAYER);
        return getSharedPreferences(SETTINGS_PLAYER, 0).getString(item, null);
    }

    private void removeSettingItem(String item) {
        Log.d(TAG, "Remove " + item + " from " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(item);
        editor.apply();
    }

    private void removeSettingAll() {
        Log.d(TAG, "Clear all TAGs from " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
}
