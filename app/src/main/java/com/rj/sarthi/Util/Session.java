package com.rj.sarthi.Util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

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

    public void createLoginSession(String AID,String name,String mno,String pass){
        editor.putBoolean("IS_LOGIN", true);
        editor.putString("uid",AID);
        editor.putString("name",name);
        editor.putString("pass",pass);
        editor.putString("mobileno",mno);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean("IS_LOGIN", false);
    }

    public String getAcId(){
        return pref.getString("uid","");
    }

    public HashMap<String,String> getUserDetail()
    {
        HashMap<String,String> detail=new HashMap<>();
        detail.put("uid",pref.getString("uid",""));
        detail.put("name",pref.getString("name",""));
        detail.put("mobileno",pref.getString("mobileno",""));
        detail.put("pass",pref.getString("pass",""));
        return detail;
    }
}
