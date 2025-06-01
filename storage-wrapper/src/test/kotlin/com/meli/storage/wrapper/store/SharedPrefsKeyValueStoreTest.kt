package com.meli.storage.wrapper.store

import android.content.SharedPreferences
import com.meli.storage.wrapper.core.StorageKey
import com.meli.storage.wrapper.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SharedPrefsKeyValueStoreTest {

  private val sharedPreferences: SharedPreferences = mockk(relaxed = true)
  private val editor: SharedPreferences.Editor = mockk(relaxed = true)
  private val json = Json
  private lateinit var store: SharedPrefsKeyValueStore

  private val key = StorageKey("testGroup", "testKey", String.serializer())

  @BeforeTest
  fun setUp() {
    every { sharedPreferences.edit() } returns editor
    store = SharedPrefsKeyValueStore(sharedPreferences, json)
  }

  @Test
  fun `put debe guardar el dato primitivo directamente`() = runTest {
    val stringKey = StorageKey("testGroup", "stringKey", String.serializer())
    store.put(stringKey, "hello")

    verify { editor.putString("testGroup::stringKey", "hello") }
    verify { editor.apply() }
  }

  @Test
  fun `put debe guardar un objeto complejo desde JSON`() = runTest {
    val usuarioKey = StorageKey("usuarios", "nombre", User.serializer())
    val usuario = User("Ana")

    store.put(usuarioKey, usuario)

    val expectedJson = json.encodeToString(User.serializer(), usuario)
    verify { editor.putString("usuarios::nombre", expectedJson) }
    verify { editor.apply() }
  }

  @Test
  fun `get debe devolver dato primitivo directamente`() = runTest {
    every { sharedPreferences.contains("testGroup::testKey") } returns true
    every { sharedPreferences.getString("testGroup::testKey", null) } returns "stored"

    val result = store.get(key)

    assertEquals("stored", result)
  }

  @Test
  fun `get debe devolver null si la clave no existe`() = runTest {
    every { sharedPreferences.contains("testGroup::testKey") } returns false

    val result = store.get(key)

    assertNull(result)
  }

  @Test
  fun `get debe devolver objeto complejo desde JSON`() = runTest {
    val usuarioKey = StorageKey("usuarios", "nombre", User.serializer())
    val jsonValue = json.encodeToString(User.serializer(), User("Ana"))

    every { sharedPreferences.contains("usuarios::nombre") } returns true
    every { sharedPreferences.getString("usuarios::nombre", null) } returns jsonValue

    val result = store.get(usuarioKey)

    assertEquals(User("Ana"), result)
  }

  @Test
  fun `remove debe eliminar la clave de SharedPreferences`() = runTest {
    store.remove(key)

    verify { editor.remove("testGroup::testKey") }
    verify { editor.apply() }
  }

  @Test
  fun `clear debe limpiar SharedPreferences`() = runTest {
    store.clear()

    verify { editor.clear() }
    verify { editor.apply() }
  }
}
