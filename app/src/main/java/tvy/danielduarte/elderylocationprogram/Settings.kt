package tvy.danielduarte.elderylocationprogram

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.view.drawToBitmap
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj

class Settings : AppCompatActivity() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    private lateinit var locationService: LocationServicesObj
    private lateinit var center: Location
    private var radius: Float = 0.0f
    private var id: Int = 1;

    private lateinit var geoFenceObj: GeoFenceObj
    private lateinit var notificationType: NotificationTypeObj

    private val txtUserName: String = findViewById<TextView>(R.id.txtUserName).toString()
    private val imgProfilePic: Bitmap = findViewById<ImageView>(R.id.imgProfilePic).drawToBitmap()


    private lateinit var profile: ProfileObj
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_settings)
        locationService = LocationServicesObj(false)

        //radius


    }


    suspend fun buildProfile(view: View) {
        geoFenceObj= GeoFenceObj(locationService.currentLocation, center, radius)
        getCheckBoxesState()

        profile = ProfileObj(txtUserName, imgProfilePic, geoFenceObj, notificationType)

        var create = write(id,profile);
    }

    fun getCheckBoxesState(){
        val chckBxSmsState = findViewById<CheckBox>(R.id.chckBxSms).isChecked
        val chckBxEmail = findViewById<CheckBox>(R.id.chckBxEmail).isChecked
        val chckBxNotification = findViewById<CheckBox>(R.id.chckBxNotification).isChecked
        notificationType = NotificationTypeObj(chckBxSmsState, chckBxEmail, chckBxNotification)

    }

    fun getCenterCoordinates(){

    }

    fun getRadiusValue(){
        val seekBarRadiusValue = findViewById<SeekBar>(R.id.seekBarRadius).progress
        radius = seekBarRadiusValue as Float
    }

}