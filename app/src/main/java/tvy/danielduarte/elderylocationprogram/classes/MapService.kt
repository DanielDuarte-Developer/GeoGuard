package tvy.danielduarte.elderylocationprogram.classes

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.image.ImageFactory
import com.tomtom.sdk.map.display.marker.MarkerOptions
import com.tomtom.sdk.map.display.ui.compass.CompassButton
import com.tomtom.sdk.map.display.ui.currentlocation.CurrentLocationButton
import tvy.danielduarte.elderylocationprogram.Settings
import java.util.concurrent.CompletableFuture

class MapService {
    private lateinit var tomtomMap: TomTomMap;
    private var idImage = 0
    private var center:Location = Location("")
    private var idImageCenter = 0
    private var centerUpdateFuture: CompletableFuture<Location>? = null

    fun getCenterAsync(): CompletableFuture<Location> {
        centerUpdateFuture = CompletableFuture()
        return centerUpdateFuture!!
    }

    constructor(idImageMarkerPerson: Int){
        this.idImage = idImageMarkerPerson
    }

    constructor(idImageMarkerPerson: Int, idImageCenter: Int){
        this.idImage = idImageMarkerPerson
        this.idImageCenter = idImageCenter
    }

    fun startMap(mapFragment: MapFragment,locationServicesObj: LocationServicesObj,  activity: AppCompatActivity){
        mapFragment.getMapAsync { map ->
            tomtomMap = map
            mapFragment.compassButton.visibilityPolicy = CompassButton.VisibilityPolicy.Invisible
            mapFragment.currentLocationButton.visibilityPolicy = CurrentLocationButton.VisibilityPolicy.Invisible
            if (activity !is Settings) {
                locationServicesObj.currentLocation.let {
                    updateMarkerPosition(it.latitude, it.longitude)
                }
            }
            getCenterByLongClickEvent()
        }
    }

    private fun updateMarkerPosition(newLatitude: Double, newLongitude: Double) {
        if(tomtomMap != null){
            tomtomMap.removeMarkers()
            val marker = MarkerOptions(
                coordinate = GeoPoint(newLatitude, newLongitude),
                pinImage = ImageFactory.fromResource(idImage)
            )
            tomtomMap.addMarker(marker)
        }
    }
    fun getCenterByLongClickEvent(): Location {
        tomtomMap.addMapLongClickListener{ coordinate: GeoPoint ->
            center.latitude = coordinate.latitude
            center.longitude = coordinate.longitude
            tomtomMap.removeMarkers()
            val marker = MarkerOptions(
                coordinate = GeoPoint(coordinate.latitude, coordinate.longitude),
                pinImage = ImageFactory.fromResource(idImageCenter)
            )
            tomtomMap.addMarker(marker)

            centerUpdateFuture?.complete(center)
            return@addMapLongClickListener true
        }
        return center
    }
}