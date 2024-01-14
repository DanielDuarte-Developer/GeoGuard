package tvy.danielduarte.elderylocationprogram

import android.content.Context
import android.graphics.Bitmap
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.android.gms.maps.MapView
import tvy.danielduarte.elderylocationprogram.classes.DataManager
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj

class Settings : AppCompatActivity() {

    private var profile: ProfileObj? = null
    private var modifiedProfileIndex:Int = -1

    private val imgProfilePic = findViewById<ImageView>(R.id.imgProfilePic)

    private val edUserName = findViewById<EditText>(R.id.edUserName)
    private val edEmail = findViewById<EditText>(R.id.edEmail)
    private val edContact = findViewById<EditText>(R.id.edContact)

    private val mapView = findViewById<MapView>(R.id.mapView)
    private val seekBarRadius = findViewById<SeekBar>(R.id.seekBarRadius)

    private val chckBxSmsState = findViewById<CheckBox>(R.id.chckBxSms)
    private val chckBxEmail = findViewById<CheckBox>(R.id.chckBxEmail)
    private val chckBxNotification = findViewById<CheckBox>(R.id.chckBxNotification)

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val dataManager: DataManager = DataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_settings)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            profile = intent.getSerializableExtra("profile", ProfileObj::class.java)
        }
        else {
            intent.getSerializableExtra("profile") as  ProfileObj
        }
        isProfileNull()

        changeBtnBuildText()
    }


    suspend fun buildProfile(view: View) {

        modifiedProfileIndex()

        val center:Location = mapView
        val radius: Float = seekBarRadius.progress.toFloat()
        val geofence = GeoFenceObj(currentLocation, center, radius)

        val notificationType = NotificationTypeObj(chckBxSmsState.isChecked, chckBxEmail.isChecked, chckBxNotification.isChecked)

        val userName:String = edUserName.toString()
        val image:Bitmap = imgProfilePic.drawToBitmap()
        val contact: Int = edContact.text.toString().toInt()
        val email: String = edEmail.text.toString()
        val profile = ProfileObj(userName, image, contact, email, geofence, notificationType)

        if (isProfileNull()){
            dataManager.write(dataManager.size(),profile)
        }
        else{
            dataManager.write(modifiedProfileIndex,profile)

        }

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

    private suspend fun modifiedProfileIndex(){
        if (!isProfileNull()){
            for (i in 1..dataManager.size()){
                if (dataManager.read(i) == profile){
                    modifiedProfileIndex = i
                }

            }
        }
    }
}