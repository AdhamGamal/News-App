package com.amg.newsapp.screens

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.amg.newsapp.R

enum class NewsPages(
    @StringRes val titleResId: Int,
    val icon: ImageVector
) {
    SEARCH_SCREEN(R.string.search_screen_title, Icons.Default.Search),
    FAVORITES_SCREEN(R.string.favorites_screen_title, Icons.Default.Star)
}
