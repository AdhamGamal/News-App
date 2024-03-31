# News App

This Android app allows users to search for news articles using the News API (https://newsapi.org/).

## Features

- Search for news articles by keyword
- View search results with paging
- Save favorite articles
- View article details including author, title, description, URL, and published date

## Tech Stack

- MVVM architecture
- Clean architecture (SOLID principles)
- Compose for UI
- Flow for reactive programming
- Retrofit for network requests
- Paging for search results
- Hilt for dependency injection
- Room for local database storage

## Requirements

- Android SDK 24+
- Kotlin 1.9+
- Android Studio 8.3+
- News API key (sign up at https://newsapi.org/)

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Add your News API key to the `local.properties` file: `news_api_key="YOUR_API_KEY"`
4. Build and run the app on an emulator or physical device

## Development Steps

1. Initial commit with basic project setup
2. Integrated Hilt for dependency injection
3. Added Retrofit for network requests and API service
4. Implemented paging for search results
5. Added Room database for storing favorite articles
6. Designed the home screen layout with tabs
7. Added screen for searching news articles
8. Added screen to display favorite articles
9. Added functionality to sync search articles with favorite state
