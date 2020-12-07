package com.realitymine.uiautomatortest.reallifeuitester;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
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
public class YoutubeTest {
    String TAG = "reallifeuitester.YoutubeTest";

    private UiDevice mDevice;
    private Context mContext;
    private TestUtils mTestUtils;

    // Take screenshots on test failure
    @Rule
    public TestOutputTestWatcher mTestOutputTestWatcher = new TestOutputTestWatcher();

    @Before
    public void testBeforeClass() {
        // Set stuff up before each test
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        mTestUtils = new TestUtils(mContext, mDevice);
    }

    @Test
    public void testYoutube()
            throws InterruptedException {
        // Initialize UiDevice instance
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context context = InstrumentationRegistry.getContext();
        String YoutubePackageName = "com.google.android.youtube";
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(YoutubePackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances

        Log.v(TAG, "Starting Youtube");
        context.startActivity(intent);

        // Wait for the app to appear
        int uiTimeout = 5000;
        device.wait(Until.hasObject(By.pkg(YoutubePackageName).depth(0)), uiTimeout);


        // Go to search page
        Log.v(TAG, "Selecting Search");
        mTestUtils.waitForAndFindObject(By.desc("Search"), 5000).click();

        // Search for videos
        Log.v(TAG, "Entering Search");
        String searchBoxRes = "youtube:id/search_edit_text";
        device.wait(Until.hasObject(By.res(searchBoxRes)), uiTimeout);
        device.findObject(By.text("Search YouTube")).setText("Songs for an empty world");
        device.pressEnter();

        // Find and press enter
        Log.v(TAG, "Finding series");
        device.findObject(By.text("Songs for an empty world")).click();
        device.pressEnter();

        //click on video
        device.wait(Until.hasObject(By.descContains("Songs for an empty world")), uiTimeout);

        device.findObject(By.descContains("Songs for an empty world")).click();




        sleep(10 * 5000);

        Log.v(TAG, "Going back from player");
        device.pressBack();
        device.pressHome();


            }
        }






