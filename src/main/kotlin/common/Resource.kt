package common

sealed class Resource<in T>() {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(val message: String, val data: T? = null) : Resource<T>()
    data object Loading: Resource<Any>()
}