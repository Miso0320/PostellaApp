package com.appteam4.postella.datastore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppKeyValueStore {
    private static SharedPreferences sharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("settings", Activity.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void put(Context context, String key, String value) {
        getSharedPreferences(context)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static String getValue(Context context, String key) {
        return getSharedPreferences(context).getString(key, null);
    }

    public static void remove(Context context, String key) {
        getSharedPreferences(context)
                .edit()
                .remove(key)
                .apply();
    }
}
