package com.example.bookJobBooker

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout

class VerifyCode : AppCompatActivity() {
    private lateinit var btnNext : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        val verifyCode = findViewById<EditText>(R.id.verificationCode)
        val linearLayout = findViewById<LinearLayout>(R.id.header)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val backBtn = linearLayout.findViewById<ImageView>(R.id.arrowBack)

        verifyCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val isValidCode = editable?.toString()?.replace("\\s".toRegex(), "")?.length == 6
                nextButton.isEnabled = isValidCode
            }
        })

        backBtn.setOnClickListener {
            navToSignIn()
        }

        nextButton.setOnClickListener {
            navToConfirmPassword()
        }
    }
    private fun navToSignIn() {
        val intent = Intent(this, SignIn::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_in_right
        )
        startActivity(intent, options.toBundle())

        finish()
    }

    private fun navToConfirmPassword() {
        val intent = Intent(this, ConfirmPassword::class.java)
        startActivity(intent)
        finish()
    }
}