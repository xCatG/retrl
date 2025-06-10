package com.cattailsw.retrl.core.domain.util // Updated package

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
    // object Loading : Result<Nothing>() // Optional: If you want to represent a loading state

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$exception]"
            // is Loading -> "Loading"
        }
    }
}

/**
 * `true` if [Result] is of type [Success] & holds non-null [Success.data].
 */
val Result<*>.succeeded
    get() = this is Result.Success && data != null

fun <T> Result<T>.successOr(fallback: T): T {
    return (this as? Result.Success<T>)?.data ?: fallback
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

/**
 * Updates value of [liveData] if [Result] is of type [Success]
 */
// inline fun <T> Result<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
//    if (this is Success) {
//        liveData.value = data
//    }
// }
