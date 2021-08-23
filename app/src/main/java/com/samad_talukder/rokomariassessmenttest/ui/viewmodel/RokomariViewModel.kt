package com.samad_talukder.rokomariassessmenttest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.model.response.SignUpResponse
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.coroutines.*
import retrofit2.Response

class RokomariViewModel(val rokomariRepository: RokomariRepository) : ViewModel() {
    var job: Job? = null
    val signUpStatus: MutableLiveData<HandleResource<SignUpResponse>> = MutableLiveData()


    fun signUp(signUpRequest: SignUpRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val signUpResponse = rokomariRepository.signUpRequest(signUpRequest)

            withContext(Dispatchers.Main) {
                signUpStatus.postValue(handleResponse(signUpResponse))
            }

        }

    }

    private fun handleResponse(signUpResponse: Response<SignUpResponse>): HandleResource<SignUpResponse>? {

        if (signUpResponse.isSuccessful) {

            signUpResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(signUpResponse.message())
    }
}