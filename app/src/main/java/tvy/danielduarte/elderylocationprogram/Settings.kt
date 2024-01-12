package tvy.danielduarte.elderylocationprogram

import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.core.view.drawToBitmap
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj

class Settings : AppCompatActivity() {

    private var locationService: LocationServicesObj = LocationServicesObj(false)



    private lateinit var center: Location // Intent para o google maps
    private var radius: Float


    private val edUserName: String = findViewById<EditText>(R.id.txtUserName).toString()
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