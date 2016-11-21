package com.svshizzle.pws.smartfridge.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.svshizzle.pws.smartfridge.R;

/**
 * Created by Arend-Jan on 5-11-2016.
 */

public abstract class SmartfridgeSave {
        public static boolean getSignedin(Context context){
            SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
            return sharedPreferences.getBoolean(context.getString(R.string.SharedPreferencesSignedIn), false);
        }
    public static String getUserId (Context context){

        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        String output =sharedPreferences.getString(context.getString(R.string.SharedPreferencesUserId), "asdrqwerqweerf");
        Log.d("output=", output);
        Log.d("fuck", "als alleen");
        return output;

    }
    public static String getAPIURL ( Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        String output =  sharedPreferences.getString(context.getString(R.string.SharedPreferencesAPIURL), "asdqwerqwerqwerqwerwqe");
        Log.d("output=", output);
        Log.d("fuck", "als alleen");
        return output;
    }
    public static void setSignedin(Context context, boolean bool){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.SharedPreferencesSignedIn), bool);
editor.commit();
    }
    public static void setUserId(Context context, String UID){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesUserId), UID);
        editor.commit();

    }
    public static void setAPIURL(Context context, String APIURL){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesAPIURL), APIURL);
        editor.commit();

    }

    public static void setContainsBackup(Context context, String containsBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesContainsBackup), containsBackup);
        editor.commit();
    }
    public static void setLogBackup(Context context, String logBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesLogBackup), logBackup);
        editor.commit();
    }
    public static String getContainsBackup ( Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        String output =  sharedPreferences.getString(context.getString(R.string.SharedPreferencesContainsBackup), "asdqwerqwerqwerqwerwqe");
        return output;
    }
    public static String getLogBackup ( Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        String output =  sharedPreferences.getString(context.getString(R.string.SharedPreferencesLogBackup), "asdqwerqwerqwerqwerwqe");

        return output;
    }
}
