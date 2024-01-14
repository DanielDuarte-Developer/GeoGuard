package tvy.danielduarte.elderylocationprogram.classes

import android.content.Context
import android.content.Intent
import android.telephony.SmsManager


class NotificationTypeObj(SendMessage:Boolean, SendEmail:Boolean, SendNotification:Boolean, Contact: Int, Email: String) {

    var contact:Int = Contact
    var email:String = Email

    private var sendMessage:Boolean = SendMessage
    private var sendEmail:Boolean = SendEmail
    private var sendNotification:Boolean = SendNotification

    fun sendMessage(context: Context, contact: Int, message: String){
        if (sendMessage == true) {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(contact.toString(),null, message, null, null)
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

            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent);
            }
        }
    }

    fun sendNotification(message:String, context: Context){
        if (sendNotification == true){
            var notificationService = NotificationService(context)
            notificationService.showNotification(message)
        }
    }
}