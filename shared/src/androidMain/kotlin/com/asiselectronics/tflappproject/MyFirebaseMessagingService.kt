package com.asiselectronics.tflappproject

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Yeni token: $token")
        // İleride: bu token'ı backend'e/Firestore'a kaydedeceğiz
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("FCM", "Bildirim alındı: ${message.notification?.title} - ${message.notification?.body}")
        // İleride: sistem bildirimi olarak gösterme kodu buraya eklenecek
    }
}