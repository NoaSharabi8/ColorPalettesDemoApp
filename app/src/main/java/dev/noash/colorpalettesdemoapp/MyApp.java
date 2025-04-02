package dev.noash.colorpalettesdemoapp;

import android.app.Application;

public class MyApp extends Application {
    private static String selectedTheme;
    @Override
    public void onCreate() {
        super.onCreate();
    }
    public static String getSelectedTheme() {
        return selectedTheme;
    }
    public static void setSelectedTheme(String newTheme) {
        selectedTheme = newTheme;
    }


}
