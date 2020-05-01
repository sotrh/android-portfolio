package com.sotrh.portfolio.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.sotrh.portfolio.R
import kotlinx.android.synthetic.main.fragment_weather.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class WeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentTime = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEEE h a", Locale.getDefault())
        weather_day.text = dateFormat.format(currentTime)

        val model: WeatherViewModel by viewModels()
        model.getForecastPeriods(39.7456,-97.0892).observe(viewLifecycleOwner) {
            weather_location.text = "${it.city}, ${it.state}"
            weather_condition.text = it.shortForecast
            weather_temperature.text = "${it.temperature}°${it.temperatureUnit}"
        }

//        val location = "Provo, Utah"
//        weather_location.text = location
//
//        val condition = "Sunny"
//        weather_condition.text = condition
//
//        val temperature = getString(R.string.weather_temperature, 79)
//        weather_temperature.text = temperature

    }
}
