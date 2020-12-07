package com.realitymine.uiautomatortest.reallifeuitester;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.realitymine.uiautomatortest.reallifeuitester.util.ScreenshotTakerKt;
import com.realitymine.uiautomatortest.reallifeuitester.util.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.support.test.rule.GrantPermissionRule;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class AwsDeviceFarmTest {
    private UiDevice mDevice;
    private Context mContext;
    private TestUtils mTestUtils;

    // This is required to grant the test app permission to write screenshots
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE);


    @Before
    public void testBeforeClass() throws IOException {
        // Set stuff up before each test
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mTestUtils = new TestUtils(mContext, mDevice);

        mDevice.executeShellCommand(
                "pm grant net.sanapeli.adbchangelanguage android.permission.CHANGE_CONFIGURATION");
    }

    @Test
    public void basicTest() throws InterruptedException {
        mDevice.pressHome();
        Thread.sleep(5000);

        mDevice.openQuickSettings();
        Thread.sleep(5000);

        mDevice.pressHome();
        mDevice.openNotification();
        Thread.sleep(5000);
    }

    @Test
    public void switchLanguages() throws InterruptedException {
        ScreenshotTakerKt.takeScreenshot("", "en");
        mTestUtils.switchDeviceLanguage("fr");

        mDevice.pressHome();
        mDevice.openNotification();

        Thread.sleep(5000);

        ScreenshotTakerKt.takeScreenshot("", "fr");

        mTestUtils.switchDeviceLanguage("es");

        mDevice.pressHome();
        mDevice.openNotification();

        Thread.sleep(5000);

        ScreenshotTakerKt.takeScreenshot("", "es");

        mTestUtils.switchDeviceLanguage("en");
    }

}
