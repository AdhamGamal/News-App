package com.amg.newsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.amg.newsapp.database.models.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Transaction
    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleEntity>>

    @Insert
    suspend fun insertArticle(article: ArticleEntity): Long


    @Query("DELETE FROM articles WHERE id = :articleId")
    suspend fun deleteArticle(articleId: Long)
}
