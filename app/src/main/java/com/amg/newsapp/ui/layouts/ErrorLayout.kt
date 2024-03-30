package com.amg.newsapp.ui.layouts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amg.newsapp.models.NoNetworkException
import com.amg.newsapp.models.SearchMinCharacterException
import com.amg.newsapp.models.SearchNotFoundException

@Composable
fun ErrorLayout(throwable: Throwable, modifier: Modifier = Modifier) {
    val message = when (throwable) {
        is NoNetworkException -> {
            "Network Issue...!!"
        }

        is SearchMinCharacterException -> {
            "Use at least 3 characters to search...!"
        }

        is SearchNotFoundException -> {
            "Search Not Found...!!"
        }

        else -> {
            "Something went wrong...!"
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