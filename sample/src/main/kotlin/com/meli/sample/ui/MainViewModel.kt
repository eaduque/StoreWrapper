package com.meli.sample.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

  private val _uiState = MutableStateFlow(MainUiState())
  val uiState = _uiState.asStateFlow()

  fun onToggleMode() {
    _uiState.update { it.copy(mode = if (it.mode == Mode.WRITE) Mode.READ else Mode.WRITE) }
  }

  fun onSelectedTab(tab: TabSection) {
    _uiState.update { it.copy(selectedTab = tab) }
  }
}
