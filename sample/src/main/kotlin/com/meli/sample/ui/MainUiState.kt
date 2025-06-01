package com.meli.sample.ui

enum class Source { SP, DT }

enum class Mode { WRITE, READ }

enum class TabSection { LOGIN, CHECKOUT }

data class MainUiState(
  val mode: Mode = Mode.WRITE,
  val selectedTab: TabSection = TabSection.LOGIN
)
