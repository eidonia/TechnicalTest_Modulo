package com.example.technicaltest.ui.heaterPage

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityHeaterBinding
import com.example.technicaltest.models.devices.Heater
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HeaterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHeaterBinding
    private lateinit var heaterViewModel: HeaterViewModel
    private var heater: Heater? = null
    private var temp: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeaterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        heaterViewModel = ViewModelProvider(this).get(HeaterViewModel::class.java)

        heaterViewModel.getHeaterDevice(intent.getIntExtra("HeaterID", 0)).observe(this, {
            heater = it
            createUI()
        })

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btnOn -> {
                    binding.btnCold.visibility = View.VISIBLE
                    binding.btnHot.visibility = View.VISIBLE
                    heaterViewModel.updateModeHeater("ON", heater?.id!!)
                }

                R.id.btnOff -> {
                    binding.btnCold.visibility = View.INVISIBLE
                    binding.btnHot.visibility = View.INVISIBLE
                    heaterViewModel.updateModeHeater("OFF", heater?.id!!)
                }
            }
        }

        binding.btnHot.setOnClickListener {
            if (temp == 28.0) {
                Toast.makeText(this, getString(R.string.maxTemp), Toast.LENGTH_LONG).show()
            } else {
                temp += 0.5
                binding.txtTemp.text = "$temp°"
                heaterViewModel.updateTempHeater(temp, heater?.id!!)
            }
        }

        binding.btnCold.setOnClickListener {
            if (temp == 7.0) {
                Toast.makeText(this, "min temp reached", Toast.LENGTH_LONG).show()
            } else {
                temp -= 0.5
                binding.txtTemp.text = "$temp°"
                heaterViewModel.updateTempHeater(temp, heater?.id!!)
            }
        }
    }

    private fun init() {
        binding.textDeviceName.visibility = View.INVISIBLE
        binding.txtTemp.visibility = View.INVISIBLE
        binding.btnCold.visibility = View.INVISIBLE
        binding.btnHot.visibility = View.INVISIBLE
        binding.toggleButton.visibility = View.INVISIBLE
    }

    private fun createUI() {
        binding.textDeviceName.visibility = View.VISIBLE
        binding.loading.visibility = View.INVISIBLE
        binding.txtTemp.visibility = View.VISIBLE
        binding.btnCold.visibility = View.VISIBLE
        binding.btnHot.visibility = View.VISIBLE
        binding.toggleButton.visibility = View.VISIBLE

        binding.txtTemp.text = "${heater?.temperature}°"
        temp = heater?.temperature!!

        if (heater?.mode.equals("OFF")) {
            binding.toggleButton.check(R.id.btnOff)
            binding.btnCold.visibility = View.INVISIBLE
            binding.btnHot.visibility = View.INVISIBLE
        } else {
            binding.toggleButton.check(R.id.btnOn)
            binding.btnCold.visibility = View.VISIBLE
            binding.btnHot.visibility = View.VISIBLE
        }
    }

    override fun onStart() {
        super.onStart()
        if (heater != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (heater != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if (heater != null) {
            createUI()
        } else {
            init()
        }
    }
}