package com.realitymine.uiautomatortest.reallifeuitester;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.realitymine.uiautomatortest.reallifeuitester.util.TestOutputTestWatcher;
import com.realitymine.uiautomatortest.reallifeuitester.util.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.lang.Thread.sleep;

@RunWith(AndroidJUnit4.class)
public class ChromeTest {
    String TAG = "reallifeuitester.ChromeTest";

    private UiDevice mDevice;
    private Context mContext;
    private TestUtils mTestUtils;

    // Take screenshots on test failure
    @Rule
    public TestOutputTestWatcher mTestOutputTestWatcher = new TestOutputTestWatcher();
    private UiObject2 obj3;

    @Before
    public void testBeforeClass() {
        // Set stuff up before each test
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mTestUtils = new TestUtils(mContext, mDevice);
    }

    @Test
    public void testChrome()
            throws InterruptedException {

        // Initialize UiDevice instance

        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = InstrumentationRegistry.getContext();
        String ChromePackageName = "com.android.chrome";
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(ChromePackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        Log.v(TAG, "Starting Chrome");
        context.startActivity(intent);

        // Wait for the app to appear

        int uiTimeout = 5000;
        device.wait(Until.hasObject(By.pkg("ChromePackageName").depth(0)), uiTimeout);

        // Go to youtube.com

        Log.v(TAG, "Selecting Search");
        device.wait(Until.hasObject(By.text("Search YouTube").clazz("com.android.chrome", "android.widget.Button"
        )), uiTimeout);
        device.pressSearch();
        UiObject2 searchbar = device.findObject(By.res("com.android.chrome:id/url_bar"));
        searchbar.setText("youtube.com");
        sleep(1000);
        device.pressEnter();

        // Wait for youtube.com to load


        //find a video


        obj3 = device.findObject(By.text("Search YouTube"));
        device.wait(Until.hasObject(By.text("Search YouTube")), uiTimeout);

        obj3.click();

        // Wait for Search button click to take effect


        device.wait(Until.hasObject(By.clazz("android.webkit.WebView")), uiTimeout);
        device.wait(Until.hasObject(By.clazz("android.widget.EditText")), uiTimeout);

        UiObject2 obj1 = device.findObject(By.clazz("android.webkit.WebView"));
        UiObject2 obj2 = obj1.findObject(By.clazz("android.widget.EditText"));
        obj2.setText("Songs for an empty world");
        device.pressEnter();

        //click on video

        Log.v(TAG, "Finding video");
        device.wait(Until.hasObject(By.clazz("android.view.View").text("Songs for an empty world")), uiTimeout);
        UiObject2 video = device.findObject(By.clazz("android.view.View").text("Songs for an empty world"));


        sleep(1000);

        video.click();

        UiObject2 skipAdButton = device.findObject(By.text("Skip ad"));
        if (skipAdButton != null) {
            skipAdButton.click();
        }
        UiObject2 skipAdsButton = device.findObject(By.text("Skip ads"));
        if (skipAdsButton != null) {
            skipAdsButton.click();
        }

        sleep(10 * 2000);

        Log.v(TAG, "Going back from player");
        device.pressBack();
        device.pressHome();


    }
}