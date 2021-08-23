package com.samad_talukder.rokomariassessmenttest.utils

sealed class HandleResource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : HandleResource<T>()
    class Success<T>(data: T?) : HandleResource<T>(data)
    class Error<T>(message: String?, data: T? = null) : HandleResource<T>(data, message)
}
