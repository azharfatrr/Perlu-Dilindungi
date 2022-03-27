package com.perlu_dilindungi.ui.detailHealthCenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.perlu_dilindungi.data.HealthCenter

class DetailHealthCenterSharedViewModel : ViewModel() {

    // Store the selected Health Center.
    private var _healthCenter = MutableLiveData<HealthCenter>()
    val healthCenter: LiveData<HealthCenter> = _healthCenter

    // Set the Selected healthCenter.
    fun setHealthCenter(healthCenter: HealthCenter) {
        _healthCenter.value = healthCenter
    }

}