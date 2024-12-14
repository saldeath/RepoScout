package com.example.reposcount.data.repos.remote

import com.example.reposcount.data.repos.remote.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubService {

    @Headers("Accept: application/vnd.github+json")
    @GET("repos")
    suspend fun getRepos(
        @Query("page") limit: Int = 1,
        @Query("per_page") offset: Int = 10,
    ): List<RepoResponse>
}