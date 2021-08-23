package com.samad_talukder.rokomariassessmenttest.repository

import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.network.ApiInstance

class RokomariRepository {

    suspend fun signUpRequest(signUpRequest: SignUpRequest)
    = ApiInstance.api.signUpRequest(signUpRequest)

    suspend fun signInRequest(signInRequest: SignInRequest)
    = ApiInstance.api.sigInRequest(signInRequest)


}