package com.amg.newsapp.database

import com.amg.newsapp.database.dao.FavoritesDao
import com.amg.newsapp.database.models.ArticleWithSource
import com.amg.newsapp.network.models.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheUseCase @Inject constructor(
    private val favoritesDao: FavoritesDao
) {

    suspend fun addToFavorite(article: Article) {
        favoritesDao.insertArticleWithSource(article)
    }

    suspend fun removeFromFavorite(articleWithSource: ArticleWithSource) {
        favoritesDao.deleteSourceIfNoArticles(articleWithSource)
    }

    fun getAllFavorites() = favoritesDao.getArticles()
}
