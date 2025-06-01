package com.meli.sample.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.meli.storage.wrapper.core.StorageManager
import com.meli.storage.wrapper.store.DataStoreKeyValueStore
import com.meli.storage.wrapper.store.SharedPrefsKeyValueStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

  @Provides
  @Singleton
  fun provideJson(): Json = Json { ignoreUnknownKeys = true }

  @Provides
  @Singleton
  @SharedPreferencesStorageManager
  fun provideStorageManagerForSharedPreferences(
    @ApplicationContext context: Context,
    json: Json
  ): StorageManager {
    val sharedPreferences = context.getSharedPreferences("my_storage", MODE_PRIVATE)
    return StorageManager(SharedPrefsKeyValueStore(sharedPreferences, json))
  }

  @Provides
  @Singleton
  @DataStoreStorageManager
  fun provideStorageManagerForDataStoreKeyValueStore(
    @ApplicationContext context: Context,
    json: Json
  ): StorageManager {
    val dataStore = PreferenceDataStoreFactory.create {
      context.preferencesDataStoreFile("my_storage")
    }
    return StorageManager(DataStoreKeyValueStore(dataStore, json))
  }
}
