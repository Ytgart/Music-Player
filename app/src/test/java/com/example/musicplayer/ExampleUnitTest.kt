package com.example.musicplayer

import com.example.musicplayer.utils.UserDataValidator
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun validateString_isCorrect() {
        val validator = UserDataValidator()
        val testCases = listOf(
            "test",
            "test test",
            ""
        )

        assertArrayEquals(
            testCases.map { validator.validateString(it) }.toTypedArray(),
            arrayOf(true, false, false)
        )
    }
}