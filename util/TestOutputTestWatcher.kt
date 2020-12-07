package com.realitymine.uiautomatortest.reallifeuitester.util

import android.os.Environment
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

/**
 * A helper class to write out screenshots or other files while tests run
 * When added as a @Rule member, it automatically takes a screenshot on a test failure
 */
class TestOutputTestWatcher : TestWatcher() {
    var parentFolderPath: String = ""

    override fun starting(description: Description?) {
        super.starting(description)

        parentFolderPath = "${description?.className}/${description?.methodName}"
    }

    override fun failed(e: Throwable?, description: Description) {
        takeScreenshot(parentFolderPath = parentFolderPath, screenShotName = "failure")
    }

    fun takeScreenshot(screenshotName: String) {
        takeScreenshot(parentFolderPath = parentFolderPath, screenShotName = screenshotName)
    }

    /**
     * Writes an HTML file that get inserted into the test results HTML table, associated with the current
     * test
     */
    fun writeSupplimentaryHtml(html: String) {
        val path =
            File(
                File(
                    File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "com.realitymine.testoutput"
                        ).absolutePath,
                    "testoutput"
                ),
                    parentFolderPath
            )
        path.mkdirs()

        val filePath = File(path, "0.html")
        filePath.writeText(html)
    }
}
