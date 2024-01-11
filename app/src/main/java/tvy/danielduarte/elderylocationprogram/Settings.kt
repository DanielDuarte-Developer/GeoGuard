package tvy.danielduarte.elderylocationprogram

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.view.drawToBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj

class Settings : AppCompatActivity() {

    private var locationService: LocationServicesObj = LocationServicesObj(false)



    private lateinit var center: Location // Intent para o google maps
    private var radius: Float


    private val edUserName: String = findViewById<EditText>(R.id.edUserName).toString()
    private val imgProfilePic: Bitmap = findViewById<ImageView>(R.id.imgProfilePic).drawToBitmap()

    private lateinit var geoFenceObj: GeoFenceObj
    private lateinit var notificationType: NotificationTypeObj

    private lateinit var profile: ProfileObj
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_settings)
        locationService
        geoFenceObj = GeoFenceObj(locationService.currentLocation, center, radius)
        profile = ProfileObj(edUserName, imgProfilePic, geoFenceObj, notificationType)

    }


}