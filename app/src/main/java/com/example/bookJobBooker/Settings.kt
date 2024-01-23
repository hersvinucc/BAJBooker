package com.example.bookJobBooker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Settings : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        // Find the Logout button
        val btnLogout = view.findViewById<LinearLayout>(R.id.btnLogout)

        // Set onClickListener for the Logout button
        btnLogout.setOnClickListener {
            // Call the method to perform logout actions
            logout()
            Log.d("SettingsFragment", "Logout button clicked")
        }

        return view
    }

    private fun logout() {
        val url = "http://192.168.100.132:8000/api/logout"
        clearAccessToken()
        Toast.makeText(requireContext(), "Logout successful", Toast.LENGTH_SHORT).show()

        // Start a new Login activity
        val intent = Intent(requireContext(), Login::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun clearAccessToken() {
        val sharedPreferences = requireContext().getSharedPreferences("Booker Token", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", null)
        Log.d("SettingsFragment", "Current AccessToken: $accessToken")
        val editor = sharedPreferences.edit()
        editor.remove("accessToken")
        Log.d("SettingsFragment", "AccessToken removed from SharedPreferences")

        editor.apply()
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settings().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
