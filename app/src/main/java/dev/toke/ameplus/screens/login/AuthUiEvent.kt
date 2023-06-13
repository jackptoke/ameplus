package dev.toke.ameplus.screens.login

sealed class AuthUiEvent {
    data class SignInUsernameChanged(val value: String): AuthUiEvent()
    object SignIn: AuthUiEvent()
}