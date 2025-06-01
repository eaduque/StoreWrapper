package com.meli.sample.frontends.login

import com.meli.sample.models.FieldInfo
import com.meli.sample.models.UserInfo
import com.meli.sample.ui.Source

data class LoginUiState(
  val userInfo: UserInfo = UserInfo("", "", false),
  val stringField: FieldInfo<String> = FieldInfo(null, false),
  val intField: FieldInfo<Int> = FieldInfo(null, false),
  val selectedKeyNameToDelete: String? = null,
  val entries: List<String> = emptyList(),
  val readData: String? = null,
  val isLoading: Boolean = false,
  val messageToShow: String? = null,
  val mode: Source = Source.SP
)
