package com.meli.sample.frontends.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.sample.di.DataStoreStorageManager
import com.meli.sample.di.SharedPreferencesStorageManager
import com.meli.sample.frontends.checkout.storage.CheckoutStorageGroup
import com.meli.sample.models.FieldInfo
import com.meli.sample.ui.Source
import com.meli.storage.wrapper.core.StorageManager
import com.meli.storage.wrapper.core.keyOf
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class CheckoutViewModel @Inject constructor(
  @SharedPreferencesStorageManager private val spStoreManager: StorageManager,
  @DataStoreStorageManager private val dsStoreManager: StorageManager,
) : ViewModel() {

  private val _uiState = MutableStateFlow(CheckoutUiState())
  val uiState = _uiState.asStateFlow()

  private val addressKey = keyOf(CheckoutStorageGroup, CheckoutStorageGroup.AddressStorage)
  private val tokenKey = keyOf(CheckoutStorageGroup, CheckoutStorageGroup.TokenStorage)
  private val counterKey = keyOf(CheckoutStorageGroup, CheckoutStorageGroup.CounterStorage)

  init {
    _uiState.update {
      it.copy(
        entries = listOf(
          CheckoutStorageGroup.AddressStorage.name,
          CheckoutStorageGroup.TokenStorage.name,
          CheckoutStorageGroup.CounterStorage.name
        )
      )
    }
  }

  fun onSourceClick() {
    _uiState.update {
      it.copy(
        mode = if (it.mode == Source.SP) Source.DT else Source.SP,
        checkoutInfo = it.checkoutInfo.copy(modified = true),
        stringField = it.stringField.copy(modified = true),
        intField = it.intField.copy(modified = true)
      )
    }
  }

  fun onCheckoutDirectionChanged(direction: String) {
    _uiState.update { it.copy(checkoutInfo = it.checkoutInfo.copy(direction = direction, modified = true)) }
  }

  fun onCityChanged(city: String) {
    _uiState.update { it.copy(checkoutInfo = it.checkoutInfo.copy(city = city, modified = true)) }
  }

  fun onKeepCouponActiveChanged() {
    _uiState.update { it.copy(checkoutInfo = it.checkoutInfo.copy(hasPromotions = !it.checkoutInfo.hasPromotions, modified = true)) }
  }

  fun onSaveCheckoutInfoClick() {
    viewModelScope.launch {
      if (_uiState.value.mode == Source.SP) {
        spStoreManager.save(key = addressKey, value = _uiState.value.checkoutInfo)
      } else {
        dsStoreManager.save(key = addressKey, value = _uiState.value.checkoutInfo)
      }
    }
    _uiState.update { it.copy(
      checkoutInfo = it.checkoutInfo.copy(modified = false),
      messageToShow = "Key '${CheckoutStorageGroup.AddressStorage.name}' guardada en ${_uiState.value.mode.name}"
    )}
  }

  fun onStringFieldChanged(newValue: String) {
    if (_uiState.value.stringField.value != newValue) {
      val valueToSave = if (newValue.isBlank()) null else newValue
      _uiState.update { it.copy(stringField = FieldInfo(value = valueToSave, modified = true)) }
    }
  }

  fun onSaveStringField() {
    val valueToSave = _uiState.value.stringField.value ?: return
    viewModelScope.launch {
      if (_uiState.value.mode == Source.SP) {
        spStoreManager.save(key = tokenKey, value = valueToSave)
      } else {
        dsStoreManager.save(key = tokenKey, value = valueToSave)
      }
      _uiState.update { it.copy(
        stringField = it.stringField.copy(modified = false),
        messageToShow = "Key '${CheckoutStorageGroup.TokenStorage.name}' guardada en ${_uiState.value.mode.name}"
      )}
    }
  }

  fun onIntFieldChanged(newValue: String) {
    newValue.toIntOrNull()?.let { newIntValue ->
      if (_uiState.value.intField.value != newIntValue) {
        _uiState.update { it.copy(intField = FieldInfo(value = newIntValue, modified = true)) }
      }
    } ?: run {
      if (newValue.isBlank()) {
        _uiState.update { it.copy(intField = FieldInfo(value = null, modified = true)) }
      }
    }
  }

  fun onSaveIntField() {
    val valueToSave = _uiState.value.intField.value ?: return
    viewModelScope.launch {
      if (_uiState.value.mode == Source.SP) {
        spStoreManager.save(key = counterKey, value = valueToSave)
      } else {
        dsStoreManager.save(key = counterKey, value = valueToSave)
      }
      _uiState.update { it.copy(
        intField = it.intField.copy(modified = false),
        messageToShow = "Key '${CheckoutStorageGroup.CounterStorage.name}' guardada en ${_uiState.value.mode.name}"
      )}
    }
  }

  fun onSelectedKeyNameToDeleteChanged(keyName: String?) {
    _uiState.update { it.copy(selectedKeyNameToDelete = keyName) }
  }

  fun onDeleteKeyClick() {
    if (_uiState.value.selectedKeyNameToDelete.isNullOrBlank()) return

    viewModelScope.launch {
      val key = when (_uiState.value.selectedKeyNameToDelete) {
        CheckoutStorageGroup.AddressStorage.name -> addressKey
        CheckoutStorageGroup.TokenStorage.name -> tokenKey
        CheckoutStorageGroup.CounterStorage.name -> counterKey
        else -> return@launch
      }

      if (_uiState.value.mode == Source.SP) {
        spStoreManager.delete(key = key)
      } else {
        dsStoreManager.delete(key = key)
      }

      _uiState.update { it.copy(messageToShow = "Key '${_uiState.value.selectedKeyNameToDelete}' eliminada de ${_uiState.value.mode.name}") }
    }
  }

  fun onMessageConsumed() {
    _uiState.update { it.copy(messageToShow = null) }
  }

  fun loadSavedData() {
    _uiState.update { it.copy(isLoading = true) }
    viewModelScope.launch {
      val savedData = if (_uiState.value.mode == Source.SP) {
        val addressInfo = spStoreManager.read(addressKey)
        val authToken = spStoreManager.read(tokenKey)
        val counter = spStoreManager.read(counterKey)

        val stringResult = """
          ${addressInfo?.let { "Address: $it" } ?: ""}
          ${authToken?.let { "Token: $it" } ?: ""}
          ${counter?.let { "Counter: $it" } ?: ""}
        """.trimIndent()

        if (stringResult.isBlank()) null else stringResult
      } else {
        val addressInfo = dsStoreManager.read(addressKey)
        val authToken = dsStoreManager.read(tokenKey)
        val counter = dsStoreManager.read(counterKey)

        val stringResult = """
          ${addressInfo?.let { "Address: $it" } ?: ""}
          ${authToken?.let { "Token: $it" } ?: ""}
          ${counter?.let { "Counter: $it" } ?: ""}
        """.trimIndent()

        if (stringResult.isBlank()) null else stringResult
      }
      _uiState.update {
        it.copy(
          readData = savedData,
          isLoading = false,
          messageToShow = "Datos leídos de ${_uiState.value.mode.name}"
        )
      }
    }
  }

  fun onClearAllClick() {
    viewModelScope.launch {
      if (_uiState.value.mode == Source.SP) {
        spStoreManager.clearAll()
      } else {
        dsStoreManager.clearAll()
      }
      delay(100)
      loadSavedData()
    }
  }
}
