package com.meli.sample.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserInfo(
  val name: String,
  val password: String,
  val keepSessionActive: Boolean,
  @Transient val modified: Boolean = false
)
