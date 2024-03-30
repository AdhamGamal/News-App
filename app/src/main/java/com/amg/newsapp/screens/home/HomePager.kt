package com.amg.newsapp.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amg.newsapp.screens.NewsPages
import com.amg.newsapp.screens.favorites.FavoritesScreen
import com.amg.newsapp.screens.search.SearchScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePager(
    pagerState: PagerState,
    pages: Array<NewsPages>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        val coroutineScope = rememberCoroutineScope()

        TabRow(
            selectedTabIndex = pagerState.currentPage
        ) {
            pages.forEachIndexed { index, page ->
                val title = stringResource(id = page.titleResId)
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = title) },
                    icon = {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(page.icon),
                            contentDescription = title
                        )
                    },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary
                )
            }
        }

        HorizontalPager(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { index ->
            when (pages[index]) {
                NewsPages.SEARCH_SCREEN -> {
                    SearchScreen()
                }

                NewsPages.FAVORITES_SCREEN -> {
                    FavoritesScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun HomePagerPreview() {
    val pages = arrayOf(NewsPages.SEARCH_SCREEN, NewsPages.FAVORITES_SCREEN)
    val pagerState = rememberPagerState(pageCount = { pages.size })
    HomePager(pagerState = pagerState, pages = pages)
}