package com.amg.newsapp.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amg.newsapp.database.CacheUseCase
import com.amg.newsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SharedArticleViewModel @Inject constructor(private val cacheUC: CacheUseCase) : ViewModel() {

    fun addToFavorites(article: Article) {
        viewModelScope.launch {
            cacheUC.addToFavorites(article)
        }
    }

    fun removeFromFavorites(article: Article) {
        viewModelScope.launch {
            if (article.isFavorite()) {
                cacheUC.removeFromFavorites(article.articleId)
            }
        }
    }
}