package tvy.danielduarte.elderylocationprogram.classes

import com.tomtom.sdk.map.display.ui.MapFragment
import com.tomtom.sdk.location.GeoPoint
import com.tomtom.sdk.map.display.TomTomMap
import com.tomtom.sdk.map.display.image.ImageFactory
import com.tomtom.sdk.map.display.marker.MarkerOptions

class MapService (idImage: Int){
    private lateinit var tomtomMap: TomTomMap;
    private var idImage = idImage

    fun startMap(mapFragment: MapFragment?,locationServicesObj: LocationServicesObj){
        mapFragment?.getMapAsync { map ->
            tomtomMap = map

            locationServicesObj.currentLocation?.let {
                updateMarkerPosition(it.latitude, it.longitude)
            }


        }
    }
    fun updateMarkerPosition(newLatitude: Double, newLongitude: Double) {
        if(tomtomMap != null){
            tomtomMap.removeMarkers()
            val marker = MarkerOptions(
                coordinate = GeoPoint(newLatitude, newLongitude),
                pinImage = ImageFactory.fromResource(idImage)
            )
            tomtomMap.addMarker(marker)
        }
    }
}