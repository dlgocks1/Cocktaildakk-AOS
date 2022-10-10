package com.compose.cocktaildakk_compose.domain.model

sealed class NetworkState {
  object None : NetworkState()
  object Connected : NetworkState()
  object NotConnected : NetworkState()
}
