package com.example.dc

interface NewsApi {

    // Interface that provides a way to make network requests with suspend functions
    suspend fun fetchLatestNews(): List<String>


}