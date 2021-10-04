package com.example.tms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";
    private static final String KEY_UNAME = "keyuname";
    private static final String KEY_FNAME = "keyfname";
    private static final String KEY_SID = "keysid";
    private static final String KEY_PASS = "keypass";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_SECTION = "keysection";
    private static final String KEY_ROLE = "keyrole";
    private static final String KEY_PICKUP = "keypick";
    private static final String KEY_CODENAME = "keycodename";
    private static final String KEY_DESIGNATION = "keydesignation";
    private static final String KEY_DEPT = "keydept";
    private static final String KEY_ROUTE = "keyroute";
    private static final String KEY_BATCH = "keybatch";
    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }
    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will store the user data in shared preferences
    public void userLogin(user user) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SID, user.getSid());
        editor.putString(KEY_UNAME, user.getUname());
        editor.putString(KEY_FNAME, user.getFname());
        editor.putString(KEY_PASS, user.getPass());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_DEPT, user.getDept());
        editor.putString(KEY_PICKUP, user.getPick());
        editor.putString(KEY_SECTION, user.getSection());
        editor.putString(KEY_BATCH, user.getBatch());
        editor.putString(KEY_ROLE, user.getRole());
        editor.putString(KEY_CODENAME, user.getCodename());
        editor.putString(KEY_DESIGNATION, user.getDesignation());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(KEY_UNAME, null)!=null;
    }

    public user getUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new user(
                sharedPreferences.getString(KEY_SID, null),
                sharedPreferences.getString(KEY_UNAME, null),
                sharedPreferences.getString(KEY_FNAME, null),
                sharedPreferences.getString(KEY_PASS, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_DEPT, null),
                sharedPreferences.getString(KEY_PICKUP, null),
                sharedPreferences.getString(KEY_SECTION, null),
                sharedPreferences.getString(KEY_BATCH, null),
                sharedPreferences.getString(KEY_ROLE, null),
                sharedPreferences.getString(KEY_CODENAME, null),
                sharedPreferences.getString(KEY_DESIGNATION, null)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, LoginActivity.class));
    }
}
