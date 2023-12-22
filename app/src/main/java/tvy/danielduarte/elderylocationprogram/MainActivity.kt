package tvy.danielduarte.elderylocationprogram

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.MapView


class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val DISTANCE_IN_RADIUS_METERS = 15
    private lateinit var textView: TextView

    val perimeterLatitudeStart = 39.2209175
    val perimeterLongitudeStart = -8.686665
    private var lastKnownLocation: Location? = null
    private val REQUEST_LOCATION_PERMISSION = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        /*
        val map = findViewById<MapView>(R.id.mapView)

        val mapCallback = CreateMap()
        map.getMapAsync(mapCallback)
         */

    }

    fun Click(view: View) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates()
        }else {
            // Solicitar permissão
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult.lastLocation != null) {
                lastKnownLocation = locationResult.lastLocation
                checkIfOutsidePerimeter()
            }
        }
    }
    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000) // Intervalo em milissegundos para atualizações de localização (aqui, a cada 10 segundos)
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

    }

    private fun checkIfOutsidePerimeter() {
        // Verifique se a última localização conhecida está fora do perímetro desejado
        if (lastKnownLocation != null) {
            val latitude = lastKnownLocation!!.latitude
            val longitude = lastKnownLocation!!.longitude

            textView.text = "Latitude: $latitude, Longitude: $longitude"


            val distanceAtual = calculateDistance(perimeterLatitudeStart, perimeterLongitudeStart,latitude, longitude)
            Log.d("ABC1",distanceAtual.toString())


            if (distanceAtual > DISTANCE_IN_RADIUS_METERS) {
                // O dispositivo está fora do perímetro, notifique o usuário
                textView.text = "Fora do perímetro! Distância: $distanceAtual metros"
            } else {
                // O dispositivo está dentro do perímetro
                textView.text = "Dentro do perímetro"
            }



        }
    }
    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val location1 = Location("")
        location1.latitude = lat1
        location1.longitude = lon1

        val location2 = Location("")
        location2.latitude = lat2
        location2.longitude = lon2

        return location1.distanceTo(location2)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}