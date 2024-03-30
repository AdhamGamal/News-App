package com.amg.newsapp.screens.favorites

import com.amg.newsapp.models.Article


sealed class FavoritesAction {
    data object Loading : FavoritesAction()
    data class Loaded(val articles: List<Article>) : FavoritesAction()
    data class Error(val ex: Throwable) : FavoritesAction()
}