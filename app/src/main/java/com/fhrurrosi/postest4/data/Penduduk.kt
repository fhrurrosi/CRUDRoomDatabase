package com.fhrurrosi.postest4.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "penduduk")
data class Penduduk(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nama: String,
    val nik: String,
    val kecamatan: String,
    val kabupaten: String,
    val desa: String,
    val rt: String,
    val rw: String,
    val jenisKelamin: String,
    val statusPernikahan: String
)