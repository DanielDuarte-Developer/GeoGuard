package tvy.danielduarte.elderylocationprogram.classes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Contactables
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


class NotificationTypeObj(SendMessage:Boolean, SendEmail:Boolean, SendNotification:Boolean, Contact: Int, Email: String) {

    var contact:Int = Contact
    var email:String = Email

    private var sendMessage:Boolean = SendMessage
    private var sendEmail:Boolean = SendEmail
    private var sendNotification:Boolean = SendNotification

    fun sendMessage(){
        if (sendMessage == true){

        }
    }

    fun sendEmail(context: Context, recipient: String, subject: String, message: String){
        if (sendEmail == true){
            val intent = Intent(Intent.ACTION_SEND).apply{
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, recipient)
                putExtra(Intent.EXTRA_SUBJECT, subject)
                putExtra(Intent.EXTRA_TEXT, message)

            }
            context.startActivity(intent)

        }
    }

    fun sendNotification(message:String, context: Context){
        if (sendNotification == true){
            var notificationService = NotificationService(context)
            notificationService.showNotification(message)
        }
    }
}