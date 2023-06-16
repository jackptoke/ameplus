package dev.toke.ameplus.models

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.time.ZoneId


@Serializable
data class TokenResponse(
    val expiration: String = "",
    val message: String = "",
    val name: String = "",
    val refreshToken: String = "",
    val statusCode: Int = 0,
    val token: String = "",
    val username: String = ""
)