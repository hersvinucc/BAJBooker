package com.example.bookJobBooker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class Login : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignIn: Button
    private lateinit var editTextPassword: EditText
    private lateinit var imgPasswordToggle: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //For Login Button
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            navigateToMainActivity()
        }

        //For SignIn Button
        buttonSignIn = findViewById(R.id.buttonSignIn)
        buttonSignIn.setOnClickListener {
            navigateToSignInActivity()
        }

        // For Password Toggle
        editTextPassword = findViewById(R.id.editTextPassword)
        imgPasswordToggle = findViewById(R.id.imgPasswordToggle)
        imgPasswordToggle.setOnClickListener(object : View.OnClickListener {
            var passwordVisible = false

            override fun onClick(v: View) {
                passwordVisible = !passwordVisible

                val imgResource = if (passwordVisible) R.drawable.eye else R.drawable.eye_slash

                editTextPassword.inputType =
                    if (passwordVisible) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    else InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

                imgPasswordToggle.setImageResource(imgResource)
            }
        })
    }
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToSignInActivity() {
        val intent = Intent(this, SignIn::class.java)
        startActivity(intent)
        finish()
    }
}

