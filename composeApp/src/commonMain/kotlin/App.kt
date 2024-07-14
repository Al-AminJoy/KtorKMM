import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

import cafe.adriel.voyager.navigator.Navigator
import networking.NetworkClient


import org.jetbrains.compose.ui.tooling.preview.Preview

private const val TAG = "AppNetwork"

@Composable
@Preview
fun App(networkClient:NetworkClient) {
    MaterialTheme {
        Navigator(Home(networkClient))
    }
}
