package com.example.bookJobBooker

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class ConfirmPassword : AppCompatActivity() {
    private lateinit var confirmPassword: EditText
    private lateinit var imgPasswordToggle: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_password)

        confirmPassword = findViewById(R.id.confirmPassword)
        imgPasswordToggle = findViewById(R.id.imgPasswordToggle)
        val nextButton = findViewById<Button>(R.id.nextButton)
        imgPasswordToggle.setOnClickListener(object : View.OnClickListener {
            var passwordVisible = false

            override fun onClick(v: View) {
                passwordVisible = !passwordVisible

                val imgResource = if (passwordVisible) R.drawable.eye else R.drawable.eye_slash

                confirmPassword.inputType =
                    if (passwordVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                imgPasswordToggle.setImageResource(imgResource)
            }
        })

        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val isValidCode = editable?.toString()?.replace("\\s".toRegex(), "")?.length == 8
                nextButton.isEnabled = isValidCode
            }
        })


        nextButton.setOnClickListener {
            navToMain()
        }
    }

    private fun navToMain() {
        val main = Intent(this, MainActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.fade_in,
            R.anim.fade_out
        )
        startActivity(main, options.toBundle())
        finish()
    }
}