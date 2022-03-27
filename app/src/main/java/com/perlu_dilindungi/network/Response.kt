package com.perlu_dilindungi.network

import com.perlu_dilindungi.data.City
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.data.News
import com.perlu_dilindungi.data.Province
import com.perlu_dilindungi.data.QR
import com.squareup.moshi.Json

/**
 * The response object from the request to /api/get-news
 */
data class NewsResponse(
    val success: Boolean,
    val message: String,
    @Json(name = "count_total")
    val countTotal: Int,
    val results: List<News>
)


/**
 * The response object from request to /api/get-province
 */
data class ProvinceResponse(
    val curr_val: String,
    val message: String,
    val results: List<Province>
)

/**
 * The response object from request to /api/get-city
 */
data class CityResponse(
    val curr_val: String,
    val message: String,
    val results: List<City>
)

/**
 * The response object from the request to /api/get-faskes-vaksinasi
 */
data class HealthCenterResponse(
    val success: Boolean,
    val message: String,
    @Json(name = "count_total")
    val countTotal: Int,
    val data: List<HealthCenter>
)

/**
 * The response object from the request to /check-in
 */
data class CheckInResponse(
    val success: Boolean,
    val code: Int,
    val message: String,
    val data : QR
)