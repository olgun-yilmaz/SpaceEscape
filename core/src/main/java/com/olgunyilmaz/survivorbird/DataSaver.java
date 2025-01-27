package com.olgunyilmaz.survivorbird;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class DataSaver {
    public Preferences preferences;
    private static final String KEY = "highScore";
    private static final String PREF_NAME = "SpaceEscapePrefs";

    public DataSaver(){
        preferences = Gdx.app.getPreferences(PREF_NAME);
    }

    public void saveData(int highScore){
        preferences.putInteger(KEY,highScore);
        preferences.flush();
    }

    public int fetchData(){
        return preferences.getInteger(KEY,0);
    }



}
