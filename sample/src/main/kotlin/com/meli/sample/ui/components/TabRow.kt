package com.meli.sample.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.meli.sample.ui.TabSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabRow(
  selectedTabIndex: Int,
  modifier: Modifier = Modifier,
  onTabSelected: (TabSection) -> Unit,
) {
  PrimaryTabRow(selectedTabIndex = selectedTabIndex, modifier = modifier) {
    TabSection.entries.forEachIndexed { index, section ->
      Tab(
        selected = selectedTabIndex == index,
        onClick = { onTabSelected(section) },
        text = { Text(text = section.name, maxLines = 1, overflow = TextOverflow.Ellipsis) },
      )
    }
  }
}
