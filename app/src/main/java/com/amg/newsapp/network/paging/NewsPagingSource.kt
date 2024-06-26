package com.amg.newsapp.network.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.amg.newsapp.models.Article
import com.amg.newsapp.network.api.NewsApiService

private const val STARTING_PAGE_INDEX = 1

class NewsPagingSource(
    private val service: NewsApiService,
    private val query: String,
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.searchNews(query, getCurrentDate(), page, params.loadSize)
            val articles = response.articles.map { it.toArticle() }
            LoadResult.Page(
                data = articles,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if ((page * params.loadSize) >= (response.totalResults ?: 0)) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }

    private fun getCurrentDate(): String {
        /*
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val date = Date()
                return dateFormat.format(date)
        */
        return "2024-03-25"
    }
}