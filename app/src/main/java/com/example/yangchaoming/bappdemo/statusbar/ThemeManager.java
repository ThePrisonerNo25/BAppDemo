package com.example.yangchaoming.bappdemo.statusbar;

import android.content.Context;

import com.example.yangchaoming.bappdemo.R;

class ThemeManager {
    public static void setCustomizedThemes(Context context, String theme){
        switch (theme){
            case "GreenLightTheme":
                context.setTheme(R.style.GreenLightTheme);
                break;
            case "GreenDarkTheme":
                context.setTheme(R.style.GreenDarkTheme);
                break;
            case "BlueDarkTheme":
                context.setTheme(R.style.BlueDarkTheme);
                break;
//            case "purple":
//                context.setTheme(R.style.Theme3);
//                break;
//            case "green":
//                context.setTheme(R.style.Theme4);
//                break;
//            case "grey":
//                context.setTheme(R.style.Theme5);
//                break;
//            case "orange":
//                context.setTheme(R.style.Theme6);
//                break;
//            case "pink":
//                context.setTheme(R.style.Theme7);
//                break;
        }
    }
}
