package az.zero.animeaz.data.remote.util

sealed class ResponseState<T>(
    val data: T? = null,
    val placeholder: T? = null,
    val throwable: Throwable? = null,
) {
    class Loading<T>(placeholder: T? = null) : ResponseState<T>(placeholder = placeholder)
    class Success<T>(data: T) : ResponseState<T>(data = data)
    class Error<T>(throwable: Throwable, data: T? = null) :
        ResponseState<T>(data = data, throwable = throwable)

    class Empty<T> : ResponseState<T>()


    val ResponseState<*>.isLoading get() = this is ResponseState.Loading
    val ResponseState<*>.isSuccess get() = this is ResponseState.Success
    val ResponseState<*>.isError get() = this is ResponseState.Error<*>

    fun peek(): T? = this.data

}


//sealed class ResponseState<T> {
//    data class Loading<T>(val placeholder: T? = null) : ResponseState<T>
//    data class Success<T>(val data: T) : ResponseState<T>
//    data class Error<T>(val throwable: Throwable, val data: T? = null) : ResponseState<T>
//    class Empty<T> : ResponseState<T>
//
//
//    val isLoading: Boolean
//        get() = this is Loading<*>
//
//    // region Functional Programming
//    @Composable
//    fun onSuccess(action: @Composable (data: T) -> Unit): ResponseState<T> {
//        if (this is Success) {
//            action(this.data)
//        }
//        return this
//    }
//
//    @Composable
//    fun onFailure(action: @Composable (errorReason: Throwable, data: T?) -> Unit): ResponseState<T> {
//        if (this is Error) {
//            action(this.throwable, data)
//        }
//        return this
//    }
//
//    @Composable
//    fun onLoading(action: @Composable () -> Unit): ResponseState<T> {
//        if (this is Loading) {
//            action()
//        }
//        return this
//    }
//
//
//    @Composable
//    fun onAny(action: @Composable (ResponseState<T>) -> Unit): ResponseState<T> {
//        action(this)
//        return this
//    }
//    // endregion
//}