package com.example.dc.datasource

import com.example.dc.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRemoteDataSource(
    private val newsApi: NewsApi,
    private val refreshIntervalMs: Long = 5000
) {
    /**
     * 在 IO 线程中，获取网络数据，在主线程调用是安全的
     */
    suspend fun fetchLatestNews(): List<String> = withContext(Dispatchers.IO) {
        // 将耗时操作移动到 IO 线程中
        newsApi.fetchLatestNews()
    }


}