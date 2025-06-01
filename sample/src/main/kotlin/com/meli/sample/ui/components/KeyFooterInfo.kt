package com.meli.sample.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColumnScope.KeyFooterInfo(keyName: String, modifier: Modifier = Modifier) {
  Text(
    text = "key: $keyName",
    modifier = modifier.align(Alignment.End).padding(end = 16.dp),
    style = TextStyle(fontSize = 9.sp, platformStyle = PlatformTextStyle(includeFontPadding = false))
  )
}
