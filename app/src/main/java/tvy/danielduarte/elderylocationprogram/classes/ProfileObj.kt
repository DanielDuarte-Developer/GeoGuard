package tvy.danielduarte.elderylocationprogram

import android.graphics.Bitmap
import android.location.Location
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj

class ProfileObj (Name: String, Image: Bitmap, GeoFenceObj: GeoFenceObj, NotificationType: NotificationTypeObj){
    var name: String = Name
    var image: Bitmap = Image
    var geoFenceObj: GeoFenceObj = GeoFenceObj
    var notificationType: NotificationTypeObj = NotificationType


}