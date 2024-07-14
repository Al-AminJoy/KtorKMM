import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import io.ktor.client.engine.darwin.Darwin
import networking.NetworkClient
import networking.createHttpClient

fun MainViewController() = ComposeUIViewController { App(networkClient =  remember {
    NetworkClient(createHttpClient(Darwin.create()))
}) }