package com.amg.newsapp.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amg.newsapp.network.api.NewsApiService
import com.amg.newsapp.network.models.Article
import com.amg.newsapp.network.paging.NewsPagingSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class NetworkUseCase @Inject constructor(private val service: NewsApiService) {

    fun getSearchResultStream(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { NewsPagingSource(service, query) }
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}
