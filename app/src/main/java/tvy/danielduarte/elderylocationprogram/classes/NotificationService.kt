package tvy.danielduarte.elderylocationprogram.classes

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import tvy.danielduarte.elderylocationprogram.Profile
import tvy.danielduarte.elderylocationprogram.R

class NotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(trackedThingName:String){
        val activityIntent = Intent(context, Profile::class.java)
        val activityPendingIntent = PendingIntent.getActivity(context, 1 ,activityIntent,
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_fence_24)
            .setContentTitle("GeoGuard")
            .setContentText("$trackedThingName is out of the GeoFence")
            .setContentIntent(activityPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(1, notification)
    }


    companion object {
        const val CHANNEL_ID = "channel"
    }

}