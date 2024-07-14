package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.SerializationException

class NetworkClient(private val httpClient: HttpClient) {

    private val mutableStateData  = MutableStateFlow<RequestState<List<Post>>>(RequestState.Idle)
    val stateData = mutableStateData.asStateFlow()

    suspend fun getPostList() {

        try {
            mutableStateData.value = RequestState.Loading

            val response = httpClient.get("https://jsonplaceholder.typicode.com/posts")
            when (response.status.value) {
                in 200 .. 299 -> {
                    val postList = response.body<List<Post>>()
                    mutableStateData.value = RequestState.Success(data = postList)
                }

                401 -> {
                    mutableStateData.value = RequestState.Error("Unauthorized")
                }
                400 -> {
                    mutableStateData.value = RequestState.Error("Bad Request")
                }
                404 -> {
                    mutableStateData.value = RequestState.Error("No Data Found")
                }
                408 -> {
                    mutableStateData.value = RequestState.Error("Request Timeout")
                }
                409 -> {
                    mutableStateData.value = RequestState.Error("Conflict")
                }
                413 -> {
                    mutableStateData.value = RequestState.Error("Payload Too Large")
                }
                in 500..599 -> {
                    mutableStateData.value = RequestState.Error("Internal Server Error")
                }

                else -> {
                    mutableStateData.value = RequestState.Error("Unknown Error")
                }
            }
        } catch (e: UnresolvedAddressException) {
            mutableStateData.value = RequestState.Error("No Internet or server Down")
        } catch (e: SerializationException) {
            mutableStateData.value =  RequestState.Error("Serialization exception")
        }


    }

}