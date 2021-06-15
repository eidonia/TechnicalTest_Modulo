package com.example.technicaltest.ui.mainActivity

import android.app.ActivityOptions
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.transition.Fade
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityMainBinding
import com.example.technicaltest.models.devices.Device
import com.example.technicaltest.ui.heaterPage.HeaterActivity
import com.example.technicaltest.ui.lightPage.LightActivity
import com.example.technicaltest.ui.rollerShutterPage.RollerShutterActivity
import com.example.technicaltest.ui.settings.UserSettings
import com.example.technicaltest.utils.Constants.HEATER
import com.example.technicaltest.utils.Constants.LIGHT
import com.example.technicaltest.utils.Constants.ROLLER_SHUTTER
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DeviceAdapter.AdapterCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var deviceAdapter: DeviceAdapter
    private var listDevices = mutableListOf<Device>()

    override fun onCreate(savedInstanceState: Bundle?) {
        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            exitTransition = Fade()
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> binding.btnDarkMode.visibility = View.INVISIBLE
            Configuration.UI_MODE_NIGHT_NO -> binding.btnLightMode.visibility = View.INVISIBLE
        }

        deviceAdapter = DeviceAdapter(this)
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.listDevice.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.listDevice.adapter = deviceAdapter

        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("alreadyDone", false)) {
            mainActivityViewModel.getData().observe(this, {
                if (it) {
                    mainActivityViewModel.getUserName().observe(this, { name ->
                        loaded()
                        binding.textWelcome.text = getString(R.string.textWelcome, name)
                    })
                }
            })
        } else {
            mainActivityViewModel.getUserName().observe(this, { name ->
                loaded()
                binding.textWelcome.text = getString(R.string.textWelcome, name)
            })
        }

        binding.btnDarkMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            binding.btnDarkMode.visibility = View.INVISIBLE
            binding.btnLightMode.visibility = View.VISIBLE
        }

        binding.btnLightMode.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            binding.btnDarkMode.visibility = View.VISIBLE
            binding.btnLightMode.visibility = View.INVISIBLE
        }

        binding.btnUserPage.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    UserSettings::class.java
                )
            )
        }

        binding.checkHeater.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mainActivityViewModel.getHeaterList().observe(this, {
                    if (!checkIfExist(it[0].id)) {
                        listDevices.addAll(it)
                        deviceAdapter.addList(listDevices)
                    }
                })
            } else {
                deleteInList(HEATER)
            }
        }

        binding.checkLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mainActivityViewModel.getLightList().observe(this, {
                    if (!checkIfExist(it[0].id)) {
                        listDevices.addAll(it)
                        deviceAdapter.addList(listDevices)
                    }
                })
            } else {
                deleteInList(LIGHT)
            }
        }

        binding.checkRollingShutter.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                mainActivityViewModel.getRollerShutterlist().observe(this, {
                    if (!checkIfExist(it[0].id)) {
                        listDevices.addAll(it)
                        deviceAdapter.addList(listDevices)
                    }
                })
            } else {
                deleteInList(ROLLER_SHUTTER)
            }
        }
    }

    private fun checkIfExist(it: Int?): Boolean {
        for (device: Device in listDevices) {
            if (device.id == it) return true
        }

        return false
    }

    private fun deleteInList(s: String) {
        listDevices.removeIf {
            it.productType.equals(s)
        }
        deviceAdapter.addList(listDevices)
    }

    private fun init() {
        binding.textWelcome.visibility = View.INVISIBLE
        binding.btnUserPage.visibility = View.INVISIBLE
        binding.checkBoxconstraint.visibility = View.INVISIBLE
        binding.listDevice.visibility = View.INVISIBLE
        binding.text.visibility = View.INVISIBLE
    }

    private fun loaded() {
        binding.loading.visibility = View.INVISIBLE
        binding.textWelcome.visibility = View.VISIBLE
        binding.btnUserPage.visibility = View.VISIBLE
        binding.checkBoxconstraint.visibility = View.VISIBLE
        binding.listDevice.visibility = View.VISIBLE
        binding.text.visibility = View.VISIBLE
    }

    override fun deleteDevice(device: Device) {
        val contextView = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)

        Snackbar.make(contextView, getString(R.string.deleteDevice), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.btnYesDelete)) {
                when (device.productType) {
                    HEATER -> {
                        mainActivityViewModel.deleteHeater(device.id!!)
                        deleteInList(HEATER)
                    }
                    LIGHT -> {
                        mainActivityViewModel.deleteLight(device.id!!)
                        deleteInList(LIGHT)

                    }
                    ROLLER_SHUTTER -> {
                        mainActivityViewModel.deleteShutterRoller(device.id!!)
                        deleteInList(ROLLER_SHUTTER)
                    }
                }

            }
            .show()
    }

    override fun openPageDevice(device: Device) {
        when (device.productType) {
            HEATER -> {
                val intent = Intent(this, HeaterActivity::class.java)
                intent.putExtra("HeaterID", device.id)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            LIGHT -> {
                val intent = Intent(this, LightActivity::class.java)
                intent.putExtra("LightID", device.id)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
            ROLLER_SHUTTER -> {
                val intent = Intent(this, RollerShutterActivity::class.java)
                intent.putExtra("RollerShutterID", device.id)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

}