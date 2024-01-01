package com.example.bookJobBooker

import android.app.ActivityOptions
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.hbb20.CountryCodePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignIn : AppCompatActivity() {
    private lateinit var editTextDate: EditText
    private lateinit var  datePicker: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //For Phone Number
        val countryCodePicker = findViewById<CountryCodePicker>(R.id.ccp)
        countryCodePicker.setDefaultCountryUsingNameCode("PH")
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val linearLayout = findViewById<LinearLayout>(R.id.header)
        val backBtn = linearLayout.findViewById<ImageView>(R.id.arrowBack)

        phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val isValidPhoneNumber = editable?.toString()?.replace("\\s".toRegex(), "")?.length == 10
                nextButton.isEnabled = isValidPhoneNumber
            }
        })

        //For Date
        editTextDate = findViewById(R.id.editTextDate)
        datePicker = findViewById(R.id.datePicker)
        //For Login Button
        backBtn.setOnClickListener {
            navToLogin()
        }

        nextButton.setOnClickListener {
            navToVerifyCode()
        }

    }

    //Input Date Picker
    fun showDatePickerDialog(v: View) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectedDate = formatDate(year, month, dayOfMonth)
                editTextDate.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.show()
    }


    // Format Date
    private fun formatDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun navToLogin() {
        val login = Intent(this, Login::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_in_right
        )
        startActivity(login, options.toBundle())

        finish()
    }

    private fun navToVerifyCode() {
        val verifyCode = Intent(this, VerifyCode::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_in_right
        )
        startActivity(verifyCode, options.toBundle())

        finish()
    }
}
