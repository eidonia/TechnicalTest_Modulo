package com.example.technicaltest.ui.lightPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.repository.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LightViewModel @Inject constructor(private val roomRepo: RoomRepo): ViewModel() {

    fun getLightDevice(id: Int): MutableLiveData<Light> {
        return roomRepo.getLight(id)
    }

    fun updateModeLight(mode: String, id: Int) {
        roomRepo.updateModeLight(mode, id)
    }

    fun updateIntensityLight(intensity: Int, id: Int) {
        roomRepo.updateIntensityLight(intensity, id)
    }
}