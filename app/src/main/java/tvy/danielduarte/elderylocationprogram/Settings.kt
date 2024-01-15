package tvy.danielduarte.elderylocationprogram

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.view.drawToBitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.map.display.ui.compass.CompassButton
import com.tomtom.sdk.map.display.ui.currentlocation.CurrentLocationButton
import kotlinx.coroutines.launch
import tvy.danielduarte.elderylocationprogram.classes.DataManager
import tvy.danielduarte.elderylocationprogram.classes.DataManagerFile
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.MapService
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj
import kotlin.math.log

class Settings : AppCompatActivity() {

    private var profile: ProfileObj? = null
    private var modifiedProfileIndex:Long = -1
    private lateinit var location: LocationServicesObj
    private lateinit var mapService: MapService
    private lateinit var dataManager: DataManagerFile
    private var filePath: String  = ""
     var geofence: GeoFenceObj ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_settings)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            profile = intent.getSerializableExtra("profile", ProfileObj::class.java)
            filePath = intent.getStringExtra("filePath")!!
        }
        else {
            profile = intent.getSerializableExtra("profile") as  ProfileObj
            filePath = intent.getStringExtra("filePath")!!
        }

        dataManager = DataManagerFile(this,filePath)

        location =  LocationServicesObj(true, this)
        location.startLocationUpdatesEveryMinute()
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapService = MapService(R.drawable.personpin,R.drawable.center)
        mapService.startMap(mapFragment,location,this)

        location.getCurrentLocationAsync().thenAccept { newCurrentLocation ->
            Log.d("verifyData", "Current Location $newCurrentLocation")
            mapService.getCenterAsync().thenAccept { newCenter ->
                Log.d("verifyData", "Center $newCenter")
                val seekBarRadius = findViewById<SeekBar>(R.id.seekBarRadius)
                val radius: Float = seekBarRadius!!.progress.toFloat()
                geofence = GeoFenceObj(newCurrentLocation, newCenter, radius)
            }
        }

        isProfileNull()
        changeBtnBuildText()

    }

    fun buildProfile(view: View) {
        val imgProfilePic = findViewById<ImageView>(R.id.imgProfilePic)
        val edUserName = findViewById<EditText>(R.id.edUserName)
        val edEmail = findViewById<EditText>(R.id.edEmail)
        val edContact = findViewById<EditText>(R.id.edContact)
        val chckBxSmsState = findViewById<CheckBox>(R.id.chckBxSms)
        val chckBxEmail = findViewById<CheckBox>(R.id.chckBxEmail)
        val chckBxNotification = findViewById<CheckBox>(R.id.chckBxNotification)

        modifiedProfileIndex()

        Log.d("GeofenceTeste", " Geofence: "+ geofence+"Center : " + geofence?.center + " CurrentanteLocation: " + geofence?.currentLocation)

        val notificationType = NotificationTypeObj(
            chckBxSmsState!!.isChecked,
            chckBxEmail!!.isChecked,
            chckBxNotification!!.isChecked
        )

        val userName: String = edUserName.text.toString()
        val image: Bitmap = imgProfilePic!!.drawToBitmap()
        val contact: Int = edContact!!.text.toString().toInt()
        val email: String = edEmail!!.text.toString()

        if(geofence != null){
            var profile = ProfileObj(userName, image, contact, email, geofence, notificationType)
            Log.d("GeofenceTeste", " Profile: "+ profile +"Center : " + profile.geoFenceObj?.center + " CurrentanteLocation: " + profile.geoFenceObj?.currentLocation)
            if (isProfileNull()) {
                dataManager.write(dataManager.size() + 1 , profile)
            } else {
                dataManager.write(modifiedProfileIndex, profile)
            }

        }

        if(profile != null){
            Log.d("AGBS","Entrei no Intent")
            val intent = Intent(this, Profile::class.java)
            intent.putExtra("profile",profile)
            startActivity(intent)
        }

        Log.d("wadw", "Click Done")
    }

    private fun changeBtnBuildText(){
        val btnBuild = findViewById<Button>(R.id.btnBuild)

        if (isProfileNull()){
            btnBuild.text = "Criar Perfil"
        }
        else {
            btnBuild.text = "Guardar Mudanças"
        }
    }

    private fun isProfileNull(): Boolean {
        var isProfileNull:Boolean
        if (profile == null){
            isProfileNull = true
        }
        else {
            isProfileNull = false
        }
        return isProfileNull
    }

    private fun modifiedProfileIndex(){
        if (!isProfileNull()){
            for (i in 1..dataManager.size()){
                if (dataManager.read(i) == profile){
                    modifiedProfileIndex = i
                }

            }
        }
    }


}