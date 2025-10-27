package com.fhrurrosi.postest4.data

import androidx.lifecycle.LiveData

class PendudukRepository(private val pendudukDao: PendudukDao) {

    val allPenduduk: LiveData<List<Penduduk>> = pendudukDao.getAllPenduduk()

    suspend fun insert(penduduk: Penduduk) {
        pendudukDao.insert(penduduk)
    }

    suspend fun update(penduduk: Penduduk) {
        pendudukDao.update(penduduk)
    }

    suspend fun delete(penduduk: Penduduk) {
        pendudukDao.delete(penduduk)
    }
}