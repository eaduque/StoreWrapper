package com.meli.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.meli.sample.frontends.checkout.CheckoutViewModel
import com.meli.sample.frontends.login.LoginViewModel
import com.meli.sample.ui.MainScreen
import com.meli.sample.ui.MainUiState
import com.meli.sample.ui.MainViewModel
import com.meli.sample.ui.Mode
import com.meli.sample.ui.TabSection
import com.meli.sample.ui.components.TabRow
import com.meli.sample.ui.components.ToggleButton
import com.meli.sample.ui.components.TopAppBar
import com.meli.sample.ui.theme.StoreWrapperTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    var uiState: MainUiState by mutableStateOf(MainUiState())

    lifecycleScope.launch {
      lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
        viewModel.uiState.onEach { uiState = it }.collect()
      }
    }

    setContent {
      StoreWrapperTheme {
        val navController = rememberNavController()
        val loginViewModel: LoginViewModel = hiltViewModel()
        val checkoutViewModel: CheckoutViewModel = hiltViewModel()

        val destination = remember(uiState.mode, uiState.selectedTab) {
          when (uiState.mode) {
            Mode.WRITE -> when (uiState.selectedTab) {
              TabSection.LOGIN -> "login_write"
              TabSection.CHECKOUT -> "checkout_write"
            }
            Mode.READ -> when (uiState.selectedTab) {
              TabSection.LOGIN -> "login_read"
              TabSection.CHECKOUT -> "checkout_read"
            }
          }
        }

        LaunchedEffect(destination) {
          loginViewModel.onMessageConsumed()
          checkoutViewModel.onMessageConsumed()
          navController.navigate(destination) {
            popUpTo(navController.graph.id) {
              inclusive = true
            }
          }
        }

        Scaffold(
          modifier = Modifier.fillMaxSize(),
          topBar = {
            Column {
              TopAppBar(mode = uiState.mode)
              TabRow(
                selectedTabIndex = uiState.selectedTab.ordinal,
                onTabSelected = viewModel::onSelectedTab
              )
            }
          },
          floatingActionButton = {
            ToggleButton(mode = uiState.mode, onClick = viewModel::onToggleMode)
          }
        ) { innerPadding ->
          MainScreen(
            navController = navController,
            loginViewModel = loginViewModel,
            checkoutViewModel = checkoutViewModel,
            startDestination = "login_write",
            modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}
