package com.fhrurrosi.postest4.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PendudukDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(penduduk: Penduduk)

    @Update
    suspend fun update(penduduk: Penduduk)

    @Delete
    suspend fun delete(penduduk: Penduduk)

    @Query("SELECT * FROM penduduk ORDER BY id DESC")
    fun getAllPenduduk(): LiveData<List<Penduduk>>

    @Query("SELECT * FROM penduduk WHERE id = :id")
    suspend fun getPendudukById(id: Int): Penduduk?
}