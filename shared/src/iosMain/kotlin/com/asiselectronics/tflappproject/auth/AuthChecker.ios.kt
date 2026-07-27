package com.asiselectronics.tflappproject.auth


actual fun isUserLoggedIn(): Boolean {
    return false // ileride iOS Firebase entegrasyonu (GitLive SDK) ile doldurulacak
}

actual suspend fun loginUser(email:String, password:String): Result<Unit>{
    return Result.failure(NotImplementedError("İOS firebase entegrasyonu daha yapılmadı"))
}

actual suspend fun registerUser(email: String, password: String): Result<Unit>{
    return Result.failure(NotImplementedError("İOS firebase entegrasyonu daha yapılmadı"))
}
actual fun getCurrentUserEmail(): String? = null

actual fun logoutUser() {
    // iOS Firebase entegrasyonu henüz yapılmadı
}