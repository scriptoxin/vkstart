package com.example.vkstart.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.vkstart.R
import com.example.vkstart.data.SampleData
import com.example.vkstart.model.AppInfo

@Composable
fun Showcase(onAppClick: (AppInfo) -> Unit, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = { SampleData.SHOWCASE_PAGE_COUNT })
    Column(modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.showcase),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp).semantics { heading() },
        )
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            pageSpacing = 16.dp,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth().testTag("showcase_pager"),
        ) { page ->
            Column(
                modifier = Modifier.fillMaxWidth().testTag("showcase_page_$page"),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SampleData.showcasePages[page].forEach { app ->
                    AppListItem(appInfo = app, onClick = { onAppClick(app) })
                }
            }
        }
        Text(
            text = stringResource(
                R.string.showcase_page,
                pagerState.currentPage + 1,
                pagerState.pageCount,
            ),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}
