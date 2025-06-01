package com.meli.storage.wrapper.core

import com.meli.storage.wrapper.FrontendGroupTest
import com.meli.storage.wrapper.FrontendStorageGroupKeyTest
import com.meli.storage.wrapper.User
import kotlin.test.Test
import kotlin.test.assertEquals

class KeyOfTest {

  @Test
  fun `keyOf debería construir StorageKey correctamente`() {
    val key = keyOf(FrontendGroupTest, FrontendStorageGroupKeyTest)

    assertEquals("login", key.group)
    assertEquals("auth_token", key.name)
    assertEquals("login::auth_token", key.fullName)
    assertEquals(User.serializer(), key.serializer)
  }
}
