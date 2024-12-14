package com.example.reposcount.data.repos.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val githubId: Int,
    val description: String?,
    val fullName: String,
    val htmlUrl: String,
    val name: String,
    val avatarUrl: String,
    val visibility: String,
    val private: Boolean
)