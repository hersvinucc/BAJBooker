package com.example.bookJobBooker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            goToLandingScreen()
        }, 3000L)
    }

    private fun goToLandingScreen() {
        Intent(this, GetStarted::class.java). also {
            startActivity(it)
            finish()
        }
    }
}