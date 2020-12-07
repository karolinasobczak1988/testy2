package com.realitymine.uiautomatortest.reallifeuitester.util

import android.os.Environment.DIRECTORY_DOWNLOADS
import android.os.Environment.getExternalStoragePublicDirectory
import android.support.test.runner.screenshot.BasicScreenCaptureProcessor
import java.io.File

class MyScreenCaptureProcessor(parentFolderPath: String) : BasicScreenCaptureProcessor() {

    init {
        this.mDefaultScreenshotPath =
            File(
                File(
                    File(
                        getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS),
                        "com.realitymine.testoutput"
                    ).absolutePath,
            "testoutput"),
            parentFolderPath
        )
    }

    override fun getFilename(prefix: String): String = prefix
}
