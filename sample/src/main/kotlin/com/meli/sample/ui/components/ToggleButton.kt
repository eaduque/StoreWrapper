package com.meli.sample.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meli.sample.R
import com.meli.sample.ui.Mode

@Composable
fun ToggleButton(mode: Mode, modifier: Modifier = Modifier, onClick: () -> Unit) {
  val imageResId = if (mode == Mode.WRITE) R.drawable.ic__read_mode else R.drawable.ic__write_mode
  val contentDescriptionRedId = if (mode == Mode.WRITE) R.string.change_to_read_mode else R.string.change_to_write_mode

  FloatingActionButton(onClick = onClick, modifier = modifier) {
    Image(
      painter = painterResource(imageResId),
      contentDescription = stringResource(contentDescriptionRedId),
      modifier = Modifier.size(44.dp)
    )
  }
}
