package com.amg.newsapp.ui.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amg.newsapp.models.NoFavoritesException
import com.amg.newsapp.models.NoNetworkException
import com.amg.newsapp.models.SearchMinCharacterException
import com.amg.newsapp.models.SearchNotFoundException

@Composable
fun ErrorLayout(throwable: Throwable, modifier: Modifier = Modifier) {
    val message = when (throwable) {
        is NoNetworkException -> {
            "Network issue. Please check your internet connection and try again."
        }

        is SearchMinCharacterException -> {
            "Please enter at least 3 characters to perform a search."
        }

        is SearchNotFoundException -> {
            "No results found for your search query."
        }

        is NoFavoritesException -> {
            "You don't have any favorites yet. Add some favorites to see them here."
        }

        else -> {
            "Oops! Something went wrong. Please try again later."
        }
    }

    Text(
        modifier = modifier
            .padding(16.dp)
            .wrapContentSize(),
        text = message
    )
}

@Preview
@Composable
fun ErrorLayoutPreview() {
    ErrorLayout(NoNetworkException(), Modifier.fillMaxSize())
}