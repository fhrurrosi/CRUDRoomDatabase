package com.fhrurrosi.postest4.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.fhrurrosi.postest4.data.AppDatabase
import com.fhrurrosi.postest4.data.Penduduk
import com.fhrurrosi.postest4.data.PendudukRepository
import kotlinx.coroutines.launch

class PendudukViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PendudukRepository
    val allPenduduk: LiveData<List<Penduduk>>

    init {
        val pendudukDao = AppDatabase.getDatabase(application).pendudukDao()
        repository = PendudukRepository(pendudukDao)
        allPenduduk = repository.allPenduduk
    }

    fun insert(penduduk: Penduduk) = viewModelScope.launch {
        repository.insert(penduduk)
    }

    fun update(penduduk: Penduduk) = viewModelScope.launch {
        repository.update(penduduk)
    }

    fun delete(penduduk: Penduduk) = viewModelScope.launch {
        repository.delete(penduduk)
    }
}