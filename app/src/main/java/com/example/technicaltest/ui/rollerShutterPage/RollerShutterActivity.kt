package com.example.technicaltest.ui.rollerShutterPage

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityRollerShutterActivityBinding
import com.example.technicaltest.models.devices.RollerShutter
import com.google.android.material.slider.Slider
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RollerShutterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRollerShutterActivityBinding
    private lateinit var viewModel: RollerShutterViewModel
    private var rollerShutter: RollerShutter? = null
    private var gradientRadiusVal: Float = 0f
    private lateinit var gradientDrawable: GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRollerShutterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layout = findViewById<ImageView>(R.id.gradientRoller)

        gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            intArrayOf(0xFFffff8d.toInt(), 0xFF00616261.toInt())
        )
        gradientDrawable.gradientType = GradientDrawable.RADIAL_GRADIENT
        gradientDrawable.cornerRadius = 30f

        layout.setBackgroundDrawable(gradientDrawable)

        viewModel = ViewModelProvider(this).get(RollerShutterViewModel::class.java)

        viewModel.getRollerShutter(intent.getIntExtra("RollerShutterID", 0)).observe(this, {
            rollerShutter = it

            createUI()
        })

        binding.slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                gradientRadiusVal = slider.value * 10f
                gradientDrawable.gradientRadius = gradientRadiusVal
                viewModel.updatePositionRollerShutter(slider.value.toInt(), rollerShutter?.id!!)
            }

        })

    }

    private fun init() {
        binding.textDeviceName.visibility = View.INVISIBLE
        binding.slider.visibility = View.INVISIBLE
    }

    private fun createUI() {
        binding.textDeviceName.visibility = View.VISIBLE
        binding.loading.visibility = View.INVISIBLE
        binding.slider.visibility = View.VISIBLE
        binding.gradientRoller.visibility = View.VISIBLE

        binding.textDeviceName.text = rollerShutter?.deviceName
        binding.slider.value = rollerShutter?.position?.toFloat()!!

        gradientRadiusVal = binding.slider.value * 10f
        gradientDrawable.gradientRadius = gradientRadiusVal

    }

    override fun onStart() {
        super.onStart()
        if (rollerShutter != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (rollerShutter != null) {
            createUI()
        } else {
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if (rollerShutter != null) {
            createUI()
        } else {
            init()
        }
    }
}