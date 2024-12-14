package com.example.reposcount.presentation.repodetails.textProvider

import android.content.Context
import com.example.reposcount.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RepoDetailsTextProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : RepoDetailsTextProvider {

    override val yes: String = context.getString(R.string.repo_details_yes)
    override val no: String = context.getString(R.string.repo_details_no)
}