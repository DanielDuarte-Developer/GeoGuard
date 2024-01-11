package tvy.danielduarte.elderylocationprogram.classes

import android.location.Location

class GeoFenceObj (CurrentLocation: Location, Center:Location, Radius: Float){

    var currentLocation : Location = CurrentLocation
    var center : Location = Center
    var radius : Float = Radius
    var outOfbounds: Boolean = false

    fun distanceFromCenter()  {
        if(radius != null){
            return checkIfOutOfBounds(currentLocation.distanceTo(center), radius)
        }
    }

    private fun checkIfOutOfBounds(distance: Float, radius: Float){
        if (distance > radius){
            outOfbounds = true

        }else{
            outOfbounds = false
        }

    }
}