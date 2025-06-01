package com.meli.sample.ui.utils

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun ToastHelper(
  message: String?,
  toast: Toast?,
  onToastCreated: (Toast?) -> Unit,
  onMessageConsumed: () -> Unit,
) {
  val context = LocalContext.current

  LaunchedEffect(message) {
    toast?.cancel()
    if (message == null) return@LaunchedEffect
    val currentToast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
    onToastCreated(currentToast)
    currentToast.show()
    delay(2000)
    onMessageConsumed()
  }

  DisposableEffect(toast) {
    onDispose {
      toast?.cancel()
    }
  }
}
