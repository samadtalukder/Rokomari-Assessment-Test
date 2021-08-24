package com.samad_talukder.rokomariassessmenttest.repository

import com.samad_talukder.rokomariassessmenttest.model.request.PurchaseBookRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.network.ApiInstance

class RokomariRepository {

    suspend fun signUpRequest(signUpRequest: SignUpRequest) =
        ApiInstance.api.callSignUpApi(signUpRequest)

    suspend fun signInRequest(signInRequest: SignInRequest) =
        ApiInstance.api.callSigInApi(signInRequest)

    suspend fun allBookRequest(token: String, pageNo: Int, limit: Int, isNewArrival: String) =
        ApiInstance.api.callNewArrivalBookApi(token, pageNo, limit, isNewArrival)

    suspend fun bookDetails(token: String, bookId: String) =
        ApiInstance.api.callBookDetailsApi(token, bookId)

    suspend fun bookPurchase(token: String, purchaseBookRequest: PurchaseBookRequest) =
        ApiInstance.api.callPurchaseApi(token, purchaseBookRequest)

    suspend fun bookPurchaseByUser(token: String) =
        ApiInstance.api.callPurchaseByUserBookApi(token)

    suspend fun myWallet(token: String) =
        ApiInstance.api.callPurchaseByUserBookApi(token)


}