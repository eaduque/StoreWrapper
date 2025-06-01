package com.meli.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionDivider(text: String, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    HorizontalDivider(modifier = modifier.weight(1f))
    Text(
      text = text,
      modifier = Modifier.padding(horizontal = 4.dp),
      fontSize = 11.sp,
      fontFamily = FontFamily.Monospace,
    )
    HorizontalDivider(modifier = modifier.weight(1f))
  }
}
