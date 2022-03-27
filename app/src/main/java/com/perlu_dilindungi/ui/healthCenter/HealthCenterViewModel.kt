package com.perlu_dilindungi.ui.healthCenter

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perlu_dilindungi.config.ApiStatus
import com.perlu_dilindungi.config.Tag
import com.perlu_dilindungi.data.City
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.data.Province
import com.perlu_dilindungi.network.PerluDilindungiApi
import kotlinx.coroutines.launch

class HealthCenterViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private var _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    val baseMessage = "Silahkan pilih Provinsi dan Kota Anda"
    private var _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> = _statusMessage

    // The value in provinces is taken from Province.Value
    private var _provinces = MutableLiveData<List<String>>()
    val provinces: LiveData<List<String>> = _provinces

    // The value in cities is taken from City.Value
    private var _cities = MutableLiveData<List<String>>()
    val cities: LiveData<List<String>> = _cities

    private var _healthCenters = MutableLiveData<List<HealthCenter>>()
    val healthCenters: LiveData<List<HealthCenter>> = _healthCenters

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    init {
        getProvinces()
    }

    /**
     * Initialize the message with baseMessage.
     */
    private fun initMessage() {
        _status.value = ApiStatus.INIT
        _statusMessage.value = baseMessage
    }

    /**
     * Gets province from the Perlu Dilindungi API Retrofit service and updates the
     * [Province] [List] [LiveData].
     */
    private fun getProvinces() {
        viewModelScope.launch {
            try {
                val provinceResponse = PerluDilindungiApi.retrofitService.getProvince()
                _provinces.value = provinceResponse.results.map { it.value }
                initMessage()
            } catch (e: Exception) {
                Log.e(Tag.NETWORK, "Cannot get the data ${e.message}")
                _status.value = ApiStatus.ERROR
            }
        }
    }

    /**
     * Gets city from the Perlu Dilindungi API Retrofit service and updates the
     * [City] [List] [LiveData].
     */
    fun getCities(province: String) {
        viewModelScope.launch {
            try {
                val cityResponse = PerluDilindungiApi.retrofitService.getCity(province)
                _cities.value = cityResponse.results.map { it.value }
                initMessage()
            } catch (e: Exception) {
                Log.e(Tag.NETWORK, "Cannot get the data ${e.message}")
                _status.value = ApiStatus.ERROR
                _cities.value = listOf()
            }
        }
    }

    /**
     * Gets city from the Perlu Dilindungi API Retrofit service and updates the
     * [HealthCenter] [List] [LiveData].
     */
    fun getHealthCenters(province: String, city: String) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val healthCenterResponse = PerluDilindungiApi
                    .retrofitService.getHealthCenter(province, city)
                val healthCenters = filterHealthCenter(healthCenterResponse.data)
                _healthCenters.value = healthCenters
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                Log.e(Tag.NETWORK, "Cannot get the data ${e.message}")
                _status.value = ApiStatus.ERROR
                _healthCenters.value = listOf()
            }
        }
    }

    /**
     * Filter the health center by distance.
     */
    private fun filterHealthCenter(healthCenters: List<HealthCenter>): List<HealthCenter> {
        val myLatitude = _location.value?.latitude ?: 0.0
        val myLongitude = _location.value?.longitude ?: 0.0

        return healthCenters.sortedWith() { a: HealthCenter, b: HealthCenter ->
            // Calculate distance between two Location.
            val resultsA = FloatArray(1)
            val resultsB = FloatArray(1)
            Location.distanceBetween(myLatitude, myLongitude, a.latitude.toDouble(), a.longitude.toDouble(), resultsA)
            Location.distanceBetween(myLatitude, myLongitude, b.latitude.toDouble(), b.longitude.toDouble(), resultsB)
            return@sortedWith (resultsA[0] - resultsB[0]).toInt()
        }.slice(0..4)
    }

    /**
     * Remove all healthCenter items.
     */
    fun clearHealthCenters() {
        _healthCenters.value = listOf()
    }

    /**
     * Update the viewModel location value.
     */
    fun updateLocation(location: Location) {
        _location.value = location
    }
}
