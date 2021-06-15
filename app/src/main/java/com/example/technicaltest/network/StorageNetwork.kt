package com.example.technicaltest.network

import com.example.technicaltest.models.response.Devices
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface StorageNetwork {

    @GET("modulotest/data.json")
    fun getData(): Observable<Devices>


}