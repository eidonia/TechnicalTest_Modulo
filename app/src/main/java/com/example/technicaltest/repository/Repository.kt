package com.example.technicaltest.repository

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import com.example.technicaltest.dao.HeaterDao
import com.example.technicaltest.dao.LightDao
import com.example.technicaltest.dao.RollerShutterDao
import com.example.technicaltest.dao.UserDao
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.models.response.BTDevice
import com.example.technicaltest.models.response.User
import com.example.technicaltest.network.StorageNetwork
import com.example.technicaltest.utils.Constants.HEATER
import com.example.technicaltest.utils.Constants.LIGHT
import com.example.technicaltest.utils.Constants.ROLLER_SHUTTER
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository @Inject constructor(
    private val storageNetwork: StorageNetwork,
    private val context: Application,
    private val heaterDao: HeaterDao,
    private val lightDao: LightDao,
    private val rollerShutterDao: RollerShutterDao,
    private val userDao: UserDao
) {


    fun getData(): MutableLiveData<Boolean> {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit()
            .putBoolean("alreadyDone", true)
            .apply()
        val mutableLiveData = MutableLiveData<Boolean>()
        storageNetwork.getData()
            .subscribeOn(Schedulers.io())
            .map {
                val user = it.user
                user?.addressString = it.user?.createAddressString()
                return@map user
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<User>() {
                override fun onNext(t: User?) {
                    if (t != null) {
                        insertUserToRoom(t, mutableLiveData)
                    }
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }

            })

        storageNetwork.getData()
            .subscribeOn(Schedulers.io())
            .map {
                val listDevices = mutableListOf<Heater>()
                for (device: BTDevice in it.devices!!) {
                    if (device.productType.equals(HEATER)) {
                        val heater = Heater(
                            id = device.id,
                            temperature = device.temperature,
                            mode = device.mode,
                            deviceName = device.deviceName,
                            productType = device.productType
                        )
                        listDevices.add(heater)
                    }
                }
                return@map listDevices
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MutableList<Heater>>() {
                override fun onNext(t: MutableList<Heater>?) {
                    if (t != null) {
                        insertHeaterToRoom(t)
                    }
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }

            })

        storageNetwork.getData()
            .subscribeOn(Schedulers.io())
            .map {
                val listDevices = mutableListOf<Light>()
                for (device: BTDevice in it.devices!!) {
                    if (device.productType.equals(LIGHT)) {
                        val light = Light(
                            id = device.id,
                            intensity = device.intensity,
                            mode = device.mode,
                            deviceName = device.deviceName,
                            productType = device.productType
                        )
                        listDevices.add(light)
                    }
                }
                return@map listDevices
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MutableList<Light>>() {
                override fun onNext(t: MutableList<Light>?) {
                    if (t != null) {
                        insertLightToRoom(t)
                    }
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }

            })

        storageNetwork.getData()
            .subscribeOn(Schedulers.io())
            .map {
                val listDevices = mutableListOf<RollerShutter>()
                for (device: BTDevice in it.devices!!) {
                    if (device.productType.equals(ROLLER_SHUTTER)) {
                        val rollerShutter = RollerShutter(
                            id = device.id,
                            position = device.position,
                            deviceName = device.deviceName,
                            productType = device.productType
                        )
                        listDevices.add(rollerShutter)
                    }
                }
                return@map listDevices
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableObserver<MutableList<RollerShutter>>() {
                override fun onNext(t: MutableList<RollerShutter>?) {
                    if (t != null) {
                        insertRollerShutterToRoom(t)
                    }
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }

            })

        return mutableLiveData
    }

    private fun insertUserToRoom(user: User, mutableLiveData: MutableLiveData<Boolean>) {
        user.email = "${user.firstName}.${user.lastName}@gmail.com"
        userDao.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onComplete() {
                    mutableLiveData.postValue(true)
                }

                override fun onError(e: Throwable?) {
                }

            })
    }

    private fun insertHeaterToRoom(listHeater: MutableList<Heater>) {
        heaterDao.insertHeaters(listHeater)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                }

            })
    }

    private fun insertLightToRoom(listLight: MutableList<Light>) {
        lightDao.insertLights(listLight)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                }

            })
    }

    private fun insertRollerShutterToRoom(listRollerShutter: MutableList<RollerShutter>) {
        rollerShutterDao.insertHeaters(listRollerShutter)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable?) {
                }

            })
    }
}