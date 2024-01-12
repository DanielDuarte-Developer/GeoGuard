package tvy.danielduarte.elderylocationprogram.classes

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import tvy.danielduarte.elderylocationprogram.Profile
import tvy.danielduarte.elderylocationprogram.R

class NotificationService(private val context: Context) {

    fun showNotification(trackedThingName:String){
        val activityIntent = Intent (context, Profile::class.java)
        val activityPendingIntent = PendingIntent.getActivity(context, 1, activityIntent)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_fence_24)
            .setContentTitle("GeoGuard")
            .setContentText("$trackedThingName is out of the GeoFence")
            .setContentIntent(activityPendingIntent)
    }


    companion object {
        const val CHANNEL_ID = "channel"
    }

}