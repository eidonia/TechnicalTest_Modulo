package com.example.technicaltest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.technicaltest.models.devices.RollerShutter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface RollerShutterDao {

    @Insert
    fun insertHeaters(listHeaters: MutableList<RollerShutter>): Completable

    @Query("SELECT * FROM rollershutter")
    fun getAllRollerShutter(): Flowable<MutableList<RollerShutter>>

    @Query("SELECT * FROM rollershutter WHERE id = :id")
    fun getRollerShutter(id: Int): Flowable<RollerShutter>

    @Query("UPDATE rollershutter SET position = :position WHERE id = :id")
    fun updatePositionRollerShutter(position: Int, id: Int): Completable

    @Query("DELETE FROM rollershutter WHERE id = :id")
    fun deleteRollerShutter(id: Int)
}