package com.example.technicaltest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.technicaltest.dao.HeaterDao
import com.example.technicaltest.dao.LightDao
import com.example.technicaltest.dao.RollerShutterDao
import com.example.technicaltest.dao.UserDao
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.models.response.User

@Database(
    entities = [User::class, Heater::class, Light::class, RollerShutter::class],
    version = 1,
    exportSchema = false
)
abstract class DeviceDatabase : RoomDatabase() {

    abstract fun heaterDao(): HeaterDao
    abstract fun userDao(): UserDao
    abstract fun lightDao(): LightDao
    abstract fun rollerDao(): RollerShutterDao
}