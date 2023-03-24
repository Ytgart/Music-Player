package com.example.musicplayer

import androidx.core.content.res.ResourcesCompat.ThemeCompat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.musicplayer.utils.UserDataValidator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.musicplayer", appContext.packageName)
    }

    @Test
    fun hasNoErrors_isCorrect() {
        val validator = UserDataValidator()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        appContext.setTheme(R.style.Theme_AppCompat)

        val inputFieldsCase1 = listOf(
            TextInputLayout(appContext).apply {
                error = null
            },
            TextInputLayout(appContext).apply {
                error = "Test Error"
            }
        )
        val inputFieldsCase2 = listOf(
            TextInputLayout(appContext).apply {
                error = null
            },
            TextInputLayout(appContext).apply {
                error = null
            }
        )

        assertArrayEquals(
            arrayOf(
                validator.hasNoErrors(inputFieldsCase1),
                validator.hasNoErrors(inputFieldsCase2)
            ),
            arrayOf(false, true)
        )
    }
}