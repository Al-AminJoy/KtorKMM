import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import networking.NetworkClient
import networking.Post
import networking.RequestState

class HomeViewModel(val networkClient: NetworkClient): ScreenModel {

    private val postdata = networkClient.stateData

    val postList : MutableState<RequestState<List<Post>>> = mutableStateOf(RequestState.Idle)

    init {
        screenModelScope.launch(Dispatchers.IO) {
            networkClient.getPostList()
        }
        screenModelScope.launch {
            postdata.collectLatest {
                postList.value = it
            }
        }
    }
}