package com.amg.newsapp.screens.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amg.newsapp.database.CacheUseCase
import com.amg.newsapp.models.NoFavoritesException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val cacheUC: CacheUseCase) : ViewModel() {

    var uiState by mutableStateOf<FavoritesAction>(FavoritesAction.Loading)
        private set

    init {
        load()
    }

    private fun load() {
        viewModelScope.launch {
            cacheUC.getAllFavorites().collect {
                uiState = if (it.isNotEmpty()) {
                    FavoritesAction.Loaded(it)
                } else {
                    FavoritesAction.Error(NoFavoritesException())
                }
            }
        }
    }
}