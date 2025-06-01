package com.meli.sample.ui

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.meli.sample.frontends.checkout.CheckoutViewModel
import com.meli.sample.frontends.checkout.screens.CheckoutReadScreen
import com.meli.sample.frontends.checkout.screens.CheckoutWriteScreen
import com.meli.sample.frontends.login.LoginViewModel
import com.meli.sample.frontends.login.screens.LoginReadScreen
import com.meli.sample.frontends.login.screens.LoginWriteScreen

@Composable
fun MainScreen(
  navController: NavHostController,
  loginViewModel: LoginViewModel,
  checkoutViewModel: CheckoutViewModel,
  startDestination: String,
  modifier: Modifier = Modifier
) {
  NavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier,
    enterTransition = { EnterTransition.None },
    exitTransition = { ExitTransition.None },
    popEnterTransition = { EnterTransition.None },
    popExitTransition = { ExitTransition.None }
  ) {
    composable("login_write") { LoginWriteScreen(loginViewModel) }
    composable("login_read") { LoginReadScreen(loginViewModel) }
    composable("checkout_write") { CheckoutWriteScreen(checkoutViewModel) }
    composable("checkout_read") { CheckoutReadScreen(checkoutViewModel) }
  }
}
