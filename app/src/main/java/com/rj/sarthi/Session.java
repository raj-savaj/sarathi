package com.rj.sarthi;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    public Session(Context context) {
        this.context = context;
        this.context = context;
        pref = context.getSharedPreferences("AV", PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setCheckApp(){
        editor.putBoolean("APP",true);
        editor.commit();
    }
    public boolean CheckApp(){
        return pref.getBoolean("APP",false);
    }
}
