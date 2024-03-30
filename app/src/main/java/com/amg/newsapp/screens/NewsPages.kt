package com.amg.newsapp.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.amg.newsapp.R

enum class NewsPages(
    @StringRes val titleResId: Int,
    @DrawableRes val icon: Int,
) {
    SEARCH_SCREEN(R.string.search_screen_title, R.drawable.ic_search),
    FAVORITES_SCREEN(R.string.favorites_screen_title, R.drawable.ic_favorite)
}
