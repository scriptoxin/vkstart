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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.swipe
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
            waitForAnimations()
            compose.onNodeWithTag("showcase_pager").performScrollTo()
            repeat(targetPage) {
                swipeShowcase(forward = true)
            }
            compose.onAllNodes(
                hasClickAction() and hasParent(hasTestTag("showcase_page_$targetPage")),
            ).assertCountEquals(3)
            if (targetPage == 2) {
                // The last page must remain the last page after another swipe.
                swipeShowcase(forward = true)
                compose.onNodeWithTag("showcase_page_2").assertIsDisplayed()
                swipeShowcase(forward = false)
                compose.onNodeWithTag("showcase_page_1").assertIsDisplayed()
                swipeShowcase(forward = true)
            }
            compose.onNode(
                hasText(firstAppOnPage[targetPage]) and
                    hasAnyAncestor(hasTestTag("showcase_page_$targetPage")),
            ).performScrollTo().performClick()
            waitForAnimations()
            compose.onNodeWithText("Список приложений").assertIsDisplayed()
        }
    }

    @Test
    fun selectedAppDetailsAndBackButtonWork() {
        compose.onNodeWithText("VK Музыка").performClick()
        waitForAnimations()
        compose.onNode(
            hasText("VK Музыка") and !hasAnyAncestor(hasTestTag("showcase_pager")),
        ).assertIsDisplayed()
        compose.onNodeWithText("Описание приложения").assertIsDisplayed()
        compose.onNodeWithContentDescription("Назад").performClick()
        waitForAnimations()
        compose.onNodeWithText("Список приложений").assertIsDisplayed()
        compose.onNodeWithText("VK Видео").performClick()
        waitForAnimations()
        compose.activityRule.scenario.recreate()
        compose.onNode(
            hasText("VK Видео") and !hasAnyAncestor(hasTestTag("showcase_pager")),
        ).assertIsDisplayed()
        compose.onNodeWithContentDescription("Назад").performClick()
        waitForAnimations()
        compose.onNodeWithText("Список приложений").assertIsDisplayed()
    }

    private fun waitForAnimations() {
        // Both destinations can be present until NavHost's transition finishes.
        compose.mainClock.advanceTimeBy(1_000)
        compose.waitForIdle()
    }

    private fun swipeShowcase(forward: Boolean) {
        val pager = compose.onNodeWithTag("showcase_pager")
        val node = pager.fetchSemanticsNode()
        // A tall pager may be clipped by the vertical list. Swipe in its visible area.
        val visibleY = node.boundsInRoot.center.y - node.positionInRoot.y
        pager.performTouchInput {
            val left = Offset(width * 0.1f, visibleY)
            val right = Offset(width * 0.9f, visibleY)
            swipe(start = if (forward) right else left, end = if (forward) left else right)
        }
        waitForAnimations()
    }
}
