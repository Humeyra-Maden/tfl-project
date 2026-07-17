package com.asiselectronics.tflappproject.presentation.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asiselectronics.tflappproject.auth.registerUser
import com.asiselectronics.tflappproject.presentation.auth.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSuccess: Boolean = false

)

class RegisterViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEmailChange(value: String){
        _uiState.value = _uiState.value.copy(email = value, errorMessage = null)
    }

    fun onPasswordChange(value: String){
        _uiState.value = _uiState.value.copy(password = value, errorMessage = null)
    }

    fun onConfirmPasswordChange(value: String){
        _uiState.value = _uiState.value.copy(confirmPassword = value, errorMessage = null)
    }

    fun register(){
        val state = _uiState.value

        if(state.email.isBlank() || state.password.isBlank()) {
            _uiState.value =state.copy(errorMessage = "E-posta ve Şifre Boş Olamaz")
            return
        }

        if(state.password.length<8){
            _uiState.value = state.copy(errorMessage ="Şifre En Az 8 Karakter Olmalıdır!")
            return
        }

        if(state.password != state.confirmPassword){
            _uiState.value = state.copy(errorMessage = "Şifreler Eşleşmiyor!")
            return
        }

        viewModelScope.launch{
            _uiState.value = state.copy(isLoading = true, errorMessage = null)
            val result = registerUser(state.email, state.password)
            result.fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(isLoading = false, registerSuccess = true)
                },
                onFailure = {error ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = error.message ?: "Kayıt Başarısız"
                    )
                }
            )
        }
    }
}