package com.perlu_dilindungi.network

import com.perlu_dilindungi.data.QrRequest
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "https://perludilindungi.herokuapp.com"

object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(NULL_TO_EMPTY_STRING_ADAPTER)
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/**
 * An interface that expose the [getNews], [getProvince], [getCity], [getHealthCenter], [checkIn] methods
 */
interface PerluDilindungiService {
    /**
     * Returns a [NewsResponse] and this method can be called from a Coroutine.
     */
    @GET("/api/get-news")
    suspend fun getNews(): NewsResponse

    /**
     * Returns a [ProvinceResponse] and this method can be called from a Coroutine.
     */
    @GET("/api/get-province")
    suspend fun getProvince(): ProvinceResponse

    /**
     * Returns a [CityResponse] and this method can be called from a Coroutine.
     */
    @GET("/api/get-city")
    suspend fun getCity(
        @Query("start_id") province: String
    ): CityResponse

    /**
     * Returns a [HealthCenterResponse] and this method can be called from a Coroutine.
     */
    @GET("/api/get-faskes-vaksinasi")
    suspend fun getHealthCenter(
        @Query("province") province: String,
        @Query("city") city: String
    ): HealthCenterResponse

    /**
     * Returns a [CheckInResponse] and this method can be called from a Coroutine.
     */

    @POST("/check-in")
    suspend fun postQRScanner(
        @Body req : QrRequest
    ): CheckInResponse
}



/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object PerluDilindungiApi {
    val retrofitService: PerluDilindungiService by lazy { retrofit.create(PerluDilindungiService::class.java) }
}