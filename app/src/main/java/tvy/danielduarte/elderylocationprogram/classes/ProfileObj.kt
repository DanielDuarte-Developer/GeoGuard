package tvy.danielduarte.elderylocationprogram

import android.graphics.Bitmap
import android.location.Location
import android.provider.ContactsContract.CommonDataKinds.Email
import tvy.danielduarte.elderylocationprogram.classes.GeoFenceObj
import tvy.danielduarte.elderylocationprogram.classes.NotificationTypeObj
import java.io.Serializable

class ProfileObj (Name: String, Image: Bitmap, Contact: Int, Email:String, GeoFenceObj: GeoFenceObj?, NotificationType: NotificationTypeObj):
    Serializable {
    var name: String = Name
    var image: Bitmap = Image
    var contact: Int = Contact
    var email: String = Email
    var geoFenceObj: GeoFenceObj? = GeoFenceObj
    var notificationType: NotificationTypeObj = NotificationType


}