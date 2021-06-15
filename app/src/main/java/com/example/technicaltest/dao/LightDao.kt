package com.example.technicaltest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.technicaltest.models.devices.Light
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface LightDao {

    @Insert
    fun insertLights(listHeaters: MutableList<Light>): Completable

    @Query("SELECT * FROM light")
    fun getAllLight(): Flowable<MutableList<Light>>

    @Query("SELECT * FROM light WHERE id = :id")
    fun getLight(id: Int): Flowable<Light>

    @Query("UPDATE light SET mode = :mode WHERE id = :id")
    fun updateModeLight(mode: String, id: Int): Completable

    @Query("UPDATE light SET intensity = :intensity WHERE id = :id")
    fun updateIntensityLight(intensity: Int, id: Int): Completable

    @Query("DELETE FROM light WHERE id = :id")
    fun deleteLight(id: Int)
}