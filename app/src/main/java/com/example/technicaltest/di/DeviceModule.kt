package com.example.technicaltest.di

import android.content.Context
import androidx.room.Room
import com.example.technicaltest.dao.HeaterDao
import com.example.technicaltest.dao.LightDao
import com.example.technicaltest.dao.RollerShutterDao
import com.example.technicaltest.dao.UserDao
import com.example.technicaltest.database.DeviceDatabase
import com.example.technicaltest.network.StorageNetwork
import com.example.technicaltest.utils.Constants.DB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {

    @Provides
    @Singleton
    fun provideDevicesCall(): StorageNetwork {
        return Retrofit.Builder()
            .baseUrl("http://storage42.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(StorageNetwork::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DeviceDatabase {
        return Room.databaseBuilder(context, DeviceDatabase::class.java, DB_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaterDao(deviceDatabase: DeviceDatabase): HeaterDao {
        return deviceDatabase.heaterDao()
    }

    @Provides
    @Singleton
    fun provideLightDao(deviceDatabase: DeviceDatabase): LightDao {
        return deviceDatabase.lightDao()
    }

    @Provides
    @Singleton
    fun provideRollerShutterDao(deviceDatabase: DeviceDatabase): RollerShutterDao {
        return deviceDatabase.rollerDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(deviceDatabase: DeviceDatabase): UserDao {
        return deviceDatabase.userDao()
    }

}