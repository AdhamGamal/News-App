package com.amg.newsapp.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.amg.newsapp.R
import com.amg.newsapp.screens.NewsPages
import com.amg.newsapp.ui.theme.NewsAppTheme
import com.amg.newsapp.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    pages: Array<NewsPages> = NewsPages.entries.toTypedArray()
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeTopAppBar(scrollBehavior)
        }
    ) { contentPadding ->
        HomePager(
            pagerState,
            pages,
            Modifier.padding(top = contentPadding.calculateTopPadding())
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = Purple40
            )
        },
        scrollBehavior = scrollBehavior
    )
}


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun HomeScreenPreview() {
    NewsAppTheme {
        val pages = NewsPages.entries.toTypedArray()
        HomePager(
            pagerState = rememberPagerState(pageCount = { pages.size }),
            pages = pages
        )
    }
}