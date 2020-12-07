package com.realitymine.uiautomatortest.reallifeuitester;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PermissionsTest {

    String TAG = "reallifeuitester.NetflixTest";

    @Test
    public void testUninstalling() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getContext();

        // Trigger uninstall of Moron Test
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:com.distinctdev.tmtlite"));
        context.startActivity(intent);

        // Wait for uninstall dialog
        int uiTimeout = 5000;
        device.wait(Until.hasObject(By.pkg("com.google.android.packageinstaller").depth(0)), uiTimeout);

        // Press OK
        device.findObject(By.res("android:id/button1")).click();

    }

    @Test
    public void testAllowPermission() {
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getContext();

        // We don't trigger any permission here - just wait for a permission dialog to be present

        // Wait for  dialog
        int uiTimeout = 5000;
        device.wait(Until.hasObject(By.res("com.android.packageinstaller:id/permission_allow_button")), uiTimeout);

        // Press OK
        device.findObject(By.res("com.android.packageinstaller:id/permission_allow_button")).click();

    }

}
