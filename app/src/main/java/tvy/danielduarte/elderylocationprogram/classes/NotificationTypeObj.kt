package tvy.danielduarte.elderylocationprogram.classes

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.ContextCompat.getSystemService


class NotificationTypeObj(Message:Boolean, Email:Boolean, Notification:Boolean) {
    var message:Boolean = Message
    var email:Boolean = Email
    var notification:Boolean = Notification

    fun sendMessage(){
        if (message == true){

        }
    }

    fun sendEmail(){
        if (email == true){

        }
    }

    fun sendNotification(message:String, context: Context){
        if (notification == true){
            var notificationService = NotificationService(context)
            notificationService.showNotification(message)
        }
    }
}