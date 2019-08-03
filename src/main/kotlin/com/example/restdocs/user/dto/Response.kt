package com.example.restdocs.user.dto

data class Response<T>(
        val code: Int,
        val message: String,
        val data: T?,
        val error: T?
) {

  companion object {
    fun success(): Response<Unit> = success(null)
    fun <T> success(data: T?): Response<T> = Response(200, "OK", data, null)
    fun <T> error(error: T?): Response<T> = Response(500, "Server Error", null, error)
  }
}