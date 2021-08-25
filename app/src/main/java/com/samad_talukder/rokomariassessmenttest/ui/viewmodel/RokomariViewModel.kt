package com.samad_talukder.rokomariassessmenttest.ui.viewmodel

import android.R.raw
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
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

            val newArrivalBooksResponse =
                rokomariRepository.allBookRequest(token, pageNo, limit, isNewArrival)

            withContext(Dispatchers.Main) {
                when (isNewArrival) {
                    "True" -> {
                        newArrivalResponse.postValue(handleBooksResponse(newArrivalBooksResponse))
                    }
                    "False" -> {
                        exploreBookResponse.postValue(handleBooksResponse(newArrivalBooksResponse))
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

    private fun handleMyWalletResponse(bookMyWalletResponse: Response<MyWalletResponse>): HandleResource<MyWalletResponse>? {
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


    private fun handleBookPurchaseResponse(bookPurchaseData: Response<PurchaseResponse>): HandleResource<PurchaseResponse>? {
        bookPurchaseData.body()?.let { myDataData ->
            if (bookPurchaseData.code() == 200) {
                return HandleResource.Success(myDataData)
            } else if (bookPurchaseData.code() == 403) {
                val rawResponse = myDataData.errorResponse.error
                Log.e("ErrorRes", rawResponse)
                return HandleResource.Failed(getErrorMessage(rawResponse))

            }

        }
        return HandleResource.Failed(bookPurchaseData.body()?.errorResponse?.error)
        /*if (bookPurchaseData.code() == 200) {

                bookPurchaseData.body()?.let { bookPurchaseResponseData ->
                    return HandleResource.Success(bookPurchaseResponseData)
                }

            } else {
                Log.e("errorBody", "${bookPurchaseData}")

            }

            return HandleResource.Error(bookPurchaseData.errorBody().toString())*/
    }

    private fun getErrorMessage(rawResponse: String): String {
        val jsonObject = JSONObject(rawResponse)
        return jsonObject.getString("error")
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

            newArrivalBooksResponse.body()?.let { booksResponseData ->
                return HandleResource.Success(booksResponseData)
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

