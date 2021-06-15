package com.example.technicaltest.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.technicaltest.models.response.User
import com.example.technicaltest.repository.RoomRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val roomRepo: RoomRepo) : ViewModel() {

    fun getUser(): MutableLiveData<User> {
        return roomRepo.getUser()
    }

    fun updateUser(
        firstname: String,
        lastname: String,
        birthdate: Long,
        address: String,
        email: String
    ) {
        roomRepo.updateUser(firstname, lastname, birthdate, address, email)
    }

}