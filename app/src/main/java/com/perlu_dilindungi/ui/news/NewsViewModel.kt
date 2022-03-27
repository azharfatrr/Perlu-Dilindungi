package com.perlu_dilindungi.ui.news

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perlu_dilindungi.config.ApiStatus
import com.perlu_dilindungi.config.Tag
import com.perlu_dilindungi.data.News
import com.perlu_dilindungi.network.NewsResponse
import com.perlu_dilindungi.network.PerluDilindungiApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NewsViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of News
    // with new values
    private val _news = MutableLiveData<List<News>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val news: LiveData<List<News>> = _news


    init {
        getNews()
    }


    /**
     * Gets News information from the Perlu Dilindungi API Retrofit service and updates the
     * [News] [List] [LiveData].
     */
    private fun getNews() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val newsResponse = PerluDilindungiApi.retrofitService.getNews()
                _news.value = parseNewsResponse(newsResponse)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(Tag.NETWORK, "Cannot get the data ${e.message}")
                _status.value = ApiStatus.ERROR
                _news.value = listOf()
            }
        }
    }

    /**
     * Parse the NewsResponse into formatted List<NewsData>
     */
    private fun parseNewsResponse(newsResponse: NewsResponse): List<News> {
        val newsData = newsResponse.results
        val format = DateTimeFormatter.ofPattern("dd MMMM yyyy")

        newsData.map {
            val date = LocalDate.parse(it.pubDate, DateTimeFormatter.RFC_1123_DATE_TIME)
            it.pubDate = date.format(format)
        }

        return newsData
    }
}