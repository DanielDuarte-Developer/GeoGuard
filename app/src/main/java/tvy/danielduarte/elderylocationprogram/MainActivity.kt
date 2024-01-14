package tvy.danielduarte.elderylocationprogram

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import tvy.danielduarte.elderylocationprogram.classes.DataManager
import tvy.danielduarte.elderylocationprogram.classes.NotificationService

class MainActivity : AppCompatActivity() {

    private val REQUEST_LOCATION_PERMISSION = 1

    private val dataManager = DataManager(this)
    private var profilesList: MutableList<ProfileObj> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPerms()
        createNotificationChannel()
        settingsToList(profilesList)

    }

    private fun checkPerms(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            askPerms()
        }
    }
    private fun askPerms(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),REQUEST_LOCATION_PERMISSION)
        checkPerms()
    }
    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NotificationService.CHANNEL_ID,
                "Out of Bounds",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Used for notifying if the device is out of the geo-fence"

            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun settingsToList(profilesList:MutableList<ProfileObj>){
        lifecycleScope.launch{
            for (i in 1..dataManager.size())
                dataManager.read(i)?.let { profilesList.add(it) }
        }

        showProfiles()
    }

    private fun showProfiles() {

        var lnPerfil = findViewById<LinearLayout>(R.id.lnPerfil)
        lnPerfil.removeAllViews()

        profilesList.forEach{
            val profile:ProfileObj = it
            val txtPerfil = layoutInflater.inflate(R.layout.single_profile, null)
            txtPerfil.findViewById<TextView>(R.id.txtPerfil).text = profile.name
            txtPerfil.setOnClickListener {
                val inte = Intent(this, Profile::class.java)
                inte.putExtra("profile", profile)
                startActivity(inte)
            }
            lnPerfil.addView(txtPerfil)
        }

    }

    fun createProfile(view: View) {
        val inte = Intent(this, Settings::class.java)
        startActivity(inte)
    }

}