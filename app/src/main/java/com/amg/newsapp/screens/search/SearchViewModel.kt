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
import com.google.android.gms.tasks.Task
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val networkUC: NetworkUseCase,
    private val cacheUC: CacheUseCase,
    private val remoteConfig: FirebaseRemoteConfig,
) : ViewModel() {

    var uiState by mutableStateOf<SearchAction>(SearchAction.Loading)
        private set

    var favoriteArticles by mutableStateOf<List<Article>>(emptyList())
        private set

    var showTextFlag by mutableStateOf(true)
        private set

    init {
        search("")
        loadFavorites()

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                onRemoteConfigComplete(task)
            }

        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                if (configUpdate.updatedKeys.contains("ShowingText")) {
                    remoteConfig.activate().addOnCompleteListener { task ->
                        onRemoteConfigComplete(task)
                    }
                } else {
                    showTextFlag = true
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {

            }
        })
    }

    private fun onRemoteConfigComplete(task: Task<Boolean>) {
        showTextFlag = if (task.isSuccessful) {
            remoteConfig.getBoolean("ShowingText")
        } else {
            true
        }
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