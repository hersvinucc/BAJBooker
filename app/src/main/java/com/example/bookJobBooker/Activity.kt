package com.example.bookJobBooker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class Activity : Fragment() {

    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_activity, container, false)

        // Initialize the map fragment
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(map: GoogleMap?) {
                if (map != null) {
                    googleMap = map


                    val philippines = LatLng(14.65354, 120.99462)
                    googleMap!!.addMarker(MarkerOptions().position(philippines).title("Philippines"))

                    // Move the camera to the marker position and zoom in
                    googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(philippines, 6f))
                }
            }
        })

        return view
    }

    companion object {
        private const val ARG_ACTIVITY_PARAM1: String = "activity_param1"
        private const val ARG_ACTIVITY_PARAM2: String = "activity_param2"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Activity().apply {
                arguments = Bundle().apply {
                    putString(ARG_ACTIVITY_PARAM1, param1)
                    putString(ARG_ACTIVITY_PARAM2, param2)
                }
            }
    }
}
