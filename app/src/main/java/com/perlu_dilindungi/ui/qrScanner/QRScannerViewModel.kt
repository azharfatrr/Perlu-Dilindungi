package com.perlu_dilindungi.ui.qrScanner

import android.location.Location
import android.media.Image
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perlu_dilindungi.R
import com.perlu_dilindungi.config.ApiStatus
import com.perlu_dilindungi.config.Tag
import com.perlu_dilindungi.data.News
import com.perlu_dilindungi.data.QR
import com.perlu_dilindungi.data.QrRequest
import com.perlu_dilindungi.network.NewsResponse
import com.perlu_dilindungi.network.PerluDilindungiApi
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class QRScannerViewModel : ViewModel() {
    //tipe data yang diperlukan atau yang akan digunakan fragment/activity
    // The internal MutableLiveData that stores the status of the most recent request

    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus> = _status

    private val _qr = MutableLiveData<QR>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val qr: LiveData<QR> = _qr

    private var _location = MutableLiveData<Location>()
    val location: LiveData<Location> = _location

    private var _qrCode = MutableLiveData<String>()
    val qrCode: LiveData<String> = _qrCode

    private var _checkInStatus = MutableLiveData<Boolean>(false)
    val checkInStatus: LiveData<Boolean> = _checkInStatus

    private var _reason = MutableLiveData<String>()
    val reason: LiveData<String> = _reason

    private var _reasonfalse = MutableLiveData<String>()
    val reasonfalse: LiveData<String> = _reasonfalse



    /**
     * POST QR Code to the Perlu Dilindungi API Retrofit service and show results
     */
    fun postQRScanner() {
        var req = QrRequest( qrCode = _qrCode.value,
            latitude = _location.value?.latitude,
            longitude = _location.value?.longitude
        )


        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val checkInResponse = PerluDilindungiApi.retrofitService.postQRScanner(req)
                _qr.value = checkInResponse.data
                _status.value = ApiStatus.DONE
                when(qr.value?.userStatus) {
                    "red", "black" -> _checkInStatus.value = false
                    "yellow", "green" -> _checkInStatus.value = true
                    else -> _checkInStatus.value = false
                }
                if(_checkInStatus.value == true) {
                    _reason.value = "Berhasil"
                    _reasonfalse.value = ""

                }
                else {
                    _reason.value = "Gagal"
                    _reasonfalse.value = qr.value?.reason.toString()
                }
                Log.v("Request-Body", req.toString())
                Log.v("Response", _qr.value.toString())
            } catch (e: Exception) {
                Log.e(Tag.NETWORK, "Cannot get the data ${e.message}")
                _status.value = ApiStatus.ERROR
            }
        }

    }
    /**
     * Update the viewModel location value.
     */
    fun updateLocation(location: Location) {
        _location.value = location
    }

    fun getQrCode(qrCode: String) {
        _qrCode.value = qrCode
    }

}