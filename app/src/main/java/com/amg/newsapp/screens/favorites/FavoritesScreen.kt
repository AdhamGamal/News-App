package com.amg.newsapp.screens.favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.amg.newsapp.models.Article
import com.amg.newsapp.screens.SharedArticleViewModel
import com.amg.newsapp.ui.layouts.ErrorLayout
import com.amg.newsapp.ui.layouts.LoadingLayout
import com.amg.newsapp.ui.listitems.ArticleListItem

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    sharedArticleVM: SharedArticleViewModel = hiltViewModel(),
) {
    FavoritesScreen(
        viewModel.uiState,
        onUnFavorite = {
            sharedArticleVM.removeFromFavorites(it)
        },
        modifier = Modifier.fillMaxSize()
    )
}


@Composable
fun FavoritesScreen(
    uiState: FavoritesAction,
    onUnFavorite: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is FavoritesAction.Loading -> {
            LoadingLayout(modifier)
        }

        is FavoritesAction.Loaded -> {
            LazyColumn(modifier) {
                items(uiState.articles) { item ->
                    ArticleListItem(
                        item,
                        {},
                        onUnFavorite,
                        Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        is FavoritesAction.Error -> {
            ErrorLayout(uiState.ex, modifier)
        }
    }
}

@Preview
@Composable
private fun GalleryScreenPreview() {
    FavoritesScreen(
        FavoritesAction.Loaded(Article.generateFakeData(3)),
        {},
        Modifier.fillMaxSize()
    )
}
