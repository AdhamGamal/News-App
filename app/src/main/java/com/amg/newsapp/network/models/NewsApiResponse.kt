package com.amg.newsapp.network.models

class NewsApiResponse(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleApiResponse> = emptyList()
)