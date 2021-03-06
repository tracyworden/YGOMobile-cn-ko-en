package cn.garymb.ygomobile;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatDelegate;

import com.bumptech.glide.Glide;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;

import cn.garymb.ygomobile.lite.R;
import cn.garymb.ygomobile.utils.CrashHandler;

public class App extends GameApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppsSettings.init(this);
        //初始化异常工具类
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        //初始化图片选择器
        initImgsel();
        //初始化bugly
        initBugly();
    }

    @Override
    public NativeInitOptions getNativeInitOptions() {
        NativeInitOptions options = AppsSettings.get().getNativeInitOptions();
        return options;
    }

    @Override
    public float getSmallerSize() {
        return AppsSettings.get().getSmallerSize();
    }

    @Override
    public void attachGame(Activity activity) {
        super.attachGame(activity);
        AppsSettings.get().update(activity);
    }

    @Override
    public float getXScale() {
        return AppsSettings.get().getXScale(getGameWidth(), getGameHeight());
    }

    @Override
    public float getYScale() {
        return AppsSettings.get().getYScale(getGameWidth(), getGameHeight());
    }

    @Override
    public String getCardImagePath() {
        return AppsSettings.get().getCardImagePath();
    }

    @Override
    public String getFontPath() {
        return AppsSettings.get().getFontPath();
    }

    @Override
    public boolean isKeepScale() {
        return AppsSettings.get().isKeepScale();
    }

    @Override
    public void saveSetting(String key, String value) {
        AppsSettings.get().saveSettings(key, value);
    }

    @Override
    public String getSetting(String key) {
        return AppsSettings.get().getSettings(key);
    }

    @Override
    public int getIntSetting(String key, int def) {
        return AppsSettings.get().getIntSettings(key, def);
    }

    @Override
    public void saveIntSetting(String key, int value) {
        AppsSettings.get().saveIntSettings(key, value);
    }

    @Override
    public float getScreenWidth() {
        return AppsSettings.get().getScreenWidth();
    }

    @Override
    public boolean isLockSreenOrientation() {
        return AppsSettings.get().isLockSreenOrientation();
    }

    @Override
    public boolean canNdkCash() {
        return false;
    }

    @Override
    public boolean isImmerSiveMode() {
        return AppsSettings.get().isImmerSiveMode();
    }

    public boolean isSensorRefresh() {
        return AppsSettings.get().isSensorRefresh();
    }

    @Override
    public float getScreenHeight() {
        return AppsSettings.get().getScreenHeight();
    }

    @Override
    public void runWindbot(String args) {
        Intent intent = new Intent();
        intent.putExtra("args", args);
        intent.setAction("RUN_WINDBOT");
        getBaseContext().sendBroadcast(intent);
    }

    private void initImgsel() {
        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
    }

    public void initBugly() {
        Beta.initDelay = 0;
        Beta.showInterruptedStrategy = true;
        Beta.largeIconId = R.drawable.ic_icon_round;
        Beta.defaultBannerId = R.drawable.ic_icon_round;
        Beta.strToastYourAreTheLatestVersion = this.getString(R.string.Already_Lastest);
        Beta.strToastCheckingUpgrade = this.getString(R.string.Checking_Update);
        Beta.upgradeDialogLayoutId = R.layout.dialog_upgrade;
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("BUGLY_APPID");
        Bugly.init(this, msg, true);
    }
}
