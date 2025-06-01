package com.meli.sample.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.meli.sample.R
import com.meli.sample.ui.Source

@Composable
fun StoreServerSelector(source: Source, onSourceClick: () -> Unit, modifier: Modifier = Modifier) {
  val colors = SwitchDefaults.colors().copy()
  val checked = source == Source.DT

  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {
    Switch(
      checked = checked,
      onCheckedChange = { onSourceClick() },
      thumbContent = if (checked) {
        {
          Image(
            painter = painterResource(R.drawable.ic__data_store),
            contentDescription = null,
            modifier = Modifier.size(SwitchDefaults.IconSize),
          )
        }
      } else {
        {
          Image(
            painter = painterResource(R.drawable.ic__shared_preferences),
            contentDescription = null,
            modifier = Modifier.size(SwitchDefaults.IconSize),
          )
        }
      },
      colors = colors.copy(
        uncheckedIconColor = colors.checkedIconColor,
        uncheckedThumbColor = colors.checkedThumbColor,
        uncheckedTrackColor = colors.checkedTrackColor,
        uncheckedBorderColor = colors.checkedBorderColor,
      )
    )
    Text(
      text = if (checked) "DataStore" else "SharedPreferences",
      style = MaterialTheme.typography.titleMedium,
    )
  }
}
