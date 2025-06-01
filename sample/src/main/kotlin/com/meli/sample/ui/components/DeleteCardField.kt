package com.meli.sample.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meli.sample.R

@Composable
fun DeleteCardField(
  entries: List<String>,
  selectedKeyName: String?,
  onSelectionChange: (String?) -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val noSelectedKey = "None"
  var expanded by remember { mutableStateOf(false) }

  OutlinedCard(modifier = modifier, shape = MaterialTheme.shapes.extraLarge) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      Box(
        modifier = Modifier
          .weight(1f)
          .clickable(indication = null, interactionSource = null, onClick = { expanded = !expanded })
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainer, shape = MaterialTheme.shapes.extraLarge)
            .padding(8.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = if (selectedKeyName != noSelectedKey && !selectedKeyName.isNullOrBlank())
              selectedKeyName else stringResource(R.string.select_key_to_delete),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.titleMedium
          )
          Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
          expanded = expanded,
          onDismissRequest = { expanded = false },
        ) {
          DropdownMenuItem(
            text = { Text(text = noSelectedKey) },
            onClick = {
              onSelectionChange(null)
              expanded = false
            }
          )
          entries.forEach { storageKey ->
            DropdownMenuItem(
              text = { Text(text = storageKey) },
              onClick = {
                onSelectionChange(storageKey)
                expanded = false
              }
            )
          }
        }
      }
      Button(onClick = onClick, enabled = selectedKeyName != noSelectedKey && !selectedKeyName.isNullOrBlank()) {
        Text(stringResource(R.string.user_delete_key))
      }
    }
  }
}
