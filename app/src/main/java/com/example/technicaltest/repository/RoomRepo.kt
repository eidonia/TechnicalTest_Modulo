package com.example.technicaltest.repository

import androidx.lifecycle.MutableLiveData
import com.example.technicaltest.dao.HeaterDao
import com.example.technicaltest.dao.LightDao
import com.example.technicaltest.dao.RollerShutterDao
import com.example.technicaltest.dao.UserDao
import com.example.technicaltest.models.devices.Heater
import com.example.technicaltest.models.devices.Light
import com.example.technicaltest.models.devices.RollerShutter
import com.example.technicaltest.models.response.User
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subscribers.ResourceSubscriber
import javax.inject.Inject

class RoomRepo @Inject constructor(
    private val heaterDao: HeaterDao,
    private val lightDao: LightDao,
    private val rollerShutterDao: RollerShutterDao,
    private val userDao: UserDao
) {

    fun getUserName(): MutableLiveData<String> {
        val mutableLiveData = MutableLiveData<String>()
        userDao.getUser()
            .subscribeOn(Schedulers.io())
            .map {
                return@map "${it.firstName} ${it.lastName}"
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<String>() {
                override fun onNext(t: String?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }

            })

        return mutableLiveData
    }

    fun getListHeaters(): MutableLiveData<MutableList<Heater>> {
        val mutableLiveData = MutableLiveData<MutableList<Heater>>()
        heaterDao.getAllHeater()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<MutableList<Heater>>() {
                override fun onNext(t: MutableList<Heater>?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {

                }

                override fun onComplete() {

                }

            })


        return mutableLiveData
    }

    fun deleteHeater(id: Int) {
        heaterDao.deleteHeater(id)

    }

    fun getListLights(): MutableLiveData<MutableList<Light>> {
        val mutableLiveData = MutableLiveData<MutableList<Light>>()
        lightDao.getAllLight()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<MutableList<Light>>() {
                override fun onNext(t: MutableList<Light>?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }

            })
        return mutableLiveData
    }

    fun getListRollerShutter(): MutableLiveData<MutableList<RollerShutter>> {
        val mutableLiveData = MutableLiveData<MutableList<RollerShutter>>()
        rollerShutterDao.getAllRollerShutter()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<MutableList<RollerShutter>>() {
                override fun onNext(t: MutableList<RollerShutter>?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }

            })
        return mutableLiveData
    }

    fun deleteLight(id: Int) {
        lightDao.deleteLight(id)
    }

    fun deleteRollerShutter(id: Int) {
        rollerShutterDao.deleteRollerShutter(id)
    }

    fun getRollerShutter(id: Int): MutableLiveData<RollerShutter> {
        val mutableLiveData = MutableLiveData<RollerShutter>()
        rollerShutterDao.getRollerShutter(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<RollerShutter>() {
                override fun onNext(t: RollerShutter?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }
            })

        return mutableLiveData
    }

    fun getHeater(id: Int): MutableLiveData<Heater> {
        val mutableLiveData = MutableLiveData<Heater>()
        heaterDao.getHeater(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<Heater>() {
                override fun onNext(t: Heater?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }

            })
        return mutableLiveData
    }
    fun getLight(id: Int): MutableLiveData<Light> {
        val mutableLiveData = MutableLiveData<Light>()
        lightDao.getLight(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<Light>() {
                override fun onNext(t: Light?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {
                }

                override fun onComplete() {
                }

            })
        return mutableLiveData
    }
    
    fun updateModeLight(mode: String, id: Int) {
        lightDao.updateModeLight(mode, id)
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

    fun updateIntensityLight(intensity: Int, id: Int) {
        lightDao.updateIntensityLight(intensity, id)
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

    fun updateModeHeater(mode: String, id: Int) {
        heaterDao.updateModeHeater(mode, id)
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

    fun updateTempHeater(temperature: Double, id: Int) {
        heaterDao.updateTempHeater(temperature, id)
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

    fun updatePositionRollerShutter(position: Int, id: Int) {
        rollerShutterDao.updatePositionRollerShutter(position, id)
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

    fun getUser(): MutableLiveData<User> {
        val mutableLiveData = MutableLiveData<User>()
        userDao.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ResourceSubscriber<User>() {
                override fun onNext(t: User?) {
                    mutableLiveData.postValue(t)
                }

                override fun onError(t: Throwable?) {

                }

                override fun onComplete() {

                }

            })

        return mutableLiveData
    }

    fun updateUser(firstname: String, lastname: String, birthdate: Long, address: String, email: String) {
        userDao.updateNameuser(firstname, lastname, birthdate, address, email)
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