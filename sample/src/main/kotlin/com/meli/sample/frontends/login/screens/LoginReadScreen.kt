package com.meli.sample.frontends.login.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meli.sample.R
import com.meli.sample.frontends.login.LoginViewModel
import com.meli.sample.ui.components.DeleteAllButton
import com.meli.sample.ui.components.StoreServerSelector
import com.meli.sample.ui.utils.ToastHelper

@Composable
fun LoginReadScreen(
  viewModel: LoginViewModel,
  modifier: Modifier = Modifier,
) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  var currentToast by remember { mutableStateOf<Toast?>(null) }

  ToastHelper(
    message = uiState.value.messageToShow,
    toast = currentToast,
    onToastCreated = { currentToast = it },
    onMessageConsumed = viewModel::onMessageConsumed
  )

  LaunchedEffect(Unit) {
    viewModel.loadSavedData()
  }

  Surface(color = MaterialTheme.colorScheme.background) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState())
    ) {
      Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Spacer(modifier = Modifier)
        StoreServerSelector(
          source = uiState.value.mode,
          onSourceClick = {
            viewModel.onSourceClick()
            viewModel.loadSavedData()
          }
        )

        val isDatePresent = !uiState.value.readData.isNullOrBlank()
        if (isDatePresent) {
          Text(uiState.value.readData!!)
        } else if (!uiState.value.isLoading) {
          Text(
            text = stringResource(R.string.no_saved_data_for, uiState.value.mode.name),
            modifier = Modifier.align(Alignment.CenterHorizontally),
          )
        }
        Spacer(modifier = Modifier.height(24.dp))
        DeleteAllButton(
          enabled = isDatePresent,
          onClick = viewModel::onClearAllClick,
          modifier = Modifier.align(Alignment.CenterHorizontally)
        )
      }

      if (uiState.value.isLoading) {
        CircularProgressIndicator(
          modifier = Modifier.align(Alignment.TopCenter).padding(top = 56.dp)
        )
      }
    }
  }
}
