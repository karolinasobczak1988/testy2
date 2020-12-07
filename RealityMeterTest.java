package com.realitymine.uiautomatortest.reallifeuitester;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;

import com.realitymine.uiautomatortest.reallifeuitester.util.LocaleHelper;
import com.realitymine.uiautomatortest.reallifeuitester.util.ScreenshotTakerKt;
import com.realitymine.uiautomatortest.reallifeuitester.util.TestOutputTestWatcher;
import com.realitymine.uiautomatortest.reallifeuitester.util.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Locale;

@RunWith(AndroidJUnit4.class)
public class RealityMeterTest {
    private int UI_TIMEOUT_MS = 10000;
    private int UI_SHORT_PAUSE_MS = 2000;

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
    /**
     * Works with app v4. May need tweaking for app v5
     */
    public void testInstallAndOnboarding()
            throws InterruptedException {

        String packageName = "com.apadmi.analyzeme.android";
        String regEmailAddress = "graham-a11y-test@realitymine.com";

        // Uninstall, re-install and launch app
        mTestUtils.uninstallIfInstalled(packageName);
        mTestUtils.installFromPlayStore(packageName);
        mTestUtils.launchApp(packageName);

        // Click Continue to "We need more information to complete configuration
        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), UI_TIMEOUT_MS);

        // Press Continue
        mTestUtils.waitForAndFindObject(By.res("android:id/button1"), UI_TIMEOUT_MS).click();

        // Enter reg email address
        mTestUtils.waitForAndFindObject(By.res("com.apadmi.analyzeme.android:id/EmailAddressText"), UI_TIMEOUT_MS).setText(regEmailAddress);
        mTestUtils.waitForAndFindObject(By.res("com.apadmi.analyzeme.android:id/RegisterButton"), UI_TIMEOUT_MS).click();

        mDevice.waitForIdle();

        // Accept T&Cs. This can take some time, due to network + our slow servers!
        mTestUtils.waitForAndFindObject(By.res("com.apadmi.analyzeme.android:id/leftButton"), 30 * 1000).click();

        // Press Continue on "...is now configured"
        mTestUtils.waitForAndFindObject(By.res("android:id/button1"), UI_TIMEOUT_MS).click();
    }

    @Test
    // Switches to the language code specified by the "switchToLanguageCode" command line argument, or "en" by default
    public void switchLanguageViaSettingsAndroid8() throws InterruptedException {
        String switchToLanguageCode = "en";

        // Get the language from the command line arguments, if present
        // See: https://github.com/googlesamples/android-testing-templates/blob/master/AndroidTestingBlueprint/README.md#custom-gradle-command-line-arguments
        Bundle arguments = InstrumentationRegistry.getArguments();
        String arg = arguments.getString("switchToLanguageCode");
        if (arg != null) {
            switchToLanguageCode = arg;
        }

        String langFullName = LocaleHelper.getNativeLangFullNameFromCode(switchToLanguageCode);
        String langName = LocaleHelper.getNativeLangNameFromCode(switchToLanguageCode);
        String regionName = LocaleHelper.getRegionFromCode(switchToLanguageCode);

        Intent localeIntent = new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS);
        localeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);    // Ensure we go to the root of the Languages screens - doesn't work
        mContext.startActivity (localeIntent);

        // Wait for list of languages
        UiObject2 dragList = mTestUtils.waitForAndFindObject(By.res("com.android.settings:id/dragList"), UI_TIMEOUT_MS);
        List<UiObject2> langLabels = dragList.findObjects(By.res("com.android.settings:id/label"));

        // Is our language already the default?
        // Search by full name of language i.e. Language (Region)
        if (langLabels.get(0).getText().startsWith(langFullName)) {
            // It's already the selected language, there is nothing further to do
            return;
        }

        // If we have multiple languages, do a clean up now and remove all by the default (first)
        mTestUtils.removeAllLanguagesExceptDefault();

        // Add our new language
        mTestUtils.waitForAndFindObject(By.res("com.android.settings:id/add_language"), UI_TIMEOUT_MS).click();
        mDevice.waitForWindowUpdate("com.android.settings", UI_SHORT_PAUSE_MS);

        // Select from scrolling list
        UiScrollable langList = new UiScrollable(new UiSelector().scrollable(true));
        langList.setMaxSearchSwipes(99);
        try {
            UiObject langObj = langList.getChildByText(new UiSelector().resourceId("android:id/locale"), langName, true);
            langObj.click();
            mDevice.waitForWindowUpdate("com.android.settings", UI_SHORT_PAUSE_MS);
        } catch (UiObjectNotFoundException e) {
            // Language not found
            throw new Resources.NotFoundException("Language: " + langName + " not found on device");
        }

        // Do we need to select a region?
        if (mTestUtils.waitForAndFindObject(By.res("android:id/button1"), UI_SHORT_PAUSE_MS) == null) {
            // If we need to select a particular region, find it now
            if (regionName != null) {
                UiScrollable regionList = new UiScrollable(new UiSelector().resourceId("android:id/list"));
                regionList.setMaxSearchSwipes(99);
                try {
                    UiObject regionObj = regionList.getChildByText(new UiSelector().resourceId("android:id/locale"), regionName, true);
                    regionObj.click();
                    mDevice.waitForWindowUpdate("com.android.settings", UI_SHORT_PAUSE_MS);
                } catch (UiObjectNotFoundException e) {
                    // Region not found
                    throw new Resources.NotFoundException("Region: " + regionName + " for language: " + langName + " not found on device");
                }
            }
            else {
                // Just select the first region
                mTestUtils.waitForAndFindObject(By.res("android:id/locale"), UI_TIMEOUT_MS).click();
            }
        }

        // If we see a dialog to confirm switching to this language now (like we do on Samsung S7), set as default now
        // Otherwise (like on Nexus 5X API 27 emu where we see), we'll need to delete all but the desired langauge
        // Select "Set as default" button within confirmation dialog
        UiObject2 setAsDefaultButton = mTestUtils.waitForAndFindObject(By.res("android:id/button1"), UI_SHORT_PAUSE_MS);
        if (setAsDefaultButton != null) {
            setAsDefaultButton.click();
        }

        // Is our language already the default?
        // Search by full name of language i.e. Language (Region)
        dragList = mTestUtils.waitForAndFindObject(By.res("com.android.settings:id/dragList"), UI_TIMEOUT_MS);
        langLabels = dragList.findObjects(By.res("com.android.settings:id/label"));

        if (langLabels.get(0).getText().startsWith(langFullName)) {
            // It's already the selected language, there is nothing further to do
            return;
        }
        else {
            mTestUtils.removeAllLanguagesExceptLangName(langFullName);
        }
    }

    @Test
    public void screenshotNotificationChannels() throws InterruptedException {
        mDevice.pressHome();
        mDevice.openNotification();

        // Long press on the AnalyzeMe notification. Note: longClick() doesn't appear to do the job
        mTestUtils.waitForAndFindObject(By.pkg("com.apadmi.analyzeme.android"), UI_TIMEOUT_MS).click(3000);

        // More details...
        mTestUtils.waitForAndFindObject(By.res("com.android.systemui:id/more_settings"), UI_TIMEOUT_MS).click();

        Thread.sleep(8000);

        Locale loc = mContext.getResources().getConfiguration().locale;
        ScreenshotTakerKt.takeScreenshot("running_tests/",
                loc.toLanguageTag() + "_screenshotNotificationChannels");
    }
}
