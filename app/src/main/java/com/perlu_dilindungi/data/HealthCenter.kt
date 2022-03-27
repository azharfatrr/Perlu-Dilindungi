package com.perlu_dilindungi.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Entity data class represents a single row in the database.
 */
@Entity(tableName = "healthCenter")
data class HealthCenter(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "kode")
    val kode: String,
    @ColumnInfo(name = "kota")
    val nama: String,
    @ColumnInfo(name = "nama")
    val kota: String,
    @ColumnInfo(name = "provinsi")
    val provinsi: String,
    @ColumnInfo(name = "alamat")
    val alamat: String,
    @ColumnInfo(name = "latitude")
    val latitude: String,
    @ColumnInfo(name = "longitude")
    val longitude: String,
    @ColumnInfo(name = "telp")
    val telp: String,
    @ColumnInfo(name = "jenis_faskes")
    @Json(name = "jenis_faskes")
    val jenisFaskes: String,
    @ColumnInfo(name = "kelas_rs")
    @Json(name = "kelas_rs")
    val kelasRS: String,
    @ColumnInfo(name = "status")
    val status: String
)

// It may not necessary.
data class HealthCenterDetail(
    val id: Int,
    val kode: String,
    val batch: String,
    @Json(name = "divaksin")
    val diVaksin: Int,
    @Json(name = "divaksin_1")
    val diVaksin1: Int,
    @Json(name = "divaksin_2")
    val diVaksin2: Int,
    @Json(name = "batal_vaksin")
    val batalVaksin: Int,
    @Json(name = "batal_vaksin_1")
    val batalVaksin1: Int,
    @Json(name = "batal_vaksin_2")
    val batalVaksin2: Int,
    @Json(name = "pending_vaksin")
    val pendingVaksin: Int,
    @Json(name = "pending_vaksin_1")
    val pendingVaksin1: Int,
    @Json(name = "pending_vaksin_2")
    val pendingVaksin2: Int,
    val tanggal: String,
)