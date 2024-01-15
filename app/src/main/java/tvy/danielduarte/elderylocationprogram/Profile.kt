package tvy.danielduarte.elderylocationprogram

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapFragment
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.MapService

class Profile : AppCompatActivity() {
    var profile: ProfileObj? = null
    private lateinit var mapService:MapService
    private lateinit var location: LocationServicesObj
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_profile)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){

            profile = intent.getSerializableExtra("profile", ProfileObj::class.java)
        }else{

            profile = intent.getSerializableExtra("profile") as ProfileObj
        }

        val textViewName = findViewById<TextView>(R.id.textName)
        val img = findViewById<ImageView>(R.id.imageVPerfilPic)
        textViewName.text = profile?.name
        img.setImageBitmap(profile?.image)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapService = MapService(R.drawable.personpin)
        location = LocationServicesObj(true, this)
        mapService.startMap(mapFragment,location,this)
        location.startLocationUpdatesEveryMinute()
    }

    override fun onDestroy() {
        super.onDestroy()
        location.stopLocationUpdates()
    }
}