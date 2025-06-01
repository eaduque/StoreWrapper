package com.meli.storage.wrapper.core

import com.meli.storage.wrapper.store.KeyValueStore
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.builtins.serializer
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class StorageManagerTest {

  private val keyValueStore: KeyValueStore = mockk(relaxed = true)
  private val storageManager = StorageManager(keyValueStore)

  private val testKey = StorageKey(
    group = "testGroup",
    name = "testName",
    serializer = String.serializer()
  )

  @AfterTest
  fun tearDown() {
    clearMocks(keyValueStore)
  }

  @Test
  fun `save debería delegar el llamado a keyValueStore_put`() = runTest {
    storageManager.save(testKey, "hello")
    coVerify { keyValueStore.put(testKey, "hello") }
  }

  @Test
  fun `read debería delegar el llamado a keyValueStore_get y retornar el value`() = runTest {
    coEvery { keyValueStore.get(testKey) } returns "stored"

    val result = storageManager.read(testKey)

    coVerify { keyValueStore.get(testKey) }
    assertEquals("stored", result)
  }

  @Test
  fun `delete debería delegar el llamado a keyValueStore_remove`() = runTest {
    storageManager.delete(testKey)
    coVerify { keyValueStore.remove(testKey) }
  }

  @Test
  fun `clearAll debería delegar el llamado a keyValueStore_clear`() = runTest {
    storageManager.clearAll()
    coVerify { keyValueStore.clear() }
  }
}
