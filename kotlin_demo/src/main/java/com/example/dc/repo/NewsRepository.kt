package com.example.dc.repo

import com.example.dc.datasource.NewsRemoteDataSource
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class NewsRepository( private val newsRemoteDataSource: NewsRemoteDataSource) {
    // Mutex to make writes to cached values thread-safe.
    private val latestNewsMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestNews: List<String> = emptyList()

    suspend fun fetchLatestNews(refresh: Boolean = false): List<String> = latestNews


}