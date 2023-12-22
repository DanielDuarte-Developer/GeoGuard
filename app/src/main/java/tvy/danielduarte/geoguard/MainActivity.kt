package tvy.danielduarte.elderylocationprogram

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationServices
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient // The Client which is used to get the device location
    private val REQUEST_LOCATION_PERMISSION = 1 // Integer of the request used by the OS to distinct mutiple requests
    private var currentLocation : Location ?= null // "Location" type object, it's the current location updated once the app is created
    private var center : Location ?= null // Center of the perimeter
    private var radius : Float ?= null // Radius of the perimeter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) // Initializing the client
        currentLocation = getCurrentLocation() as Location // Getting the location "returns a single location fix" I'm assuming it is a "Location" object
    }

    //Checks if the app doesn't have the necessary permissions, if not, asks for them, then gets the current location
    private fun getCurrentLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION ),REQUEST_LOCATION_PERMISSION )

            return
        }
        fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)

    }

    // Function called by the click of "btnCenter" sets the current device location to be the center of the perimeter in the "center" variable
    fun setPerimeterCenter(view: View) {
        center = getCurrentLocation() as Location
    }

    // Function called by the click of "btnRadius" sets the radius of the perimeter from the EditText "txtRadius" and gives the value to the "radius" variable

    fun setPerimeterRadius(view: View) {
        val txtRadius : EditText = findViewById(R.id.txtRadius)
        radius = txtRadius.text as Float
    }

    // Checks the distance of the device from the center CURRENTLY UNIMPLEMMENTED needs a timer for it to be called from time to time same with the method get current location
    private fun distanceFromCenter(currentLocation: Location, center: Location)  {

        return checkIfOutOfBounds(currentLocation.distanceTo(center), radius!!)
    }


    // Checks if the device is out of the perimeter
    private fun checkIfOutOfBounds(distance: Float, radius: Float){
        if (distance > radius){
            // Ligar ao controlo de animais o crocodilo fugiu da jaula
        }
    }}