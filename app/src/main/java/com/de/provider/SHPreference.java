package com.de.provider;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shafi on 5/20/2015.
 */
public class SHPreference {
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;
    //Constants
    private String MOBILE_MESS_PREF = "MOBILE_MESS_PREF";
    private String APP_CODE = "APP_CODE";
    private String USER_NAME = "USER_NAME";

    public SHPreference(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(MOBILE_MESS_PREF, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public void saveAppCode(String appCode) {
        prefsEditor.putString(APP_CODE, appCode).commit();
    }

    public String getAPPCode() {
        return appSharedPrefs.getString(APP_CODE, null);
    }

    public void saveUserName(String userName) {
        prefsEditor.putString(USER_NAME, userName).commit();
    }

    public String getUserName() {
        return appSharedPrefs.getString(USER_NAME, null);
    }
}
