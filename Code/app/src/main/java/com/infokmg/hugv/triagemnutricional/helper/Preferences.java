package com.infokmg.hugv.triagemnutricional.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by uealuduslab on 31/07/2018.
 */

public class Preferences {

    private Context context;
    private SharedPreferences preferences;
    private String NAME_FILE = "TriagemNutricional.preferences";
    private int MODE = 0;
    private SharedPreferences.Editor editor;

    private final String KEY_IDENTIFIER = "identifierLoggedUser";
    private final String KEY_NAME = "nameLoggedUser";

    public Preferences(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(NAME_FILE, MODE);

        editor = preferences.edit();
    }

    public void saveUserPreferences(String identifierUser, String nameUser){
        editor.putString(KEY_IDENTIFIER, identifierUser);
        editor.putString(KEY_NAME, nameUser);
        editor.commit();
    }

    public String getIdentifier(){
        return preferences.getString(KEY_IDENTIFIER, null);
    }

    public String getName(){
        return preferences.getString(KEY_NAME, null);
    }
}

