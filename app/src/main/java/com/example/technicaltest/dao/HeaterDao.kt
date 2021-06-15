package com.example.technicaltest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.technicaltest.models.devices.Heater
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface HeaterDao {

    @Insert
    fun insertHeaters(listHeaters: MutableList<Heater>): Completable

    @Query("SELECT * FROM heater")
    fun getAllHeater(): Flowable<MutableList<Heater>>

    @Query("SELECT * FROM heater WHERE id = :id")
    fun getHeater(id: Int): Flowable<Heater>

    @Query("UPDATE heater SET mode = :mode WHERE id = :id")
    fun updateModeHeater(mode: String, id: Int): Completable

    @Query("UPDATE heater SET temperature = :temperature WHERE id = :id")
    fun updateTempHeater(temperature: Double, id: Int): Completable

    @Query("DELETE FROM heater WHERE id = :id")
    fun deleteHeater(id: Int)
}