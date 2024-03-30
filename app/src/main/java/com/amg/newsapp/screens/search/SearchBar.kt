package com.amg.newsapp.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amg.newsapp.ui.theme.PurpleGrey40
import com.amg.newsapp.ui.theme.PurpleGrey80

@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    onClear: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .height(56.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(PurpleGrey80),
    ) {

        if (query.isEmpty()) {
            // Hint Text
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentHeight()
                    .padding(start = 24.dp, end = 8.dp),
                color = PurpleGrey40,
                text = "Search text here",
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BasicTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .wrapContentHeight()
                    .padding(start = 24.dp, end = 8.dp),
                singleLine = true
            )

            if (query.isNotEmpty()) {
                IconButton(
                    onClick = {
                        query = ""
                        onClear(query)
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                }
            }

            IconButton(
                onClick = {
                    onSearch(query)
                }
            ) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(
        {},
        {},
        Modifier.fillMaxWidth()
    )
}