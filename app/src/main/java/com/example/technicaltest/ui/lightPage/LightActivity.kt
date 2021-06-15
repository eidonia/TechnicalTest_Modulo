package com.example.technicaltest.ui.lightPage

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityLightBinding
import com.example.technicaltest.models.devices.Light
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LightActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLightBinding
    private lateinit var lightViewModel: LightViewModel
    private var light: Light? = null
    private var gradientRadiusVal: Float = 0f
    private lateinit var gradientDrawable: GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLightBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layout = findViewById<ImageView>(R.id.gradient)

        gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(0xFFffff8d.toInt(), 0xFF00616261.toInt())
        )
        gradientDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        gradientDrawable.cornerRadius = 30f

        layout.setBackgroundDrawable(gradientDrawable)

        lightViewModel = ViewModelProvider(this).get(LightViewModel::class.java)

        lightViewModel.getLightDevice(intent.getIntExtra("LightID", 0)).observe(this, {
            light = it

            createUI()

        })

        binding.toggleButton.addOnButtonCheckedListener { _, checkedId, _ ->
            when (checkedId) {
                R.id.btnOn -> {
                    binding.sliderLight.value = 50f
                    gradientRadiusVal = light?.intensity?.toFloat()!! * 10
                    gradientDrawable.gradientRadius = gradientRadiusVal
                    binding.sliderLight.visibility = View.VISIBLE
                    lightViewModel.updateModeLight("ON", light?.id!!)
                }

                R.id.btnOff -> {
                    gradientRadiusVal = 0f
                    gradientDrawable.gradientRadius = gradientRadiusVal
                    binding.sliderLight.value = 0f
                    binding.sliderLight.visibility = View.INVISIBLE
                    lightViewModel.updateModeLight("OFF", light?.id!!)
                }
            }
        }

        binding.sliderLight.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                gradientDrawable.gradientRadius = slider.value * 10f
                lightViewModel.updateIntensityLight(slider.value.toInt(), light?.id!!)
            }

        })

    }

    private fun createUI() {
        binding.textDeviceName.visibility = View.VISIBLE
        binding.loading.visibility = View.INVISIBLE
        binding.toggleButton.visibility = View.VISIBLE
        binding.gradient.visibility = View.VISIBLE
        binding.sliderLight.visibility = View.VISIBLE

        binding.textDeviceName.text = light?.deviceName
        binding.sliderLight.value = light?.intensity!!.toFloat()

        if (light?.mode.equals("OFF")) {
            binding.toggleButton.check(R.id.btnOff)
            gradientRadiusVal = 0f
            gradientDrawable.gradientRadius = gradientRadiusVal
            binding.sliderLight.visibility = View.INVISIBLE
        } else {
            binding.toggleButton.check(R.id.btnOn)
            gradientRadiusVal = binding.sliderLight.value * 10f
            gradientDrawable.gradientRadius = gradientRadiusVal
            binding.sliderLight.visibility = View.VISIBLE
        }
    }

    private fun init() {
        binding.textDeviceName.visibility = View.INVISIBLE
        binding.toggleButton.visibility = View.INVISIBLE
        binding.gradient.visibility = View.INVISIBLE
        binding.sliderLight.visibility = View.INVISIBLE
    }

    override fun onStart() {
        super.onStart()
        if (light != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (light != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if (light != null) {
            createUI()
        } else {
            init()
        }
    }
}