package com.example.reposcount.presentation.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val rSDispatcher: RSDispatchers)

enum class RSDispatchers {
    IO
}