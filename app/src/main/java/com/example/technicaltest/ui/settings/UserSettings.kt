package com.example.technicaltest.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.technicaltest.R
import com.example.technicaltest.databinding.ActivityUserSettingsBinding
import com.example.technicaltest.models.response.User
import com.example.technicaltest.utils.Constants.EMAIL
import com.example.technicaltest.utils.Constants.LETTER
import com.example.technicaltest.utils.Constants.NUMBER
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@AndroidEntryPoint
class UserSettings : AppCompatActivity() {

    private lateinit var binding: ActivityUserSettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel
    private var user: User? = null
    private var birthMilliseconds: Long? = 0
    private val VALID_EMAIL_ADDRESS_REGEX: Pattern =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE)
    private var check = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsViewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)

        settingsViewModel.getUser().observe(this, {
            user = it
            loaded()
        })


        binding.btnModify.setOnClickListener {
            mainLayoutInvisible()
            modify()
        }

        binding.btnValidate.setOnClickListener {
            checkField()

            if (check == 0) {
                updateUser()
                endModify()
                mainLayoutVisible()
            }
        }

        binding.btnBirthDate.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.selectBirthdate))
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(supportFragmentManager, "tag")

            datePicker.addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
                binding.textModifyBirthdate.setTextColor(resources.getColor(R.color.colorPrimaryLight))
                binding.textModifyBirthdate.text = sdf.format(datePicker.selection)
                birthMilliseconds = datePicker.selection
            }
        }
    }

    private fun updateUser() {
        with(binding) {

            val adress =
                "${numStreet.editText!!.text}, ${vicinity.editText!!.text} - ${zipCode.editText!!.text} ${city.editText!!.text} - ${country.editText!!.text}"
            settingsViewModel.updateUser(
                firstname = textFirstName.editText!!.text.toString(),
                lastname = textLastName.editText!!.text.toString(),
                birthdate = birthMilliseconds!!,
                address = adress,
                email = email.editText!!.text.toString()
            )
        }
    }

    private fun init() {
        with(binding) {
            loading.visibility = View.VISIBLE
            mainLayoutInvisible()
            endModify()

        }
    }

    private fun endModify() {
        with(binding) {
            textFirstName.visibility = View.INVISIBLE
            textLastName.visibility = View.INVISIBLE
            btnBirthDate.visibility = View.INVISIBLE
            textModifyBirthdate.visibility = View.INVISIBLE
            numStreet.visibility = View.INVISIBLE
            vicinity.visibility = View.INVISIBLE
            zipCode.visibility = View.INVISIBLE
            city.visibility = View.INVISIBLE
            country.visibility = View.INVISIBLE
            email.visibility = View.INVISIBLE
            btnValidate.visibility = View.INVISIBLE
            numStreet.visibility = View.INVISIBLE
        }
    }

    private fun modify() {
        with(binding) {
            textFirstName.visibility = View.VISIBLE
            textLastName.visibility = View.VISIBLE
            btnBirthDate.visibility = View.VISIBLE
            textModifyBirthdate.visibility = View.VISIBLE
            numStreet.visibility = View.VISIBLE
            vicinity.visibility = View.VISIBLE
            zipCode.visibility = View.VISIBLE
            city.visibility = View.VISIBLE
            country.visibility = View.VISIBLE
            email.visibility = View.VISIBLE
            btnValidate.visibility = View.VISIBLE
            numStreet.visibility = View.VISIBLE
        }
    }

    private fun mainLayoutInvisible() {
        with(binding) {
            firstName.visibility = View.INVISIBLE
            lastName.visibility = View.INVISIBLE
            picHome.visibility = View.INVISIBLE
            birthDate.visibility = View.INVISIBLE
            textVicinity.visibility = View.INVISIBLE
            picEmail.visibility = View.INVISIBLE
            textEmail.visibility = View.INVISIBLE
            btnModify.visibility = View.INVISIBLE
        }
    }

    private fun mainLayoutVisible() {
        with(binding) {
            firstName.visibility = View.VISIBLE
            firstName.text = user?.firstName
            lastName.visibility = View.VISIBLE
            lastName.text = user?.lastName
            picHome.visibility = View.VISIBLE
            birthDate.visibility = View.VISIBLE
            textVicinity.visibility = View.VISIBLE
            picEmail.visibility = View.VISIBLE
            textEmail.visibility = View.VISIBLE
            textVicinity.text = "${user?.addressString}"
            birthDate.text = "${user?.getDate()}"
            textEmail.text = user?.email
            btnModify.visibility = View.VISIBLE
        }
    }

    private fun loaded() {
        binding.loading.visibility = View.INVISIBLE
        mainLayoutVisible()
        endModify()
    }

    override fun onStart() {
        super.onStart()
        if (user != null) {
            loaded()
        } else {
            init()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (user != null) {
            loaded()
        } else {
            init()
        }
    }

    override fun onResume() {
        super.onResume()
        if (user != null) {
            loaded()
        } else {
            init()
        }
    }

    private fun checkField() {
        check = 0
        with(binding) {

            if (textModifyBirthdate.text.isEmpty()) {
                textModifyBirthdate.setTextColor(resources.getColor(R.color.design_default_color_error))
                textModifyBirthdate.text = getString(R.string.errorBirthdate)
                check++
            }

            verifyField(
                textFirstName.editText!!,
                getString(R.string.errorFirstname),
                getString(R.string.errorInvalidFirstname),
                LETTER
            )
            verifyField(
                textLastName.editText!!,
                getString(R.string.errorLastname),
                getString(R.string.errorInvalidLastname),
                LETTER
            )
            verifyField(numStreet.editText!!, getString(R.string.errorStreetNumber), "", NUMBER)
            verifyField(
                vicinity.editText!!,
                getString(R.string.errorStreet),
                getString(R.string.errorInvalidStreet),
                LETTER
            )
            verifyField(zipCode.editText!!, getString(R.string.errorPostalCode), "", NUMBER)
            verifyField(
                city.editText!!,
                getString(R.string.errorCity),
                getString(R.string.errorInvalidCity),
                LETTER
            )
            verifyField(
                country.editText!!,
                getString(R.string.errorCountry),
                getString(R.string.errorInvalidCountry),
                LETTER
            )
            verifyField(
                email.editText!!,
                getString(R.string.errorEmail),
                getString(R.string.errorInvalidEmail),
                EMAIL
            )
        }
    }

    private fun verifyField(editText: EditText, message: String, messageValid: String, s: String) {
        if (editText.text.isEmpty()) {
            editText.error = message
            check++
        } else {
            if (s == LETTER) {
                if (!containsNumber(editText.text.toString())) {
                    editText.error = messageValid
                    check++
                }
            } else if (s == EMAIL) {
                if (!validateMail(editText.text.toString())) {
                    editText.error = messageValid
                    check++
                }
            }
        }
    }


    private fun validateMail(emailStr: String?): Boolean {
        val matcher: Matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr)
        return matcher.find()
    }

    private fun containsNumber(string: String): Boolean {
        return string.matches("[a-zA-Z ]+".toRegex())
    }
}