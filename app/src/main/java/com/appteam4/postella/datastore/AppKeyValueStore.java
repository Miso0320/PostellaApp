package com.appteam4.postella.datastore;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    public static void addRecentSearchKeyword(Context context, String keyword) {
        // 기존 검색어 리스트를 불러옴
        List<String> recentSearchKeywords = getRecentSearchKeywords(context);

        // 새로운 검색어를 리스트에 추가
        recentSearchKeywords.add(keyword);

        // 최대 저장할 검색어 개수를 제한할 경우, 리스트의 크기를 조절
        int maxSearchKeywords = 10; // 예를 들어, 최대 10개의 검색어만 저장하도록 설정
        if (recentSearchKeywords.size() > maxSearchKeywords) {
            recentSearchKeywords.subList(0, recentSearchKeywords.size() - maxSearchKeywords).clear();
        }

        // 검색어 리스트를 SharedPreferences에 저장
        putRecentSearchKeywords(context, recentSearchKeywords);
    }

    public static void putRecentSearchKeywords(Context context, List<String> keywords) {
        Gson gson = new Gson();
        String json = gson.toJson(keywords);

        getSharedPreferences(context)
                .edit()
                .putString("recent_search_keywords", json)
                .apply();
    }

    public static List<String> getRecentSearchKeywords(Context context) {
        String json = getSharedPreferences(context).getString("recent_search_keywords", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(json, type);
        } else {
            return new ArrayList<>(); // 기본적으로 빈 리스트 반환
        }
    }
}
