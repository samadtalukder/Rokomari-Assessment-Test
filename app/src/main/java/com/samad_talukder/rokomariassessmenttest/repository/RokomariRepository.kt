package com.samad_talukder.rokomariassessmenttest.repository

import com.samad_talukder.rokomariassessmenttest.model.request.PurchaseBookRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.network.ApiInstance

class RokomariRepository {

    suspend fun signUpRequest(signUpRequest: SignUpRequest) =
        ApiInstance.rokomariApi.callSignUpApi(signUpRequest)

    suspend fun signInRequest(signInRequest: SignInRequest) =
        ApiInstance.rokomariApi.callSigInApi(signInRequest)

    suspend fun allBookRequest(token: String, pageNo: Int, limit: Int, isNewArrival: String) =
        ApiInstance.rokomariApi.callNewArrivalBookApi(token, pageNo, limit, isNewArrival)

    suspend fun bookDetails(token: String, bookId: String) =
        ApiInstance.rokomariApi.callBookDetailsApi(token, bookId)

    suspend fun bookPurchase(token: String, purchaseBookRequest: PurchaseBookRequest) =
        ApiInstance.rokomariApi.callPurchaseApi(token, purchaseBookRequest)

    suspend fun purchaseUserBookList(token: String) =
        ApiInstance.rokomariApi.callPurchaseUserBookListApi(token)

    suspend fun myWallet(token: String) =
        ApiInstance.rokomariApi.callWalletApi(token)


}