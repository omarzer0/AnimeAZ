package az.zero.animeaz.data.remote.util

import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

fun <Input, Output> networkCall(
    request: suspend () -> Input,
    showLoading: Boolean = true,
    mapper: (Input) -> Output
): Flow<ResponseState<Output>> = channelFlow {
    if (showLoading) trySend(ResponseState.Loading())
    try {
        val output = mapper(request.invoke())
        trySend(ResponseState.Success(output))
    } catch (e: ResponseException) {
        val throwable = Throwable("Code: ${e.response.status.value} message: ${e.message}")
        trySend(ResponseState.Error(throwable))
    } catch (e: Exception) {
        val throwable = Throwable("message: ${e.message}")
        trySend(ResponseState.Error(throwable))
    }
}
