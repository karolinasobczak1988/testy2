package com.realitymine.uiautomatortest.reallifeuitester.util;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;

import org.junit.Before;
import org.junit.Rule;

public class TestBase {
    protected int UI_TIMEOUT_MS = 10000;
    protected int UI_SHORT_PAUSE_MS = 2000;

    protected UiDevice mDevice;
    protected Context mContext;
    protected TestUtils mTestUtils;

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
}
