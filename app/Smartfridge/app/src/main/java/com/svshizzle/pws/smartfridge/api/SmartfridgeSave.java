package com.svshizzle.pws.smartfridge.api;

import android.content.Context;
import android.content.SharedPreferences;


import com.svshizzle.pws.smartfridge.R;

/**
 * Created by Arend-Jan on 5-11-2016.
 */

public abstract class SmartfridgeSave {
        static boolean getSignedin(Context context){
            SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
            return sharedPreferences.getBoolean(context.getString(R.string.SharedPreferencesSignedIn), false);
        }
    static String getUserId(Context context){

        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesUserId), "");

    }
    static String getAPIURL(Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesAPIURL), "");
    }
    static void setSignedin(Context context, boolean bool){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.SharedPreferencesSignedIn), bool);
editor.apply();
    }
    static void setUserId(Context context, String UID){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesUserId), UID);
        editor.apply();

    }
    static void setAPIURL(Context context, String APIURL){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesAPIURL), APIURL);
        editor.apply();

    }

    static void setContainsBackup(Context context, String containsBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesContainsBackup), containsBackup);
        editor.apply();
    }
    static void setLogASCBackup(Context context, String logBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesLogBackupASC), logBackup);
        editor.apply();
    }
    static void setLogDESCBackup(Context context, String logBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesLogBackupDESC), logBackup);
        editor.apply();
    }
    public static String getContainsBackup ( Context context){

        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesContainsBackup), "");
    }
    public static String getLogASCBackup ( Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);

        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesLogBackupASC), "");
    }
    public static String getLogDESCBackup ( Context context){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);

        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesLogBackupDESC), "");
    }

    static void setActiveBackup(Context context, String logBackup){
        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.SharedPreferencesActiveBackup), logBackup);
        editor.apply();
    }
    public static String getActiveBackup ( Context context){

        SharedPreferences sharedPreferences =  context.getSharedPreferences(context.getResources().getString(R.string.SharedPreferencesName), 0);
        return sharedPreferences.getString(context.getString(R.string.SharedPreferencesActiveBackup), "");
    }

}
