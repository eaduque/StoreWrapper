package com.meli.sample.frontends.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meli.sample.frontends.login.storage.LoginStorageGroup
import com.meli.sample.di.DataStoreStorageManager
import com.meli.sample.di.SharedPreferencesStorageManager
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
class LoginViewModel @Inject constructor(
  @SharedPreferencesStorageManager private val spStoreManager: StorageManager,
  @DataStoreStorageManager private val dsStoreManager: StorageManager,
) : ViewModel() {

  private val _uiState = MutableStateFlow(LoginUiState())
  val uiState = _uiState.asStateFlow()

  private val userKey = keyOf(LoginStorageGroup, LoginStorageGroup.UserStorage)
  private val tokenKey = keyOf(LoginStorageGroup, LoginStorageGroup.TokenStorage)
  private val counterKey = keyOf(LoginStorageGroup, LoginStorageGroup.CounterStorage)

  init {
    _uiState.update {
      it.copy(
        entries = listOf(
          LoginStorageGroup.UserStorage.name,
          LoginStorageGroup.TokenStorage.name,
          LoginStorageGroup.CounterStorage.name
        )
      )
    }
  }

  fun onSourceClick() {
    _uiState.update {
      it.copy(
        mode = if (it.mode == Source.SP) Source.DT else Source.SP,
        userInfo = it.userInfo.copy(modified = true),
        stringField = it.stringField.copy(modified = true),
        intField = it.intField.copy(modified = true)
      )
    }
  }

  fun onUserNameChanged(name: String) {
    _uiState.update { it.copy(userInfo = it.userInfo.copy(name = name, modified = true)) }
  }

  fun onPasswordChanged(password: String) {
    _uiState.update { it.copy(userInfo = it.userInfo.copy(password = password, modified = true)) }
  }

  fun onKeepSessionActiveChanged() {
    _uiState.update { it.copy(userInfo = it.userInfo.copy(keepSessionActive = !it.userInfo.keepSessionActive, modified = true)) }
  }

  fun onSaveUserInfoClick() {
    viewModelScope.launch {
      if (_uiState.value.mode == Source.SP) {
        spStoreManager.save(key = userKey, value = _uiState.value.userInfo)
      } else {
        dsStoreManager.save(key = userKey, value = _uiState.value.userInfo)
      }
    }
    _uiState.update { it.copy(
      userInfo = it.userInfo.copy(modified = false),
      messageToShow = "Key '${LoginStorageGroup.UserStorage.name}' guardada en ${_uiState.value.mode.name}"
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
        messageToShow = "Key '${LoginStorageGroup.TokenStorage.name}' guardada en ${_uiState.value.mode.name}"
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
        messageToShow = "Key '${LoginStorageGroup.CounterStorage.name}' guardada en ${_uiState.value.mode.name}"
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
        LoginStorageGroup.UserStorage.name -> userKey
        LoginStorageGroup.TokenStorage.name -> tokenKey
        LoginStorageGroup.CounterStorage.name -> counterKey
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
        val userInfo = spStoreManager.read(userKey)
        val authToken = spStoreManager.read(tokenKey)
        val counter = spStoreManager.read(counterKey)

        val stringResult = """
          ${userInfo?.let { "User: $it" } ?: ""}
          ${authToken?.let { "Token: $it" } ?: ""}
          ${counter?.let { "Counter: $it" } ?: ""}
        """.trimIndent()

        if (stringResult.isBlank()) null else stringResult
      } else {
        val userInfo = dsStoreManager.read(userKey)
        val authToken = dsStoreManager.read(tokenKey)
        val counter = dsStoreManager.read(counterKey)

        val stringResult = """
          ${userInfo?.let { "User: $it" } ?: ""}
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
