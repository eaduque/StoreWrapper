package com.meli.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meli.sample.R
import com.meli.sample.models.FieldInfo

@Composable
fun <T> AddKeyCardField(
  fieldInfo: FieldInfo<T>,
  label: String,
  placeholder: String,
  keyName: String,
  keyboardType: KeyboardType = KeyboardType.Unspecified,
  onValueChange: (String) -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val focusManager = LocalFocusManager.current

  Column {
    OutlinedCard(modifier = modifier, shape = MaterialTheme.shapes.extraLarge) {
      Row(
        modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        TextField(
          value = fieldInfo.value?.toString().orEmpty(),
          onValueChange = onValueChange,
          modifier = Modifier.weight(1f),
          label = { Text(label, fontFamily = FontFamily.Monospace) },
          placeholder = { Text(placeholder, fontFamily = FontFamily.Monospace) },
          trailingIcon = {
            if (fieldInfo.value?.toString().orEmpty().isNotEmpty()) {
              IconButton(onClick = { onValueChange("") }) {
                Icon(
                  painter = painterResource(R.drawable.ic__cancel),
                  contentDescription = "Clear text"
                )
              }
            }
          },
          keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
          ),
          keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
          singleLine = true,
          colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
          )
        )
        Button(
          onClick = { focusManager.clearFocus(); onClick() },
          enabled = fieldInfo.modified && fieldInfo.value != null
        ) {
          Text(stringResource(R.string.save_info))
        }
      }
    }
    KeyFooterInfo(keyName)
  }
}
