package com.amg.newsapp.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.amg.newsapp.models.Article
import com.amg.newsapp.models.NoNetworkException
import com.amg.newsapp.models.SearchNotFoundException
import com.amg.newsapp.screens.SharedArticleViewModel
import com.amg.newsapp.ui.layouts.ErrorLayout
import com.amg.newsapp.ui.layouts.LoadingLayout
import com.amg.newsapp.ui.listitems.ArticleListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    sharedArticleVM: SharedArticleViewModel = hiltViewModel(),
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    SearchScreen(
        viewModel.uiState,
        onSearch = {
            viewModel.search(it)
            keyboardController?.hide()
        },
        onClear = {
            viewModel.search(it)
            keyboardController?.hide()
        },
        onFavorite = {
            sharedArticleVM.addToFavorites(it)
        },
        onUnFavorite = {
            sharedArticleVM.removeFromFavorites(it)
        },
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun SearchScreen(
    uiState: SearchAction,
    onSearch: (String) -> Unit,
    onClear: (String) -> Unit,
    onFavorite: (Article) -> Unit,
    onUnFavorite: (Article) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        SearchBar(
            onSearch,
            onClear,
            Modifier.fillMaxWidth()
        )

        when (uiState) {
            is SearchAction.Loading -> {
                LoadingLayout(Modifier.fillMaxSize())
            }

            is SearchAction.Loaded -> {

                with(uiState.articles.collectAsLazyPagingItems()) {
                    if (itemCount == 0) {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                LoadingLayout(Modifier.fillMaxSize())
                            }

                            loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                ErrorLayout(NoNetworkException(), Modifier.fillMaxSize())
                            }

                            loadState.refresh is LoadState.NotLoading || loadState.append is LoadState.NotLoading -> {
                                ErrorLayout(SearchNotFoundException(), Modifier.fillMaxSize())
                            }
                        }
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(count = itemCount) { index ->
                                get(index)?.let {
                                    if (it.title.lowercase() != "[removed]") {
                                        ArticleListItem(
                                            it,
                                            onFavorite,
                                            onUnFavorite,
                                            Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }

                            if (loadState.append == LoadState.Loading) {
                                item {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is SearchAction.Error -> {
                ErrorLayout(uiState.ex, Modifier.fillMaxSize())
            }
        }
    }
}


@Preview
@Composable
private fun GalleryScreenPreview(
    @PreviewParameter(GalleryScreenPreviewParamProvider::class) articles: Flow<PagingData<Article>>
) {
    SearchScreen(SearchAction.Loaded(articles), {}, {}, {}, {}, Modifier.fillMaxSize())
}

private class GalleryScreenPreviewParamProvider :
    PreviewParameterProvider<Flow<PagingData<Article>>> {

    override val values: Sequence<Flow<PagingData<Article>>> =
        sequenceOf(
            flowOf(
                PagingData.from(
                    listOf(
                        Article(
                            author = "John Pike",
                            title = "U.S., Seychelles Conduct Bilateral Maritime Security engagements",
                            description = "Over the course of 7 days from March 14-21, 2024, United States Coast Guard Law Enforcement Detachment (LEDET) personnel worked alongside their counterparts in the Seychelles Coast Guard during multiple bilateral maritime security engagements in Seychelles' E…",
                            url = "https://www.globalsecurity.org/military/library/news/2024/03/mil-240324-usn02.htm",
                            publishedAt = "2024-03-27T09:14:44Z",
                        ),
                        Article(
                            author = "John Pike",
                            title = "Treasury Sanctions Financial Facilitators and Illicit Drug Traffickers Supporting the Syrian Regime",
                            description = "Today, the Department of the Treasury's Office of Foreign Assets Control (OFAC) sanctioned 11 individuals and entities supporting the regime of Syrian President Bashar Al-Assad through the facilitation of illicit financial transfers and trafficking of illegal…",
                            url = "https://www.globalsecurity.org/wmd/library/news/syria/2024/syria-240326-treasury01.htm",
                            publishedAt = "2024-03-28T18:12:04Z",
                        ),
                    )
                )
            ),
        )
}