package com.perlu_dilindungi.ui.detailHealthCenter

import androidx.lifecycle.*
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.data.room.HealthCenterDao
import kotlinx.coroutines.launch

class DetailHealthCenterViewModel(private val healthCenterDao: HealthCenterDao) : ViewModel() {
    // Store the selected Health Center.
    private var _healthCenter = MutableLiveData<HealthCenter>()
    val healthCenter: LiveData<HealthCenter> = _healthCenter

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    fun insertHealthCenter(healthCenter: HealthCenter) {
        viewModelScope.launch {
            healthCenterDao.insert(healthCenter)
        }
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteHealthCenter(healthCenter: HealthCenter) {
        viewModelScope.launch {
            healthCenterDao.delete(healthCenter)
        }
    }

    /**
     * Check any existing data
     */
    fun checkExist(healthCenter: HealthCenter): LiveData<Boolean> {
        return healthCenterDao.faskesExist(healthCenter.id).asLiveData()
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class DetailHealthCenterViewFactory(private val healthCenterDao: HealthCenterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailHealthCenterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetailHealthCenterViewModel(healthCenterDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}