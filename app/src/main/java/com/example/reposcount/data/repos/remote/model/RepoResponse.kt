package com.example.reposcount.data.repos.remote.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    @Json(name = "id")
    val id: Int,
    @Json(name = "description")
    val description: String?,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "html_url")
    val htmlUrl: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "owner")
    val ownerResponse: OwnerResponse,
    @Json(name = "visibility")
    val visibility: String,
    @Json(name = "private")
    val private: Boolean
)