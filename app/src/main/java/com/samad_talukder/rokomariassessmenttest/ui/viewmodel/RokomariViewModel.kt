package com.samad_talukder.rokomariassessmenttest.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.samad_talukder.rokomariassessmenttest.model.request.PurchaseBookRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.model.response.*
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Response


class RokomariViewModel(val rokomariRepository: RokomariRepository) : ViewModel() {
    var job: Job? = null

    val signUpStatus: MutableLiveData<HandleResource<SignUpResponse>> = MutableLiveData()

    val signInStatus: MutableLiveData<HandleResource<SignInResponse>> = MutableLiveData()

    val newArrivalResponse: MutableLiveData<HandleResource<AllBookListResponse>> = MutableLiveData()

    val exploreBookResponse: MutableLiveData<HandleResource<AllBookListResponse>> =
        MutableLiveData()

    val bookDetailsResponse: MutableLiveData<HandleResource<BookDetailsResponse>> =
        MutableLiveData()

    val bookPurchaseResponse: MutableLiveData<HandleResource<PurchaseResponse>> =
        MutableLiveData()

    val purchaseUserBookListResponse: MutableLiveData<HandleResource<PurchaseBookListResponse>> =
        MutableLiveData()

    val myWalletResponse: MutableLiveData<HandleResource<MyWalletResponse>> =
        MutableLiveData()

    val purchaseErrorResponse: MutableLiveData<HandleResource<ErrorResponse>> = MutableLiveData()


    fun signUp(signUpRequest: SignUpRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val signUpResponse = rokomariRepository.signUpRequest(signUpRequest)

            withContext(Dispatchers.Main) {
                signUpStatus.postValue(handleSignUpResponse(signUpResponse))
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

            val newArrivalBooksResponse =
                rokomariRepository.allBookRequest(token, pageNo, limit, isNewArrival)

            withContext(Dispatchers.Main) {
                when (isNewArrival) {
                    "True" -> {
                        newArrivalResponse.postValue(handleHomeBooksResponse(newArrivalBooksResponse))
                    }
                    "False" -> {
                        exploreBookResponse.postValue(handleHomeBooksResponse(newArrivalBooksResponse))
                    }
                }
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

    fun bookPurchase(token: String, purchaseBookRequest: PurchaseBookRequest) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val bookPurchaseData = rokomariRepository.bookPurchase(token, purchaseBookRequest)

            withContext(Dispatchers.Main) {
                bookPurchaseResponse.postValue(handleBookPurchaseResponse(bookPurchaseData))
            }

        }

    }

    fun bookPurchaseByUser(token: String) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val bookPurchaseByUserData = rokomariRepository.purchaseUserBookList(token)

            withContext(Dispatchers.Main) {
                purchaseUserBookListResponse.postValue(
                    handlePurchaseUserBookListResponse(
                        bookPurchaseByUserData
                    )
                )
            }

        }

    }

    fun myWallet(token: String) {
        job = CoroutineScope(Dispatchers.IO).launch {

            val bookMyWalletData = rokomariRepository.myWallet(token)

            withContext(Dispatchers.Main) {
                myWalletResponse.postValue(handleMyWalletResponse(bookMyWalletData))
            }

        }

    }


    private fun getPurchaseErrorMessage(rawResponse: String,jObject: String): String {
        val jsonObject = JSONObject(rawResponse)
        return jsonObject.getString(jObject)
    }

    private fun handleSignUpResponse(signUpResponse: Response<SignUpResponse>): HandleResource<SignUpResponse> {

        if (signUpResponse.isSuccessful) {

            signUpResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }

        return HandleResource.Error(signUpResponse.message())
    }

    private fun handleSignInResponse(signInResponse: Response<SignInResponse>): HandleResource<SignInResponse> {
        if (signInResponse.code() == 200) {
            signInResponse.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }
        }
        return HandleResource.Error(getPurchaseErrorMessage(signInResponse.errorBody()?.string().toString(),"detail"))

    }

    private fun handleHomeBooksResponse(newArrivalBooksResponse: Response<AllBookListResponse>): HandleResource<AllBookListResponse> {
        if (newArrivalBooksResponse.isSuccessful) {

            newArrivalBooksResponse.body()?.let { booksResponseData ->
                return HandleResource.Success(booksResponseData)
            }

        }

        return HandleResource.Error(getPurchaseErrorMessage(newArrivalBooksResponse.errorBody()?.string().toString(),"detail"))
    }

    private fun handleBooksDetailsResponse(bookDetailsResponseData: Response<BookDetailsResponse>): HandleResource<BookDetailsResponse> {
        if (bookDetailsResponseData.isSuccessful) {

            bookDetailsResponseData.body()?.let { signUpResponseData ->
                return HandleResource.Success(signUpResponseData)
            }

        }
        return HandleResource.Error(bookDetailsResponseData.message())
    }

    private fun handleMyWalletResponse(bookMyWalletResponse: Response<MyWalletResponse>): HandleResource<MyWalletResponse> {
        if (bookMyWalletResponse.isSuccessful) {

            bookMyWalletResponse.body()?.let { bookMyWalletData ->
                return HandleResource.Success(bookMyWalletData)
            }

        }

        return HandleResource.Error(bookMyWalletResponse.message())
    }

    private fun handlePurchaseUserBookListResponse(bookPurchaseByUserData: Response<PurchaseBookListResponse>): HandleResource<PurchaseBookListResponse>? {
        if (bookPurchaseByUserData.isSuccessful) {

            bookPurchaseByUserData.body()?.let { bookPurchaseResponseData ->
                return HandleResource.Success(bookPurchaseResponseData)
            }

        }

        return HandleResource.Error(bookPurchaseByUserData.message())
    }

    private fun handleBookPurchaseResponse(bookPurchaseData: Response<PurchaseResponse>): HandleResource<PurchaseResponse> {
        if (bookPurchaseData.code() == 200) {

            bookPurchaseData.body()?.let { bookPurchaseResponseData ->
                return HandleResource.Success(bookPurchaseResponseData)
            }

        }

        return HandleResource.Error(getPurchaseErrorMessage(bookPurchaseData.errorBody()?.string().toString(),"error"))
    }


}

