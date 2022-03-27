package com.perlu_dilindungi.ui.bookmark


import android.util.Log
import androidx.lifecycle.*
import com.perlu_dilindungi.config.Tag
import com.perlu_dilindungi.data.HealthCenter
import com.perlu_dilindungi.data.room.HealthCenterDao
import kotlinx.coroutines.launch

class FaskesViewModel(private val healthCenterDao: HealthCenterDao) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Tidak ada faskes yang di-bookmark"
    }
    val text: LiveData<String> = _text

    // Cache all items form the database using LiveData.
    val allFaskes: LiveData<List<HealthCenter>> = healthCenterDao.getAllFaskes().asLiveData()

    /**
     * Inserts the new Item into database.
     */
    fun addNewFaskes(newFaskes: HealthCenter?) {
        Log.v(Tag.TEST, "Masuk5")
//        if (newFaskes?.let { isEntryValid(it) } == true) {
//            Log.v(Tag.TEST, "Masuk7")
//            insertFaskes(newFaskes)
//        }
        if (newFaskes != null) {
            Log.v(Tag.TEST, "Masuk7")
            insertFaskes(newFaskes)
        }
    }

    /**
     * Launching a new coroutine to insert an item in a non-blocking way
     */
    fun insertFaskes(faskes: HealthCenter) {
        viewModelScope.launch {
            healthCenterDao.insert(faskes)
        }
    }

    /**
     * Launching a new coroutine to delete an item in a non-blocking way
     */
    fun deleteItem(faskes: HealthCenter) {
        viewModelScope.launch {
            healthCenterDao.delete(faskes)
        }
    }

    /**
     * Retrieve an item from the repository.
     */
    fun retrieveItem(id: Int): LiveData<HealthCenter> {
        return healthCenterDao.getFaskes(id).asLiveData()
    }

    /**
     * Check any existing data
     */
    fun isExist(faskes: HealthCenter): Boolean {
        Log.v(Tag.TEST, "Masuk4")
        Log.v(Tag.TEST, allFaskes.value.toString())
        return allFaskes.value?.contains(faskes) ?: false
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    fun isEntryValid(faskes: HealthCenter): Boolean {
        Log.v(Tag.TEST, "Masuk6")
        Log.v(Tag.TEST, faskes.toString())
        if (faskes.kode.isBlank() || faskes.nama.isBlank() || faskes.kota.isBlank()
            || faskes.provinsi.isBlank() || faskes.alamat.isBlank() || faskes.latitude.isBlank()
            || faskes.longitude.isBlank() || faskes.telp.isBlank() || faskes.jenisFaskes.isBlank()
            || faskes.kelasRS.isBlank() || faskes.status.isBlank() ) {
            return false
        }
        return true
    }

    /**
     * Returns an instance of the [HealthCenter] entity class with the item info entered by the user.
     * This will be used to add a new entry to the Inventory database.
     */
    private fun getNewFaskesEntry(kode: String, nama: String, kota: String, provinsi: String, alamat: String,
        latitude: String, longitude: String, telp: String, jenisFaskes: String, kelasRS: String, status: String
        ): HealthCenter {
        return HealthCenter(
            kode = kode,
            nama = nama,
            kota = kota,
            provinsi = provinsi,
            alamat = alamat,
            latitude = latitude,
            longitude = longitude,
            telp = telp,
            jenisFaskes = jenisFaskes,
            kelasRS = kelasRS,
            status = status
        )
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class FaskesViewModelFactory(private val healthCenterDao: HealthCenterDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FaskesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FaskesViewModel(healthCenterDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
