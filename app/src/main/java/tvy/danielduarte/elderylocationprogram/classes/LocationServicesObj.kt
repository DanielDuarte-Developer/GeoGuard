package tvy.danielduarte.elderylocationprogram.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationServicesObj {
    var repetitiveUpdates: Boolean ?= null
    private var fusedLocationClient: FusedLocationProviderClient
    var currentLocation: Location ?= null
    private var mapService: MapService ?= null

    constructor(repetitiveUpdates: Boolean, activity: Activity){
        this.repetitiveUpdates = repetitiveUpdates
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    fun startLocationUpdatesEveryMinute() {
        val locationRequest = LocationRequest.Builder(6000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        fusedLocationClient.requestLocationUpdates(locationRequest, getCurrentLocation, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(getCurrentLocation)
    }

    private val getCurrentLocation = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation != null) {
                currentLocation = locationResult.lastLocation!!

                Log.d("LocationsTeste", "Latitude: "+currentLocation!!.latitude.toString()+" Longitude: " + currentLocation!!.longitude)

                if (repetitiveUpdates == false){
                    stopLocationUpdates()

                }
            }
        }
    }



}