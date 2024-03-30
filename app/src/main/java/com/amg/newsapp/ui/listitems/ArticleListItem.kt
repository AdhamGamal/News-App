package com.amg.newsapp.ui.listitems

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amg.newsapp.R
import com.amg.newsapp.models.Article
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArticleListItem(
    article: Article,
    onFavoriteClick: (Article) -> Unit,
    onUnFavoriteClick: (Article) -> Unit,
    modifier: Modifier = Modifier,
) {

    var isFavorite by remember { mutableStateOf(article.isFavorite()) }

    Card(
        modifier = modifier.padding(10.dp),
        shape = RoundedCornerShape(5.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                )
                IconButton(
                    onClick = {
                        if (isFavorite) {
                            onUnFavoriteClick(article)
                        } else {
                            onFavoriteClick(article)
                        }
                        isFavorite = !isFavorite
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = if (isFavorite)
                            painterResource(R.drawable.ic_favorite)
                        else
                            painterResource(R.drawable.ic_unfavorite),
                        contentDescription = "Favorite"
                    )
                }
            }
            if (article.description.isNotEmpty()) {
                Text(
                    text = article.description,
                    maxLines = 2,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            if (article.url.isNotEmpty()) {
                Text(
                    text = article.url,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            if (article.author.isNotEmpty()) {
                Text(
                    text = article.author,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            if (article.publishedAt.isNotEmpty()) {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
                val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                val date = inputFormat.parse(article.publishedAt)
                date?.let {
                    val formattedDate = outputFormat.format(it)
                    Text(
                        text = formattedDate,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ArticleListItemPreview() {
    ArticleListItem(
        article = Article(
            author = "John Pike",
            title = "U.S., Seychelles Conduct Bilateral Maritime Security engagements",
            description = "Over the course of 7 days from March 14-21, 2024, United States Coast Guard Law Enforcement Detachment (LEDET) personnel worked alongside their counterparts in the Seychelles Coast Guard during multiple bilateral maritime security engagements in Seychelles' E…",
            url = "https://www.globalsecurity.org/military/library/news/2024/03/mil-240324-usn02.htm",
            publishedAt = "2024-03-27T09:14:44Z",
        ),
        onFavoriteClick = {},
        onUnFavoriteClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}