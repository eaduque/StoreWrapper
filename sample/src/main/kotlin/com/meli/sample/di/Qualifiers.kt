package com.meli.sample.di

import jakarta.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SharedPreferencesStorageManager

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataStoreStorageManager
