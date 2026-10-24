package com.example.vkstart

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppListPresentationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun contentAndScrollPositionSurviveRotation() {
        compose.onNodeWithTag("app_list").performScrollToIndex(5)
        compose.onNodeWithText("Сферум").assertIsDisplayed()

        try {
            rotateTo(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, Configuration.ORIENTATION_LANDSCAPE)
            compose.onNodeWithText("Список приложений").assertIsDisplayed()
            compose.onNodeWithText("Сферум").assertIsDisplayed()
            rotateTo(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, Configuration.ORIENTATION_PORTRAIT)
            compose.onNodeWithText("Сферум").assertIsDisplayed()
        } finally {
            compose.activityRule.scenario.onActivity {
                it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            }
        }
    }

    @Test
    fun toolbarSnackbarIsNotReplayedAfterActivityRecreation() {
        val message = compose.activity.getString(R.string.app_list_toolbar_message)
        compose.onNodeWithText(message).assertDoesNotExist()
        compose.onNodeWithText("Список приложений").performClick()
        compose.onNodeWithText(message).assertIsDisplayed()

        compose.activityRule.scenario.recreate()

        compose.onNodeWithText(message).assertDoesNotExist()
        // A fresh click still works after rotation.
        compose.onNodeWithText("Список приложений").performClick()
        compose.onNodeWithText(message).assertIsDisplayed()
        compose.mainClock.advanceTimeBy(6_000)
        compose.onNodeWithText(message).assertDoesNotExist()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText(message).assertDoesNotExist()
    }

    private fun rotateTo(requestedOrientation: Int, configurationOrientation: Int) {
        val previousActivity = compose.activity
        compose.activityRule.scenario.onActivity { it.requestedOrientation = requestedOrientation }
        compose.waitUntil(timeoutMillis = 10_000) {
            compose.activity !== previousActivity &&
                compose.activity.resources.configuration.orientation == configurationOrientation
        }
        compose.waitForIdle()
    }
}
