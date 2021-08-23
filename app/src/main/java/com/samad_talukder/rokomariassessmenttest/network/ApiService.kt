package com.samad_talukder.rokomariassessmenttest.network

import com.samad_talukder.rokomariassessmenttest.model.request.LoginRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.model.response.AllBookListResponse
import com.samad_talukder.rokomariassessmenttest.model.response.LoginResponse
import com.samad_talukder.rokomariassessmenttest.model.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type:application/json")
    @POST(ApiUrl.API_SIGN_UP)
    suspend fun signUpRequest(
        @Body signUpRequest: SignUpRequest
    ): Response<SignUpResponse>

    @Headers("Content-Type:application/json")
    @POST(ApiUrl.API_LOG_IN)
    suspend fun loginRequest(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @Headers("Content-Type: application/json")
    @GET(ApiUrl.API_BOOK_LIST)
    suspend fun getNewArrivalBook(
        @Header("Authorization") token: String,
        @Query("page") pageNo: Int,
        @Query("limit") limit: Int,
        @Query("new_arrival") arrivalType: Boolean,
    ): Response<AllBookListResponse>

    @Headers("Content-Type: application/json")
    @GET("books/{bookId}")
    suspend fun getBookDetails(
        @Header("Authorization") token: String,
        @Path("bookId") bookId: String,
        ): Response<AllBookListResponse>


}