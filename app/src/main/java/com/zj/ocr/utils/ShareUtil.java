package com.zj.ocr.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * create by zj on 2018/12/3
 */
public class ShareUtil {

    private SharedPreferences mPreferences;
    private static ShareUtil sInstance;

    private ShareUtil(Context context, String fileName) {
        mPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public synchronized static ShareUtil init(Context context, String fileName) {
        if (sInstance == null) {
            sInstance = new ShareUtil(context, fileName);
        }
        return sInstance;
    }

    public static ShareUtil getInstance() {
        return sInstance;
    }

    public void putString(String key, String value) {
        mPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return mPreferences.getString(key, defValue);
    }

    public void putInt(String key, int value) {
        mPreferences.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return mPreferences.getInt(key, defValue);
    }

    public void putLong(String key, long value) {
        mPreferences.edit().putLong(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return mPreferences.getLong(key, defValue);
    }

    public void putFloat(String key, float value) {
        mPreferences.edit().putFloat(key, value).apply();
    }

    public float getFloat(String key, float defValue) {
        return mPreferences.getFloat(key, defValue);
    }

    public void putBoolean(String key, boolean value) {
        mPreferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mPreferences.getBoolean(key, defValue);
    }

    public void remove(String key) {
        mPreferences.edit().remove(key).apply();
    }

    public void clear() {
        mPreferences.edit().clear().apply();
    }

}
