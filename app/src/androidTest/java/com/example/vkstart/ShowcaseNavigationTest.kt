package com.example.vkstart

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ShowcaseNavigationTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    fun showcaseHasThreePagesOfThreeAppsAndEveryPageReturnsToList() {
        val firstAppOnPage = listOf("ВКонтакте", "VK Мессенджер", "Почта Mail.ru")
        for (targetPage in 0..2) {
            compose.onNodeWithText("ВКонтакте").performClick()
            compose.onNodeWithTag("showcase_pager").performScrollTo()
            repeat(targetPage) {
                compose.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
                compose.waitForIdle()
            }
            compose.onAllNodes(
                hasClickAction() and hasParent(hasTestTag("showcase_page_$targetPage")),
            ).assertCountEquals(3)
            if (targetPage == 2) {
                // The last page must remain the last page after another swipe.
                compose.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
                compose.waitForIdle()
                compose.onNodeWithTag("showcase_page_2").assertIsDisplayed()
                compose.onNodeWithTag("showcase_pager").performTouchInput { swipeRight() }
                compose.waitForIdle()
                compose.onNodeWithTag("showcase_page_1").assertIsDisplayed()
                compose.onNodeWithTag("showcase_pager").performTouchInput { swipeLeft() }
                compose.waitForIdle()
            }
            compose.onNode(
                hasText(firstAppOnPage[targetPage]) and
                    hasAnyAncestor(hasTestTag("showcase_page_$targetPage")),
            ).performClick()
            compose.onNodeWithText("Список приложений").assertIsDisplayed()
        }
    }

    @Test
    fun selectedAppDetailsAndBackButtonWork() {
        compose.onNodeWithText("VK Музыка").performClick()
        compose.onNodeWithText("VK Музыка").assertIsDisplayed()
        compose.onNodeWithText("Описание приложения").assertIsDisplayed()
        compose.onNodeWithContentDescription("Назад").performClick()
        compose.onNodeWithText("Список приложений").assertIsDisplayed()
        compose.onNodeWithText("VK Видео").performClick()
        compose.activityRule.scenario.recreate()
        compose.onNodeWithText("VK Видео").assertIsDisplayed()
        compose.onNodeWithContentDescription("Назад").performClick()
        compose.onNodeWithText("Список приложений").assertIsDisplayed()
    }
}
