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
        FavoritesAction.Loaded(
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
        ),
        {},
        Modifier.fillMaxSize()
    )
}
