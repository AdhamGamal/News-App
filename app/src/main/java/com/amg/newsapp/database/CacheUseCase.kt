package com.amg.newsapp.database

import com.amg.newsapp.database.dao.FavoritesDao
import com.amg.newsapp.models.Article
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.map

@Singleton
class CacheUseCase @Inject constructor(
    private val favoritesDao: FavoritesDao
) {

    suspend fun addToFavorites(article: Article) {
        favoritesDao.insertArticle(article.toArticleEntity())
    }

    suspend fun removeFromFavorites(articleId: Long) {
        favoritesDao.deleteArticle(articleId)
    }

    fun getAllFavorites() = favoritesDao.getArticles().map { data -> data.map { it.toArticle() } }
}
