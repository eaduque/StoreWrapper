package com.meli.sample.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.meli.sample.R

@Composable
fun DeleteAllButton(enabled: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
  Button(onClick = onClick, modifier = modifier, enabled = enabled) {
    Text(stringResource(R.string.delete_all))
  }
}
