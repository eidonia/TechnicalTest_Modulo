package com.example.technicaltest.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.repository.Repository
import com.example.technicaltest.repository.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository, private val roomRepo: RoomRepo): ViewModel() {

    fun getData(): MutableLiveData<Boolean> {
        return repository.getData()
    }

    fun getUserName(): MutableLiveData<String> {
        return roomRepo.getUserName()
    }

    fun getHeaterList(): MutableLiveData<MutableList<Heater>> {
        return roomRepo.getListHeaters()
    }

    fun getLightList(): MutableLiveData<MutableList<Light>> {
        return roomRepo.getListLights()
    }

    fun getRollerShutterlist(): MutableLiveData<MutableList<RollerShutter>> {
        return roomRepo.getListRollerShutter()
    }

    fun deleteHeater(id: Int) {
        roomRepo.deleteHeater(id)
    }

    fun deleteLight(id: Int) {
        roomRepo.deleteLight(id)
    }

    fun deleteShutterRoller(id: Int) {
        roomRepo.deleteRollerShutter(id)
    }
}