package com.realitymine.uiautomatortest.reallifeuitester;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
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
public class NetflixTest {
    String TAG = "reallifeuitester.NetflixTest";

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
    public void testNetflix()
            throws InterruptedException {
        // Initialize UiDevice instance
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context context = InstrumentationRegistry.getContext();
        String netflixPackageName = "com.netflix.mediaclient";
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(netflixPackageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances

        Log.v(TAG, "Starting Netflix");
        context.startActivity(intent);

        // Wait for the app to appear
        int uiTimeout = 5000;
        device.wait(Until.hasObject(By.pkg(netflixPackageName).depth(0)), uiTimeout);

        //Selecting User
        Log.v(TAG, "Selecting User");
        mTestUtils.waitForAndFindObject(By.text("Graham"), 5000).click();


        // Go to search page
        Log.v(TAG, "Selecting Search");
        mTestUtils.waitForAndFindObject(By.desc( "Search"), 5000).click();

        // Search for series
        Log.v(TAG, "Entering Search");
        String searchBoxRes = "android:id/search_src_text";
        device.wait(Until.hasObject(By.res(searchBoxRes)), uiTimeout);
        device.findObject(By.res(searchBoxRes)).setText("disenchantment");
        device.pressEnter();

        // Find series
        Log.v(TAG, "Finding series");
        device.wait(Until.hasObject(By.res("com.netflix.mediaclient:id/search_result_img")), uiTimeout);
        device.findObject(By.desc("Disenchantment")).click();

        // Wait for details page and see if we can see the episodes list
        device.wait(Until.hasObject(By.res("com.netflix.mediaclient:id/video_details_title")), uiTimeout);

        // Scroll until we find episodes list
        Log.v(TAG, "Finding episode 1");
        for (int i = 0; i < 5; i++) {
            if (!device.hasObject(By.descStartsWith("Episode 1: "))) {
                device.findObject(By.scrollable(true)).scroll(Direction.DOWN, 0.8f);
                sleep(500);

                /*
                // Simulate a finger swipe up the screen
                int cx = device.getDisplayWidth() / 2;
                int cy = device.getDisplayHeight() / 2;
                device.drag(cx, cy, cx, 0, 10);

                sleep(200);
                */


            } else {
                break;
            }
        }

        Log.v(TAG, "Playing episode");
        device.findObject(By.descStartsWith("Episode 1: "))
                .findObject(By.res("com.netflix.mediaclient:id/episode_image_container"))
                .click();

        sleep(10 * 1000);

        Log.v(TAG, "Going back from player");
        device.pressBack();

//arrow back/
        Log.v(TAG, "Going back to search");
        device.pressBack();

        // remove previous search result
        Log.v(TAG, "Remove previous search result");
        searchBoxRes = "android:id/search_src_text";
        device.wait(Until.hasObject(By.res(searchBoxRes)), uiTimeout);
        device.findObject(By.res(searchBoxRes)).clear();

        //start a new search
        Log.v(TAG, "Entering Search");
        searchBoxRes = "android:id/search_src_text";
        device.wait(Until.hasObject(By.res(searchBoxRes)), uiTimeout);
        device.findObject(By.res(searchBoxRes)).setText("The IT Crowd");
        device.pressEnter();

        // Find series
        Log.v(TAG, "Finding series");
        device.wait(Until.hasObject(By.res("com.netflix.mediaclient:id/search_result_img")), uiTimeout);
        device.findObject(By.desc("The IT Crowd")).click();

        // Wait for details page and see if we can see the episodes list
        device.wait(Until.hasObject(By.res("com.netflix.mediaclient:id/video_details_title")), uiTimeout);

        // Scroll until we find episodes list
        Log.v(TAG, "Finding episode 1");
        for (int i = 0; i < 5; i++) {
            if (!device.hasObject(By.descStartsWith("Episode 1: "))) {
                device.findObject(By.scrollable(true)).scroll(Direction.DOWN, 0.8f);
                sleep(500);

                /*
                // Simulate a finger swipe up the screen
                int cx = device.getDisplayWidth() / 2;
                int cy = device.getDisplayHeight() / 2;
                device.drag(cx, cy, cx, 0, 10);

                sleep(200);
                */


            } else {
                break;
            }
        }

        Log.v(TAG, "Playing episode");
        device.findObject(By.descStartsWith("Episode 1: "))
                .findObject(By.res("com.netflix.mediaclient:id/episode_image_container"))
                .click();

        sleep(10 * 1000);

        Log.v(TAG, "Going back from player");

            // Back to home screen
            String homeScreen;
        homeScreen = "com.android.launcher3:id/workspace";
        device.pressBack();
        device.pressHome();







    }


    }

