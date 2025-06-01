package com.meli.sample.frontends.checkout

import com.meli.sample.models.CheckoutInfo
import com.meli.sample.models.FieldInfo
import com.meli.sample.ui.Source

data class CheckoutUiState(
  val checkoutInfo: CheckoutInfo = CheckoutInfo("", "", false),
  val stringField: FieldInfo<String> = FieldInfo(null, false),
  val intField: FieldInfo<Int> = FieldInfo(null, false),
  val selectedKeyNameToDelete: String? = null,
  val entries: List<String> = emptyList(),
  val readData: String? = null,
  val isLoading: Boolean = false,
  val messageToShow: String? = null,
  val mode: Source = Source.SP
)
