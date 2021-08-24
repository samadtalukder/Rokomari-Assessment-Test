package com.samad_talukder.rokomariassessmenttest.network

import com.samad_talukder.rokomariassessmenttest.model.request.PurchaseBookRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.model.response.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST(ApiUrl.API_SIGN_UP)
    suspend fun callSignUpApi(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>

    @Headers("Content-Type:application/json")
    @POST(ApiUrl.API_LOG_IN)
    suspend fun callSigInApi(
        @Body loginRequest: SignInRequest
    ): Response<SignInResponse>

    @Headers("Content-Type: application/json")
    @GET(ApiUrl.API_BOOK_LIST)
    suspend fun callNewArrivalBookApi(
        @Header("Authorization") token: String,
        @Query("page") pageNo: Int,
        @Query("limit") limit: Int,
        @Query("new_arrival") arrivalType: String,
    ): Response<AllBookListResponse>

    @Headers("Content-Type: application/json")
    @GET("books/{bookId}")
    suspend fun callBookDetailsApi(
        @Header("Authorization") token: String,
        @Path("bookId") bookId: String,
        ): Response<BookDetailsResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiUrl.API_PURCHASE)
    suspend fun callPurchaseApi(
        @Header("Authorization") token: String,
        @Body purchaseBookRequest: PurchaseBookRequest,
        ): Response<PurchaseResponse>

    @Headers("Content-Type: application/json")
    @GET(ApiUrl.API_PURCHASE)
    suspend fun callPurchaseUserBookListApi(
        @Header("Authorization") token: String,
        ): Response<PurchaseBookListResponse>

    @Headers("Content-Type: application/json")
    @GET(ApiUrl.API_MY_WALLET)
    suspend fun callWalletApi(
        @Header("Authorization") token: String,
        ): Response<MyWalletResponse>


}