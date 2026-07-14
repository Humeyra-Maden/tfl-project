package com.asiselectronics.tflappproject.auth

import com.google.firebase.auth.FirebaseAuth

actual fun isUserLoggedIn(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}