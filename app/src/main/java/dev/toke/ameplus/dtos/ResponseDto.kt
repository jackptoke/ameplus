package dev.toke.ameplus.dtos

data class ResponseDto(
    val statusCode: Int,
    val message: String = "",
    val data: Any?
)
