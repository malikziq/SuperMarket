package com.example.supermarket.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.supermarket.models.pojo.User;

public class PreferencesManager {
    private static final String TAG = "PreferencesManager";

    // KEY VALUES
    public static final String USER_ID = "userId";
    public static final String USER_EMAIL = "userEmail";



    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;

    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
        return sInstance;
    }

    // User Id
    public void setUserId(int userId){
        mPref.edit().putInt(USER_ID, userId).apply();
    }

    public int getUserId(){
        return mPref.getInt(USER_ID, 1);
    }

    // User Email
    public void setUserEmail(String email){
        mPref.edit().putString(USER_EMAIL, email).apply();
    }

    public String getUserEmail(){
        return mPref.getString(USER_EMAIL, "");
    }
}
