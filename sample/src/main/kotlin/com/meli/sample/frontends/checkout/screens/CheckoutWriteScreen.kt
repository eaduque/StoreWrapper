package com.meli.sample.frontends.checkout.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.meli.sample.R
import com.meli.sample.frontends.checkout.CheckoutViewModel
import com.meli.sample.frontends.checkout.storage.CheckoutStorageGroup
import com.meli.sample.ui.components.AddKeyCardField
import com.meli.sample.ui.components.CheckoutCardInfo
import com.meli.sample.ui.components.DeleteCardField
import com.meli.sample.ui.components.SectionDivider
import com.meli.sample.ui.components.StoreServerSelector
import com.meli.sample.ui.utils.ToastHelper

@Composable
fun CheckoutWriteScreen(
  viewModel: CheckoutViewModel,
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

  Surface(color = MaterialTheme.colorScheme.background) {
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
      Spacer(modifier = Modifier)
      StoreServerSelector(source = uiState.value.mode, onSourceClick = viewModel::onSourceClick)
      CheckoutCardInfo(
        checkoutInfo = uiState.value.checkoutInfo,
        dataModified = uiState.value.checkoutInfo.modified,
        onAddressDirectionChanged = viewModel::onCheckoutDirectionChanged,
        onCityChanged = viewModel::onCityChanged,
        onKeepSessionActiveChanged = viewModel::onKeepCouponActiveChanged,
        onSaveInfoClick = viewModel::onSaveCheckoutInfoClick,
        modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
      )
      SectionDivider(stringResource(R.string.primitive_types_section_title))
      AddKeyCardField(
        fieldInfo = uiState.value.stringField,
        label = "String",
        placeholder = stringResource(R.string.enter_string),
        keyName = CheckoutStorageGroup.TokenStorage.name,
        onValueChange = viewModel::onStringFieldChanged,
        onClick = viewModel::onSaveStringField,
        modifier = Modifier.fillMaxWidth()
      )
      AddKeyCardField(
        fieldInfo = uiState.value.intField,
        label = "Int",
        placeholder = stringResource(R.string.enter_int),
        keyName = CheckoutStorageGroup.CounterStorage.name,
        keyboardType = KeyboardType.Number,
        onValueChange = viewModel::onIntFieldChanged,
        onClick = viewModel::onSaveIntField,
        modifier = Modifier.fillMaxWidth()
      )
      SectionDivider(stringResource(R.string.remove_key_section_title))
      DeleteCardField(
        entries = uiState.value.entries,
        selectedKeyName = uiState.value.selectedKeyNameToDelete,
        onSelectionChange = viewModel::onSelectedKeyNameToDeleteChanged,
        onClick = viewModel::onDeleteKeyClick
      )
      Spacer(modifier = Modifier.height(72.dp))
    }
  }
}
