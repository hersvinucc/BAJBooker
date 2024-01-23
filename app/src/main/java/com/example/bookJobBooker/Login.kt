package com.example.bookJobBooker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Login : AppCompatActivity() {
    private lateinit var buttonLogin: Button
    private lateinit var buttonSignIn: Button
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUsername: EditText
    private lateinit var imgPasswordToggle: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("Booker Token", Context.MODE_PRIVATE)

        // For Login Button
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener {
            loginUser()
        }

        // For SignIn Button
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

        // For Username
        editTextUsername = findViewById(R.id.editTextUsername)
    }

    private fun loginUser() {
        val url = "http://192.168.100.132:8000/api/login"

        val jsonBody = JSONObject().apply {
            try {
                put("username", editTextUsername.text.toString().trim())
                put("password", editTextPassword.text.toString().trim())
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, jsonBody,
            { response ->
                try {
                    val userRole = response.optInt("user_role", -1)

                    if (userRole == 2) {
                        // Extract necessary fields from the response
                        val accessToken = response.optString("access_token", "")
                        val expiresIn = response.optInt("expires_in", 0)
                        val status = response.optInt("status", 0)

                        // Save accessToken to SharedPreferences
                        saveAccessToken(accessToken)

                        showToast("Login Successful")
                        Log.d("LoginResponse", "Access Token: $accessToken")
                        Log.d("Expire", "Expire Type: $expiresIn")
                        Log.d("Status", "Status Type: $status")

                        // Navigate to the homepage of the booker
                        navigateToMainActivity()
                    } else {
                        showToast("Unauthorized User. Only For Booker Login")
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                // Handle the error response here
                Log.e("LoginError", "Error: $error")

                if (error.networkResponse != null) {
                    val statusCode = error.networkResponse.statusCode
                    val errorMessage = String(error.networkResponse.data)
                    Log.e("LoginError", "Status Code: $statusCode, Error Message: $errorMessage")

                    if (statusCode == 401) {
                        showToast("Invalid username or password")
                    } else {
                        showToast("Login failed. Please try again later.")
                    }
                } else {
                    showToast("No Register Account")
                }
            })

        // Add the request to the RequestQueue
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }


    private fun saveAccessToken(accessToken: String) {
        val editor = sharedPreferences.edit()
        editor.putString("accessToken", accessToken)
        editor.apply()
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
