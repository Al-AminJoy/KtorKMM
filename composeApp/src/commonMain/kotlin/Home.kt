import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

import networking.NetworkClient
import org.jetbrains.compose.ui.tooling.preview.Preview

data class Home(val networkClient: NetworkClient) : Screen{

    @Composable
    @Preview
    override fun Content() {

        val homeViewModel = rememberScreenModel { HomeViewModel(networkClient) }

        homeViewModel.postList.value.DisplayResult(onIdle = {
            Text("Idle")
        }, onLoading = {
            Text("Loading")
        }, onSuccess = {
            Text("Success $it")
        }, onError = {
            Text("Error $it")
        }
        )
    }
}