package com.example.technicaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.repository.Repository
import com.example.technicaltest.repository.RoomRepo
import com.example.technicaltest.ui.mainActivity.MainActivityViewModel
import org.junit.Assert
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.util.*

class MainActivityViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    var mainActivityViewModel: MainActivityViewModel? = null

    lateinit var repository: Repository
    lateinit var roomRepo: RoomRepo

    var mutableBool= MutableLiveData<Boolean>()
    var mutableString= MutableLiveData<String>()
    var mutableHeater= MutableLiveData<MutableList<Heater>>()

    var listHeater = mutableListOf<Heater>(
        Heater(id = 1, mode = "ON", temperature = 25.0, deviceName = "heater", productType = "heater"),
        Heater(id = 2, mode = "OFF", temperature = 10.0, deviceName = "heater - Bedroom", productType = "heater"),
        Heater(id = 1, mode = "ON", temperature = 15.0, deviceName = "heater - Kitchen", productType = "heater")
    )

    @Before
    fun setUp() {
        repository = mock(Repository::class.java)
        roomRepo = mock(RoomRepo::class.java)
        mainActivityViewModel = MainActivityViewModel(repository, roomRepo)
    }

    @Test
    fun getData_In_Room() {
        mutableBool.value = true
        Mockito.`when`(repository.getData()).thenReturn(mutableBool)
        Assert.assertNotNull(repository.getData())
        repository.getData().observeForever {
            assertEquals(true, it)
        }
    }

    @Test
    fun getUserName_In_Room() {
        val name = "Bastien"
        mutableString.value = name
        Mockito.`when`(roomRepo.getUserName()).thenReturn(mutableString)
        Assert.assertNotNull(roomRepo.getUserName())
        roomRepo.getUserName().observeForever {
            assertEquals(name, it)
        }

    }

    @Test
    fun getListHeater_Room() {
        mutableHeater.value = listHeater
        Mockito.`when`(roomRepo.getListHeaters()).thenReturn(mutableHeater)
        Assert.assertNotNull(roomRepo.getListHeaters())
        roomRepo.getListHeaters().observeForever {
            assertEquals(listHeater, it)
        }
    }
}