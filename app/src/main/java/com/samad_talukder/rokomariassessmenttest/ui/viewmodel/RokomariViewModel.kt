package com.samad_talukder.rokomariassessmenttest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.model.response.AllBookListResponse
import com.samad_talukder.rokomariassessmenttest.model.response.BookDetailsResponse
import com.samad_talukder.rokomariassessmenttest.model.response.SignInResponse
import com.samad_talukder.rokomariassessmenttest.model.response.SignUpResponse
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.coroutines.*
import retrofit2.Response

class RokomariViewModel(val rokomariRepository: RokomariRepository) : ViewModel() {
    var job: Job? = null
    val signUpStatus: MutableLiveData<HandleResource<SignUpResponse>> = MutableLiveData()
    val signInStatus: MutableLiveData<HandleResource<SignInResponse>> = MutableLiveData()
    val newArrivalResponse: MutableLiveData<HandleResource<AllBookListResponse>> = MutableLiveData()
    val bookDetailsResponse: MutableLiveData<HandleResource<BookDetailsResponse>> = MutableLiveData()


    fun signUp(signUpRequest: SignUpRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val signUpResponse = rokomariRepository.signUpRequest(signUpRequest)

            withContext(Dispatchers.Main) {
                signUpStatus.postValue(handleResponse(signUpResponse))
            }

        }

    }

    fun signIn(signInRequest: SignInRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val signInResponse = rokomariRepository.signInRequest(signInRequest)

            withContext(Dispatchers.Main) {
                signInStatus.postValue(handleSignInResponse(signInResponse))
            }

        }

    }

    fun newArrivalBooks(token: String, pageNo: Int, limit: Int, isNewArrival: String) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val newArrivalBooksResponse = rokomariRepository.allBookRequest(token, pageNo, limit, isNewArrival)

            withContext(Dispatchers.Main) {
                newArrivalResponse.postValue(handleBooksResponse(newArrivalBooksResponse))
            }

        }

    }

    fun booksDetails(token: String, bookId: String) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val bookDetailsData = rokomariRepository.bookDetails(token, bookId)

            withContext(Dispatchers.Main) {
                bookDetailsResponse.postValue(handleBooksDetailsResponse(bookDetailsData))
            }

        }

    }

    private fun handleBooksDetailsResponse(bookDetailsResponseData: Response<BookDetailsResponse>): HandleResource<BookDetailsResponse>? {
        if (bookDetailsResponseData.isSuccessful) {

            bookDetailsResponseData.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(bookDetailsResponseData.message())
    }

    private fun handleBooksResponse(newArrivalBooksResponse: Response<AllBookListResponse>): HandleResource<AllBookListResponse>? {
        if (newArrivalBooksResponse.isSuccessful) {

            newArrivalBooksResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(newArrivalBooksResponse.message())
    }

    private fun handleResponse(signUpResponse: Response<SignUpResponse>): HandleResource<SignUpResponse>? {

        if (signUpResponse.isSuccessful) {

            signUpResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(signUpResponse.message())
    }

    private fun handleSignInResponse(signInResponse: Response<SignInResponse>): HandleResource<SignInResponse>? {
        if (signInResponse.isSuccessful) {

            signInResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(signInResponse.message())
    }
}

