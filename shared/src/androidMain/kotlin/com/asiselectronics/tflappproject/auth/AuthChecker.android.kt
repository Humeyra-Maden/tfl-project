package com.asiselectronics.tflappproject.auth

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.Result


actual fun isUserLoggedIn(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}

actual suspend fun loginUser(email: String, password: String): Result<Unit> {
    return suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.success(Unit))
                } else {
                    continuation.resume(
                        Result.failure(task.exception ?: Exception("Giriş Başarısız"))
                    )
                }
            }


    }
}

actual suspend fun registerUser(email: String, password: String): Result<Unit>{
    return suspendCancellableCoroutine { continuation ->
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Result.success(Unit))
                } else {
                    continuation.resume(
                        Result.failure(task.exception ?: Exception("Kayıt Başarısız"))
                    )
                }
            }

    }
}


actual fun getCurrentUserEmail(): String? {
    return FirebaseAuth.getInstance().currentUser?.email
}

actual fun logoutUser() {
    FirebaseAuth.getInstance().signOut()
}