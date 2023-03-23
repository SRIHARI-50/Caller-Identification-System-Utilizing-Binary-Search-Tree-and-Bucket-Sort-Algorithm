package com.example.miniproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference
{
    static final String PREF_USER_NAME = "username";
    static final String PREF_KEEP = "1";

    static final String PREF_FIRST = "1";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName, String keep)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.putString(PREF_KEEP, keep);
        editor.apply();
    }

    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getKeep(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_KEEP, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }

    public static void setFirst(Context ctx,String fir)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FIRST, fir);
        editor.apply();
    }

    public static String getFirst(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_FIRST, "");
    }

}
