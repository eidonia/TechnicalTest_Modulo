package com.example.technicaltest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.repository.Repository
import com.example.technicaltest.repository.RoomRepo
import com.example.technicaltest.ui.lightPage.LightViewModel
import com.example.technicaltest.ui.mainActivity.MainActivityViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LightViewModelTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    var lightViewModel: LightViewModel? = null

    lateinit var roomRepo: RoomRepo

    var mutableLight = MutableLiveData<Light>()

    val light = Light(intensity = 60, mode = "ON", id = 1, deviceName = "Light - Bedroom", productType = "Light")

    @Before
    fun setUp() {
        roomRepo = Mockito.mock(RoomRepo::class.java)
        lightViewModel = LightViewModel(roomRepo)
    }

    @Test
    fun getLight_Device_With_ID() {
        mutableLight.value = light
        Mockito.`when`(roomRepo.getLight(1)).thenReturn(mutableLight)
        Assert.assertNotNull(roomRepo.getLight(1))
        roomRepo.getLight(1).observeForever {
            Assert.assertEquals(light, it)
        }

    }
}