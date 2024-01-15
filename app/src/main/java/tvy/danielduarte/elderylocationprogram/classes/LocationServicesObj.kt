package tvy.danielduarte.elderylocationprogram.classes

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.util.concurrent.CompletableFuture

class LocationServicesObj {
    var repetitiveUpdates: Boolean ?= null
    private var fusedLocationClient: FusedLocationProviderClient
    var currentLocation: Location = Location("")
    private var currentLocationUpdateFuture: CompletableFuture<Location>? = null


    constructor(repetitiveUpdates: Boolean, activity: Activity){
        this.repetitiveUpdates = repetitiveUpdates
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    fun getCurrentLocationAsync(): CompletableFuture<Location> {
        currentLocationUpdateFuture = CompletableFuture()
        Log.d("VerifyCompletableFuture", "CurrentLocation promise created")
        return currentLocationUpdateFuture!!
    }

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    fun startLocationUpdatesEveryMinute() {
        val locationRequest = LocationRequest.Builder(120000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
        fusedLocationClient.requestLocationUpdates(locationRequest, getCurrentLocation, Looper.getMainLooper())
    }


    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(getCurrentLocation)
    }

    private val getCurrentLocation = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation != null) {
                currentLocation = locationResult.lastLocation!!
                Log.d("VerifyCompletableFuture", "getCurrentLocation callback")
                currentLocationUpdateFuture?.complete(currentLocation)

                if (repetitiveUpdates == false){
                    stopLocationUpdates()

                }
            }
        }
    }
}