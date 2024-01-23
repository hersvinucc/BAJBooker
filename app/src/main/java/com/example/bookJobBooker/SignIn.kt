package com.example.bookJobBooker


import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.hbb20.CountryCodePicker
import org.json.JSONException
import org.json.JSONObject


class SignIn : AppCompatActivity() {
   // private lateinit var editTextDate: EditText
    //private lateinit var datePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //For Phone Number
       // val fullNameEditText = findViewById<EditText>(R.id.fullname)
      //  val genderEditText = findViewById<EditText>(R.id.Gender)
        val countryCodePicker = findViewById<CountryCodePicker>(R.id.ccp)
        countryCodePicker.setDefaultCountryUsingNameCode("PH")
        val phoneNumberEditText = findViewById<EditText>(R.id.phoneNumberEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)
        val linearLayout = findViewById<LinearLayout>(R.id.header)
        val backBtn = linearLayout.findViewById<ImageView>(R.id.arrowBack)

        phoneNumberEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                val isValidPhoneNumber =
                    editable?.toString()?.replace("\\s".toRegex(), "")?.length == 10
                registerButton.isEnabled = isValidPhoneNumber
            }
        })

        //For Date Picker Bday
       // editTextDate = findViewById(R.id.editTextDate)
       // datePicker = findViewById(R.id.datePicker)
        //For Login Button
        backBtn.setOnClickListener {
            navToLogin()
        }

        registerButton.setOnClickListener {
            register()
        }

    }

    //Input Date Picker Bday
   /* fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, year: Int, month, dayOfMonth ->
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
    */

    //VERIFY CODE
    /*  private fun Register()
    {
        val verifyCode = Intent(this, Login::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            this,
            R.anim.slide_in_left,
            R.anim.slide_in_right
        )
        startActivity(verifyCode, options.toBundle())

        finish()
    }
*/


    //Intent To Login Page
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
    private fun showToast(message: String) {
        Toast.makeText(this@SignIn, message, Toast.LENGTH_SHORT).show()
    }
    private fun register() {
        // Get user input
        val fullName = findViewById<EditText>(R.id.fullname).text.toString()
        val gender = findViewById<EditText>(R.id.Gender).text.toString()
        val email = findViewById<EditText>(R.id.editEmail).text.toString()
        val countryCodePicker = findViewById<CountryCodePicker>(R.id.ccp)
        val phoneNumber = findViewById<EditText>(R.id.phoneNumberEditText).text.toString()
        val userName = findViewById<EditText>(R.id.userName).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        val confirmPassword = findViewById<EditText>(R.id.confirmPassword).text.toString()

        //Validation Password
        if (password != confirmPassword) {
            Log.d("Register", "Password mismatch: $password != $confirmPassword")
            return
        }
        //Parameter Request
        val params = HashMap<String, String>()
        params["name"] = fullName
        params["gender"] = gender
        params["email"] = email
        params["contact_number"] = phoneNumber
        params["username"] = userName
        params["password"] = password
        params["confirmPassword"] = confirmPassword


        Log.d("Register", "Request Parameters: $params")


        val stringRequest = object : StringRequest(
            Method.POST,
            "http://192.168.100.132:8000/api/registerBooker",
            Response.Listener { response ->
                try {
                    JSONObject(response)
                    Log.d("Register", "Success")
                    showToast("Registered successfully!")

                    //register success back to login
                    navToLogin()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                // Handle errors
                error.printStackTrace()

                if (error.networkResponse != null && error.networkResponse.data != null) {
                    val responseString = String(error.networkResponse.data, Charsets.UTF_8)
                    Log.e("Register", "Volley Error: $responseString")
                } else {
                    Log.e("Register", "Volley Error: Unknown error")
                }
            }) {



            override fun getParams(): Map<String, String> {
                return params
            }
        }

        // Add the request to the RequestQueue
        Volley.newRequestQueue(this).add(stringRequest)
    }
}



/* private fun navToVerifyCode() {
     val verifyCode = Intent(this, VerifyCode::class.java)
     val options = ActivityOptions.makeCustomAnimation(
         this,
         R.anim.slide_in_left,
         R.anim.slide_in_right
     )
     startActivity(verifyCode, options.toBundle())

     finish()
 } */
