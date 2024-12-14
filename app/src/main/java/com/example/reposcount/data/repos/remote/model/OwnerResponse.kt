package com.example.reposcount.data.repos.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String
)