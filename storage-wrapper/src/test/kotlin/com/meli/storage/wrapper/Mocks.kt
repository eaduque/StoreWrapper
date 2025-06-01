package com.meli.storage.wrapper

import com.meli.storage.wrapper.core.StorageGroup
import com.meli.storage.wrapper.core.StorageName
import kotlinx.serialization.Serializable

@Serializable
internal data class User(val name: String)

internal object FrontendGroupTest : StorageGroup {
  override val name: String = "login"
}

internal object FrontendStorageGroupKeyTest : StorageName<User, StorageGroup> {
  override val name: String = "auth_token"
}
