package com.example.technicaltest.ui.rollerShutterPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.repository.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RollerShutterViewModel @Inject constructor(private val roomRepo: RoomRepo): ViewModel() {

    fun getRollerShutter(id: Int): MutableLiveData<RollerShutter> {
        return roomRepo.getRollerShutter(id)
    }

    fun updatePositionRollerShutter(position: Int, id: Int) {
        roomRepo.updatePositionRollerShutter(position, id)
    }
}