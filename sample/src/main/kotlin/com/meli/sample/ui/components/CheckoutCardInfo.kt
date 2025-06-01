package com.meli.sample.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.meli.sample.R
import com.meli.sample.frontends.checkout.storage.CheckoutStorageGroup
import com.meli.sample.models.CheckoutInfo

@Composable
fun CheckoutCardInfo(
  checkoutInfo: CheckoutInfo,
  dataModified: Boolean,
  onAddressDirectionChanged: (String) -> Unit,
  onCityChanged: (String) -> Unit,
  onKeepSessionActiveChanged: () -> Unit,
  onSaveInfoClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current

  Column(modifier = modifier) {
    Card(shape = MaterialTheme.shapes.extraLarge) {
      Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        Text(
          text = stringResource(R.string.checkout_info_title),
          modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 8.dp),
          style = MaterialTheme.typography.titleMedium
        )
        TextField(
          value = checkoutInfo.direction,
          onValueChange = onAddressDirectionChanged,
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text(stringResource(R.string.checkout_info_direction)) },
          trailingIcon = {
            if (checkoutInfo.direction.isNotEmpty()) {
              IconButton(onClick = { onAddressDirectionChanged("") }) {
                Icon(
                  painter = painterResource(R.drawable.ic__cancel),
                  contentDescription = "Clear text"
                )
              }
            }
          },
          keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
          keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
          singleLine = true,
        )
        TextField(
          value = checkoutInfo.city,
          onValueChange = onCityChanged,
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text(stringResource(R.string.checkout_info_city)) },
          trailingIcon = {
            if (checkoutInfo.city.isNotEmpty()) {
              IconButton(onClick = { onCityChanged("") }) {
                Icon(
                  painter = painterResource(R.drawable.ic__cancel),
                  contentDescription = "Clear text"
                )
              }
            }
          },
          keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
          keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
          singleLine = true,
        )
        Row(
          modifier = Modifier.clickable(
            indication = null,
            interactionSource = null,
            onClick = onKeepSessionActiveChanged
          ),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Checkbox(
            checked = checkoutInfo.hasPromotions,
            onCheckedChange = { onKeepSessionActiveChanged() }
          )
          Text(stringResource(R.string.checkout_info_has_promotions))
        }
        Button(
          onClick = onSaveInfoClick,
          modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp),
          enabled = dataModified,
        ) {
          Text(stringResource(R.string.save_info))
        }
      }
    }
    KeyFooterInfo(CheckoutStorageGroup.AddressStorage.name)
  }
}
