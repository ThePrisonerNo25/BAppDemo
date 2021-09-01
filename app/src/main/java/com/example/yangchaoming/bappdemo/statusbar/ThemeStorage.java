package com.example.yangchaoming.bappdemo.statusbar;

import android.content.Context;
import android.content.SharedPreferences;

class ThemeStorage {
    public static void setThemeColor(Context context, String themeColor){
        SharedPreferences sharedpreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("theme", themeColor);
        editor.apply();
    }
    public static String getThemeColor(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("theme_data", Context.MODE_PRIVATE);
        return sharedpreferences.getString("theme", "AppTheme");
    }
}
