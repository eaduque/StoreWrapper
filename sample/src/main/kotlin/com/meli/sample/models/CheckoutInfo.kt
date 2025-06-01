package com.meli.sample.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class CheckoutInfo(
  val direction: String,
  val city: String,
  val hasPromotions: Boolean,
  @Transient val modified: Boolean = false
)
