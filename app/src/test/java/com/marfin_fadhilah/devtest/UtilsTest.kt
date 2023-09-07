package com.marfin_fadhilah.devtest

import com.marfin_fadhilah.devtest.core.utils.Utils.isContainFile
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.nio.file.Paths


class UtilsTest {

    @Test
    fun testIsDeviceRooted_WithExistingFile() {
        val filename1 = "/Users/bnc/Public/devtest/app"
        val filename2 = "/path/to/non_existing_file"

        assertTrue(isContainFile(filename1 ?: "", filename2))
    }

    @Test
    fun testIsDeviceRooted_WithNonExistingFile() {
        val filename1 = "/path/to/non_existing_file"
        val filename2 = "/path/to/non_existing_file"

        assertFalse(isContainFile(filename1, filename2))
    }

    @Test
    fun testIsDeviceRooted_WithEmptyFilename() {
        val filename = ""
        assertFalse(isContainFile(filename))
    }

    @Test
    fun testIsDeviceRooted_WithEmptyArgument() {
        assertFalse(isContainFile())
    }
}
