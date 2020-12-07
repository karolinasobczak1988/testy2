package com.realitymine.uiautomatortest.reallifeuitester.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import java.util.List;
import java.util.Locale;

public class TestUtils {
    private int UI_TIMEOUT_MS = 5000;
    private Context mContext;
    UiDevice mDevice;

    public TestUtils(Context context, UiDevice device) {
        mContext = context;
        mDevice = device;
    }

    public void uninstallIfInstalled(String packageName)
            throws InterruptedException {
        if (isPackageInstalled(packageName)) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + packageName));
            mContext.startActivity(intent);

            // Wait for uninstall dialog
            mDevice.wait(Until.hasObject(By.pkg("com.google.android.packageinstaller").depth(0)), UI_TIMEOUT_MS);

            // Press OK
            mDevice.findObject(By.res("android:id/button1")).click();

            // Wait to be uninstalled
            for (int i = 0;i < 60;i++) {
                if (!isPackageInstalled(packageName)) {
                    break;
                }
                Thread.sleep(1000);
            }

            // Give it a bit longer
            Thread.sleep(5000);
        }
    }

    private boolean isPackageInstalled(String packageName)
    {
        List<ApplicationInfo> packages;
        PackageManager pm;

        pm = mContext.getPackageManager();
        packages = pm.getInstalledApplications(0);
        for (ApplicationInfo packageInfo : packages) {
            if(packageInfo.packageName.equals(packageName))
                return true;
        }
        return false;
    }

    public void installFromPlayStore(String packageName)
            throws InterruptedException {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        // Find INSTALL button within details panel (this doesn't have any view ID and the text can be localized)
        // A better method might be to wait for just one button (Installed state has two: OPEN + UNINSTALL)
        mDevice.wait(Until.hasObject(By.res("com.android.vending:id/item_details_panel")), UI_TIMEOUT_MS);
        waitForAndFindObject(By.text("INSTALL"), 30 * 1000) // The Play Store UI can take a long time update
                .click();

        // Accept install prompt, if displayed
        UiObject2 confButton = waitForAndFindObject(By.res("com.android.vending:id/continue_button"), UI_TIMEOUT_MS);
        if (confButton != null) {
            confButton.click();
        }

        // Wait a good time for installed (5 mins)
        for (int i = 0; i < 300; i++) {
            if (isPackageInstalled(packageName)) {
                break;
            }
            Thread.sleep(1000);
        }

        // Give it a bit longer
        Thread.sleep(5000);
    }

    public UiObject2 waitForAndFindObject(BySelector selector, int timeout) {
        mDevice.wait(Until.hasObject(selector), timeout);
        return mDevice.findObject(selector);
    }

    public void launchApp(String packageName) {
        Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (launchIntent != null) {
            mContext.startActivity(launchIntent);
        }
    }


    // Pre-req: we are on the languages setting screen
    // Delete all except the first item
    public void removeAllLanguagesExceptDefault() throws InterruptedException {
        removeAllLanguagesExcept(0, null);
    }

    // Pre-req: we are on the languages setting screen
    // Delete all except the one starting witht the passed-in langName
    public void removeAllLanguagesExceptLangName(String langFullName) throws InterruptedException {
        removeAllLanguagesExcept(-1, langFullName);
    }

    private void removeAllLanguagesExcept(int index, String langFullName) throws InterruptedException {
        // Wait for list of languages
        UiObject2 dragList = waitForAndFindObject(By.res("com.android.settings:id/dragList"), UI_TIMEOUT_MS);
        List<UiObject2> langLabels = dragList.findObjects(By.res("com.android.settings:id/label"));

        // If we have multiple languages, do a clean up now and remove all but the one we want to keep
        if (langLabels.size() > 1) {
            // Click "DELETE" button, if it exists (e.g. Samsung S7). Otherwise hit delete menu
            UiObject2 deleteButton = waitForAndFindObject(By.clazz("android.widget.Button"), UI_TIMEOUT_MS);
            if (deleteButton != null && stringIsNullOrEmpty(deleteButton.getResourceName())) {
                deleteButton.click();
            }
            else {
                waitForAndFindObject(By.clazz("android.widget.ImageButton"), UI_TIMEOUT_MS).click();
                // Wait a little bit to ensure menu visible
                Thread.sleep(2000);
                waitForAndFindObject(By.res("android:id/title"), UI_TIMEOUT_MS).click();
            }

            // Wait a little bit to ensure the selection buttons are visible
            Thread.sleep(2000);

            // Regrab the labels
            dragList = waitForAndFindObject(By.res("com.android.settings:id/dragList"), UI_TIMEOUT_MS);

            // Samsung S7 uses id/label when in delete mode
            langLabels = dragList.findObjects(By.res("com.android.settings:id/label"));
            if (langLabels.isEmpty()) {
                // Nexus 5X uses com.android.settings:id/checkbox when in delete mode
                langLabels = dragList.findObjects(By.res("com.android.settings:id/checkbox"));
            }

            for (int i = 0;i < langLabels.size();i++) {
                if (langFullName != null) {
                    if (!langLabels.get(i).getText().startsWith(langFullName)) {
                        // Select item to delete
                        langLabels.get(i).click();
                    }
                }
                else {
                    if (i != index) {
                        // Select item to delete
                        langLabels.get(i).click();
                    }
                }
            }

            // Hit the delete button again
            deleteButton = waitForAndFindObject(By.clazz("android.widget.Button"), UI_TIMEOUT_MS);
            if (deleteButton != null) {
                deleteButton.click();
            }
            else {
                deleteButton = waitForAndFindObject(By.clazz("android.widget.TextView").clickable(true), UI_TIMEOUT_MS);
                deleteButton.click();
            }

            // Hit OK to confirm
            waitForAndFindObject(By.res("android:id/button1"), UI_TIMEOUT_MS).click();
        }

    }

    private static boolean stringIsNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.isEmpty()) {
            return true;
        }
        return false;
    }

    // Pre-requisite: https://play.google.com/store/apps/details?id=net.sanapeli.adbchangelanguage app
    // installed and granted permission (see Play Store description)
    // Example languages "en", "en-US", "fr-CA"
    public void switchDeviceLanguage(String language) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("net.sanapeli.adbchangelanguage",
                "net.sanapeli.adbchangelanguage.AdbChangeLanguage"));

        // App needs language in format "<lang>[-r<region>]" where region part is optional
        intent.putExtra("language", language.replace("-", "-r"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        // Leave some time to take effect
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
    }

    public Locale getCurrentLocale(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return mContext.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return mContext.getResources().getConfiguration().locale;
        }
    }
}
