package com.meli.storage.wrapper.core

import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals

class StorageKeyTest {

  @Serializable
  data class User(val name: String, val age: Int)

  @Test
  fun `fullName debe concatenar grupo y nombre correctamente`() {
    val group = "login_screen"
    val keyName = "user_info"
    val serializer = User.serializer()

    val storageKey = StorageKey(group, keyName, serializer)

    assertEquals("login_screen::user_info", storageKey.fullName)
  }
}
