package com.rj.sarthi.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "NgoFood";

    public Session(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String AID){
        editor.putBoolean("IS_LOGIN", true);
        editor.putString("uid",AID);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean("IS_LOGIN", false);
    }

    public String getAcId(){
        return pref.getString("uid","");
    }
}
