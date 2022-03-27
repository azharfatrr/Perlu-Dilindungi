package com.perlu_dilindungi.data.room

import androidx.room.*
import com.perlu_dilindungi.data.HealthCenter
import kotlinx.coroutines.flow.Flow

/**
 * Database access object to access the Inventory database
 */
@Dao
interface HealthCenterDao {

    @Query("SELECT * from healthCenter ORDER BY id ASC")
    fun getAllFaskes(): Flow<List<HealthCenter>>

    @Query("SELECT * from healthCenter WHERE id = :id")
    fun getFaskes(id: Int): Flow<HealthCenter>

    @Query("SELECT EXISTS (SELECT 1 FROM healthCenter WHERE id = :id)")
    fun faskesExist(id: Int): Flow<Boolean>

    // Specify the conflict strategy as IGNORE, when the user tries to add an
    // existing Item into the database Room ignores the conflict.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(faskes: HealthCenter)

    @Update
    suspend fun update(faskes: HealthCenter)

    @Delete
    suspend fun delete(faskes: HealthCenter)
}