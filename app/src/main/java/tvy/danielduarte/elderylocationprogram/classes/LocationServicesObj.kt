package tvy.danielduarte.elderylocationprogram.classes

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class LocationServicesObj(RepetitiveUpdates: Boolean) {
    var repetitiveUpdates: Boolean = RepetitiveUpdates
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var currentLocation: Location

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    fun startLocationUpdatesEveryMinute() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(60000)
        fusedLocationClient.requestLocationUpdates(locationRequest, getCurrentLocation, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(getCurrentLocation)
    }

    private val getCurrentLocation = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation != null) {
                currentLocation = locationResult.lastLocation
                if (repetitiveUpdates == false){
                    stopLocationUpdates()

                }
            }

        }
    }

}