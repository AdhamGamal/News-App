package com.amg.newsapp.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.amg.newsapp.database.CacheUseCase
import com.amg.newsapp.models.Article
import com.amg.newsapp.models.NoNetworkException
import com.amg.newsapp.models.SearchMinCharacterException
import com.amg.newsapp.network.NetworkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkUC: NetworkUseCase,
    private val cacheUC: CacheUseCase,
) : ViewModel() {

    var uiState by mutableStateOf<SearchAction>(SearchAction.Loading)
        private set

    var favoriteArticles by mutableStateOf<List<Article>>(emptyList())
        private set

    init {
        search("")
        loadFavorites()
    }

    fun search(query: String) {

        if (query.isEmpty() || query.length < 3) {
            uiState = SearchAction.Error(SearchMinCharacterException())
            return
        }

        uiState = try {
            SearchAction.Loaded(networkUC.getSearchResultStream(query).cachedIn(viewModelScope))
        } catch (ex: Exception) {
            SearchAction.Error(NoNetworkException())
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            cacheUC.getAllFavorites().collect {
                favoriteArticles = it
            }
        }
    }
}