package com.amg.newsapp.screens.search

import androidx.paging.PagingData
import com.amg.newsapp.models.Article
import kotlinx.coroutines.flow.Flow


sealed class SearchAction {
    data object Loading : SearchAction()
    data class Loaded(val articles: Flow<PagingData<Article>>) : SearchAction()
    data class Error(val ex: Throwable) : SearchAction()
}