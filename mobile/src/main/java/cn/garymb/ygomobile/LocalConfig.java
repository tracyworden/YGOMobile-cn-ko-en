package cn.garymb.ygomobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.File;

import cn.garymb.ygomobile.utils.FileUtils;

public class LocalConfig {
    @SuppressLint("StaticFieldLeak")
    private static LocalConfig sLocalConfig;

    public static LocalConfig getInstance(Context context) {
        if (sLocalConfig == null) {
            synchronized (LocalConfig.class) {
                if (sLocalConfig == null) {
                    sLocalConfig = new LocalConfig(context);
                }
            }
        }
        return sLocalConfig;
    }

    private final File lastdeck;
    private final File lastcategory;

    private LocalConfig(Context context) {
        File dir = context.getDir("ygo", Context.MODE_PRIVATE);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        lastdeck = new File(dir, "lastdeck");
        lastcategory = new File(dir, "lastcategory");
    }

    public String getLastDeck() {
        if (lastdeck.exists()) {
            return FileUtils.readAllString(lastdeck);
        }
        return null;
    }

    public void setLastDeck(String deck) {
        FileUtils.writeAllString(lastdeck, deck);
    }

    public String getLastCategory() {
        if (lastcategory.exists()) {
            return FileUtils.readAllString(lastcategory);
        }
        return null;
    }

    public void setLastCategory(String category) {
        FileUtils.writeAllString(lastcategory, category);
    }

    public void updateFromOld(SharedPreferences sharedPreferences){
        if (sharedPreferences.contains(Constants.PREF_DEF_LAST_YDK)) {
            setLastDeck(sharedPreferences.getString(Constants.PREF_DEF_LAST_YDK, null));
            sharedPreferences.edit().remove(Constants.PREF_DEF_LAST_YDK).apply();
        }
        if (sharedPreferences.contains(Constants.PREF_LAST_CATEGORY)) {
            setLastDeck(sharedPreferences.getString(Constants.PREF_LAST_CATEGORY, null));
            sharedPreferences.edit().remove(Constants.PREF_LAST_CATEGORY).apply();
        }
    }
}