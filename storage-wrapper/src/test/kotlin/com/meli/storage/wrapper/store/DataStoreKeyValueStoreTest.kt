package com.meli.storage.wrapper.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.meli.storage.wrapper.core.StorageKey
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class DataStoreKeyValueStoreTest {

  private val dataStore: DataStore<Preferences> = mockk()
  private val json = Json { ignoreUnknownKeys = true }
  private lateinit var store: DataStoreKeyValueStore

  private val testKeyString = StorageKey("grupo", "claveString", String.serializer())
  private val testValueString = "valor de prueba"
  private val testKey = StorageKey("grupo", "claveInt", Int.serializer())
  private val testValueInt = 123

  @BeforeTest
  fun configurar() {
    store = DataStoreKeyValueStore(dataStore, json)
  }

  @Test
  fun `put debe codificar valor y llamar a dataStore edit para un Int`() = runTest {
    coEvery { dataStore.updateData(any()) } returns mockk()
    store.put(testKey, testValueInt)

    coVerify { dataStore.updateData(any()) }
  }

  @Test
  fun `put debe codificar valor y llamar a dataStore edit para un String`() = runTest {
    coEvery { dataStore.updateData(any()) } returns mockk()
    store.put(testKeyString, testValueString)

    coVerify { dataStore.updateData(any()) }
  }

  @Test
  fun `get debe devolver valor decodificado cuando existe la clave`() = runTest {
    val keyPref = stringPreferencesKey(testKeyString.fullName)
    val prefsMock = mockk<Preferences>()
    every { prefsMock[keyPref] } returns json.encodeToString(testKeyString.serializer, testValueString)
    coEvery { dataStore.data } returns flowOf(prefsMock)

    val resultado = store.get(testKeyString)

    assertEquals(testValueString, resultado)
  }

  @Test
  fun `get debe devolver null cuando no existe la clave`() = runTest {
    val keyPref = stringPreferencesKey(testKeyString.fullName)
    val prefsMock = mockk<Preferences>()
    every { prefsMock[keyPref] } returns null
    coEvery { dataStore.data } returns flowOf(prefsMock)

    val resultado = store.get(testKeyString)

    assertNull(resultado)
  }

  @Test
  fun `get debe devolver null cuando ocurre error de deserializacion`() = runTest {
    val keyPref = stringPreferencesKey(testKeyString.fullName)
    val prefsMock = mockk<Preferences>()
    every { prefsMock[keyPref] } returns "valor no json válido"
    coEvery { dataStore.data } returns flowOf(prefsMock)

    val resultado = store.get(testKeyString)

    assertNull(resultado)
  }

  @Test
  fun `remove debe llamar a dataStore edit con remove de la clave`() = runTest {
    coEvery { dataStore.updateData(any()) } returns mockk()

    store.remove(testKeyString)

    coVerify(exactly = 1) { dataStore.updateData(any()) }
  }

  @Test
  fun `clear debe llamar a dataStore edit con clear`() = runTest {
    coEvery { dataStore.updateData(any()) } returns mockk()

    store.clear()

    coVerify(exactly = 1) { dataStore.updateData(any()) }
  }
}
