package com.sotrh.portfolio.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.sotrh.portfolio.R
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.view_weather.*
//import kotlinx.android.synthetic.main.view_weather.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WeatherFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Deal with location
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Deal with current time
        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEEE h a", Locale.getDefault())
        weather_day.text = dateFormat.format(currentTime)

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // TODO: show permission request dialog
//                requestPermissions(
//                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//                    PERMISSIONS_REQUEST_LOCATION_CODE
//                )
            } else {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION_CODE
                )
            }
        } else {
            startForecastQuery()
        }

    }

    private fun startForecastQuery() {
        progressBar.visibility = View.VISIBLE
        weather_group.visibility = View.GONE
        val locationTask = fusedLocationClient.lastLocation
        locationTask.addOnSuccessListener { loc ->
            val model: WeatherViewModel by viewModels()
            model.getForecastPeriods(loc.latitude, loc.longitude)
                .observe(viewLifecycleOwner) {
                    progressBar.visibility = View.GONE
                    weather_group.visibility = View.VISIBLE
                    weather_location.text = getString(R.string.weather_location, it.city, it.state)
                    weather_condition.text = it.shortForecast
                    weather_temperature.text =
                        getString(R.string.weather_temperature, it.temperature, it.temperatureUnit)
                }
        }

        locationTask.addOnFailureListener {
            Snackbar.make(requireView(), it.localizedMessage, Snackbar.LENGTH_LONG)
                .show()
            notifyLocationFailed()
        }
    }

    private fun notifyLocationFailed() {
        progressBar.visibility = View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    startForecastQuery()
                } else {
                    notifyLocationFailed()
                }
            }
            else -> {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    companion object {
        const val PERMISSIONS_REQUEST_LOCATION_CODE: Int = 0
    }
}
