package com.meli.sample.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.meli.sample.R
import com.meli.sample.ui.Mode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(mode: Mode, modifier: Modifier = Modifier) {
  val textResId = if (mode == Mode.WRITE) R.string.write_mode_title else R.string.read_mode_title

  CenterAlignedTopAppBar(
    title = {
      Text(text = stringResource(textResId), maxLines = 1, overflow = TextOverflow.Ellipsis)
    },
    modifier = modifier,
  )
}
