package com.example.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private static final String SETTINGS_PLAYER = "settings_player";
    private static final String SETTINGS_PLAYER_SPEED = "settings_item_speed";
    private static final String SETTINGS_PLAYER_VOLUME = "settings_item_volume";
    private static final String SETTINGS_PLAYER_LANGUAGE = "settings_item_language";

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPutSettings = (Button) findViewById(R.id.btn_put_settings);
        btnPutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putSettingItem(SETTINGS_PLAYER_SPEED, "fast");
                putSettingItem(SETTINGS_PLAYER_VOLUME, "20");
                putSettingItem(SETTINGS_PLAYER_LANGUAGE, "KOR");
            }
        });

        Button btnGetSettings = (Button) findViewById(R.id.btn_get_settings);
        btnGetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String speed = getSettingItem(SETTINGS_PLAYER_SPEED);
                String volume = getSettingItem(SETTINGS_PLAYER_VOLUME);
                String lang = getSettingItem(SETTINGS_PLAYER_LANGUAGE);
                Log.d(TAG, "Speed: " + speed + ", Volume: "+volume + ", Language: " + lang);
            }
        });

        Button btnRemoveSettings = (Button) findViewById(R.id.btn_remove_settings);
        btnRemoveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSettingItem(SETTINGS_PLAYER_SPEED);
                removeSettingItem(SETTINGS_PLAYER_VOLUME);
                removeSettingItem(SETTINGS_PLAYER_LANGUAGE);
            }
        });

        Button btnClearSettings = (Button) findViewById(R.id.btn_clear_settings);
        btnClearSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSettingAll();
            }
        });

        Button btnPutJson = (Button) findViewById(R.id.btn_put_json);
        btnPutJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                list.add("first");
                list.add("second");
                list.add("third");
                list.add("fourth");
                setStringArrayPref(SETTINGS_PLAYER_JSON, list);
                Log.d(TAG, "Put json");
            }
        });

        Button btnGetJson = (Button) findViewById(R.id.btn_get_json);
        btnGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> list = getStringArrayPref(SETTINGS_PLAYER_JSON);
                if (list != null) {
                    for (String value : list) {
                        Log.d(TAG, "Get json : " + value);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void putSettingItem(String key, String value) {
        Log.d(TAG, "Put " + key +" (value : " + value + " ) to " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String getSettingItem(String key) {
        Log.d(TAG, "Get " + key + " from " + SETTINGS_PLAYER);
        return getSharedPreferences(SETTINGS_PLAYER, 0).getString(key, null);
    }

    private void removeSettingItem(String key) {
        Log.d(TAG, "Remove " + key + " from " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.apply();
    }

    private void removeSettingAll() {
        Log.d(TAG, "Clear all TAGs from " + SETTINGS_PLAYER);
        SharedPreferences preferences = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public void setStringArrayPref(String key, ArrayList<String> values) {
        SharedPreferences prefs = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.commit();
    }

    public ArrayList<String> getStringArrayPref(String key) {
        SharedPreferences prefs = getSharedPreferences(SETTINGS_PLAYER, MODE_PRIVATE);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }


}
