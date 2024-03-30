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
    val favorites = viewModel.favoriteArticles.associateBy { it.generateId() }

    SearchScreen(
        viewModel.uiState,
        favorites,
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
    favorites: Map<String, Article>,
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
                                get(index)?.let { article ->
                                    favorites[article.generateId()]?.articleId?.let {
                                        article.articleId = it
                                    }
                                    if (article.title.lowercase() != "[removed]") {
                                        ArticleListItem(
                                            article,
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
    SearchScreen(SearchAction.Loaded(articles), emptyMap(), {}, {}, {}, {}, Modifier.fillMaxSize())
}

private class GalleryScreenPreviewParamProvider :
    PreviewParameterProvider<Flow<PagingData<Article>>> {

    override val values: Sequence<Flow<PagingData<Article>>> =
        sequenceOf(
            flowOf(
                PagingData.from(
                    Article.generateFakeData(3)
                )
            ),
        )
}