package dev.toke.ameplus.models

data class AuthState(
    val isLoading: Boolean = false,
    val signInUsername: String = ""
)
