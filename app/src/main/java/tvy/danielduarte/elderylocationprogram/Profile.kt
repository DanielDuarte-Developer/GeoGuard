package tvy.danielduarte.elderylocationprogram

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.ui.MapFragment
import tvy.danielduarte.elderylocationprogram.classes.LocationServicesObj
import tvy.danielduarte.elderylocationprogram.classes.MapService

class Profile : AppCompatActivity() {
    var profile: ProfileObj? = null
    private lateinit var tomtomMap: TomTomMap;
    private var mapService:MapService = MapService(R.drawable.personpin)
    private var location: LocationServicesObj = LocationServicesObj(true, mapService!!)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ){

            profile = intent.getSerializableExtra("profile", ProfileObj::class.java)
        }else{

            profile = intent.getSerializableExtra("profile") as ProfileObj
        }

        val textViewName = findViewById<TextView>(R.id.textName)
        val img = findViewById<ImageView>(R.id.imageVPerfilPic)
        textViewName.text = profile?.name
        img.setImageBitmap(profile?.image)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as? MapFragment
        mapService.startMap(mapFragment)
        location.startLocationUpdatesEveryMinute()
    }
}