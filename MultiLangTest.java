package com.realitymine.uiautomatortest.reallifeuitester;

import android.support.test.uiautomator.By;

import com.realitymine.uiautomatortest.reallifeuitester.util.TestBase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

@RunWith(Parameterized.class)
public class MultiLangTest extends TestBase {
    @Parameterized.Parameter(value = 0)
    public String mLanguage;

    @Parameterized.Parameters(name = "{index}") // Note: changing this name causes Gradle not to find any tests: com.android.builder.testing.ConnectedDevice > No tests found.
    public static Collection<Object[]> initParameters() {
        return Arrays.asList(new Object[][] {
                {"en"},
                {"pl"}
                /***,
                    {"ko"},
                    {"fr"},
                    {"fr-CA"},
                    {"da"},
                    {"nl"},
                    {"pt"},
                    {"pt-BR"},
                    {"it"},
                    {"es"},
                    {"es-MX"},
                    {"id"},
                    {"ms"},
                    {"ja"},
                    {"ru"},
                    {"hi"},
                    {"zh-CN"},
                    {"zh-TW"},
                    {"th"},
                    {"ar"},
                    {"tr"},
                    {"sv"},
                    {"de"},
                    {"nb"},
                    {"sw"},
                    {"so"},
                    {"vi"},
                    {"my"},
                    {"tl"}
                 **/
        });
    }

    @Test
    public void testNotificationChannels() throws InterruptedException {
        // Write out the language code to make the test results easier to read
        mTestOutputTestWatcher.writeSupplimentaryHtml("<span>(" + mLanguage + ")</span>");

        // mLanguage is parameterized
        mTestUtils.switchDeviceLanguage(mLanguage);

        // Check we actually switched OK
        Locale loc = mTestUtils.getCurrentLocale();
        assertEquals(mLanguage, loc.toLanguageTag());
        assertFalse(loc.getISO3Language().isEmpty());

        mDevice.pressHome();
        mDevice.openNotification();
        mTestUtils.waitForAndFindObject(By.pkg("com.apadmi.analyzeme.android"), UI_TIMEOUT_MS);
        mTestOutputTestWatcher.takeScreenshot("00-notif-" + loc.toLanguageTag());

        // Long press on the AnalyzeMe notification. Note: longClick() doesn't appear to do the job
        mTestUtils.waitForAndFindObject(By.pkg("com.apadmi.analyzeme.android"), UI_TIMEOUT_MS).click(3000);

        mTestOutputTestWatcher.takeScreenshot("10-notifpress-" + loc.toLanguageTag());

        // More details...
        mTestUtils.waitForAndFindObject(By.res("com.android.systemui:id/more_settings"), UI_TIMEOUT_MS).click();

        Thread.sleep(8000);

        mTestOutputTestWatcher.takeScreenshot("20-notifchannels-" + loc.toLanguageTag());
}
}
