package com.amg.newsapp.models

class NoNetworkException(message: String = ""): Throwable(message)
class SearchNotFoundException(message: String = ""): Throwable(message)
class SearchMinCharacterException(message: String = ""): Throwable(message)