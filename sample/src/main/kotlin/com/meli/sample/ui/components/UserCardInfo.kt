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
import com.meli.sample.frontends.login.storage.LoginStorageGroup
import com.meli.sample.models.UserInfo

@Composable
fun UserCardInfo(
  userInfo: UserInfo,
  dataModified: Boolean,
  onUserNameChanged: (String) -> Unit,
  onPasswordChanged: (String) -> Unit,
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
          text = stringResource(R.string.user_info_title),
          modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = 8.dp),
          style = MaterialTheme.typography.titleMedium
        )
        TextField(
          value = userInfo.name,
          onValueChange = onUserNameChanged,
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text(stringResource(R.string.user_info_name)) },
          trailingIcon = {
            if (userInfo.name.isNotEmpty()) {
              IconButton(onClick = { onUserNameChanged("") }) {
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
          value = userInfo.password,
          onValueChange = onPasswordChanged,
          modifier = Modifier.fillMaxWidth(),
          placeholder = { Text(stringResource(R.string.user_info_pass)) },
          trailingIcon = {
            if (userInfo.password.isNotEmpty()) {
              IconButton(onClick = { onPasswordChanged("") }) {
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
            checked = userInfo.keepSessionActive,
            onCheckedChange = { onKeepSessionActiveChanged() }
          )
          Text(stringResource(R.string.user_info_keep_session_active))
        }
        Button(
          onClick = { focusManager.clearFocus(); onSaveInfoClick() },
          modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 8.dp),
          enabled = dataModified && userInfo.name.isNotBlank() && userInfo.password.isNotBlank()
        ) {
          Text(stringResource(R.string.save_info))
        }
      }
    }
    KeyFooterInfo(LoginStorageGroup.UserStorage.name)
  }
}
