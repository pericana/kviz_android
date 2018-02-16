package hr.math.kviz;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferenceHandler {

    private static final int MODE = MODE_PRIVATE;
    private static final String NAME = "kviz";

    public static void saveInt(Context context, String key, int value){
        SharedPreferences mySharedPreferences=context.getSharedPreferences(NAME, MODE);
        SharedPreferences.Editor editor=mySharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void saveBoolean(Context context, String key, boolean value){
        SharedPreferences mySharedPreferences=context.getSharedPreferences(NAME, MODE);
        SharedPreferences.Editor editor=mySharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key){
        SharedPreferences mySharedPreferences=context.getSharedPreferences(NAME, MODE);
        return mySharedPreferences.getInt(key, 0);
    }

    public static boolean getBoolean(Context context, String key){
        SharedPreferences mySharedPreferences=context.getSharedPreferences(NAME, MODE);
        return mySharedPreferences.getBoolean(key, false);
    }

}
