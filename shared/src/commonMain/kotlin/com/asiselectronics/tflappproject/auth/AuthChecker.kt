package com.asiselectronics.tflappproject.auth

import kotlin.Result

expect fun isUserLoggedIn(): Boolean

expect suspend fun loginUser(email: String, password:String): Result<Unit>

expect suspend fun registerUser(email: String, password: String): Result<Unit>