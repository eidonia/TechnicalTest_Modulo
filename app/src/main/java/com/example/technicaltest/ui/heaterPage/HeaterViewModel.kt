package com.example.technicaltest.ui.heaterPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.repository.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HeaterViewModel @Inject constructor(private val roomRepo: RoomRepo) : ViewModel() {

    fun getHeaterDevice(id: Int): MutableLiveData<Heater> {
        return roomRepo.getHeater(id)
    }

    fun updateModeHeater(mode: String, id: Int) {
        roomRepo.updateModeHeater(mode, id)
    }

    fun updateTempHeater(temperature: Double, id: Int) {
        roomRepo.updateTempHeater(temperature, id)
    }
}