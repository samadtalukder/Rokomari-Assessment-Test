package com.samad_talukder.rokomariassessmenttest.ui.book_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.model.request.PurchaseBookRequest
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.GlideUtils
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import com.samad_talukder.rokomariassessmenttest.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookDetailsActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var rokomariViewModel: RokomariViewModel

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)
        preferenceManager = PreferenceManager(this)

        rokomariViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(RokomariViewModel::class.java)

        val bookId: String? = intent.getStringExtra("book_id")

        Log.e("ID", "$bookId")

        MainScope().launch {
            delay(500L)
            rokomariViewModel.myWallet(
                "Bearer " + preferenceManager.token.toString(),
            )

            rokomariViewModel.booksDetails(
                "Bearer " + preferenceManager.token.toString(),
                bookId.toString(),
            )

        }

        rokomariViewModel.bookDetailsResponse.observe(this, {
            when (it) {
                is HandleResource.Loading -> {

                }
                is HandleResource.Success -> {

                    it.data?.let { bookDetailsResponse ->
                        val bookDetailsData = bookDetailsResponse.model

                        tvBookName.text = bookDetailsData.name_en
                        tvAuthor.text = bookDetailsData.author_name_bn
                        tvDescription.text = bookDetailsData.summary
                        tvPrice.text = "Price: " + bookDetailsData.price.toString()
                        GlideUtils.showImage(this, bookDetailsData.image_path, ivBookImage)

                        if (bookDetailsResponse.is_purchased) {
                            btnPurchase.isEnabled = false
                            btnPurchase.background = getDrawable(R.drawable.bg_purchase_disable_button)
                        }

                        val purchaseBookRequest = PurchaseBookRequest(bookDetailsData.id)

                        btnPurchase.setOnClickListener {
                            rokomariViewModel.bookPurchase(
                                "Bearer " + preferenceManager.token.toString(),
                                purchaseBookRequest,
                            )

                            callPurchaseObserver()
                        }

                    }
                }
                is HandleResource.Error -> {

                    it.message?.let { response ->
                        ToastUtils.showToastMessage(this, response)
                    }
                }
            }
        })

        rokomariViewModel.myWalletResponse.observe(this, {
            when (it) {
                is HandleResource.Loading -> {

                }
                is HandleResource.Success -> {

                    it.data?.let { walletResponse ->
                        val bookDetailsData = walletResponse.model
                        tvBalance.text =
                            "Your Wallet Balance: " + bookDetailsData.balance.toString()

                    }
                }
                is HandleResource.Error -> {

                    it.message?.let { walletResponse ->
                        ToastUtils.showToastMessage(this, walletResponse)
                    }
                }
            }
        })


    }

    private fun callPurchaseObserver() {
        rokomariViewModel.bookPurchaseResponse.observe(this, {
            when (it) {
                is HandleResource.Loading -> {

                }

                is HandleResource.Success -> {
                    it.data?.let { walletResponse ->
                        val bookDetailsData = walletResponse.message
                        ToastUtils.showToastMessage(this, bookDetailsData)

                    }
                }

                is HandleResource.Error -> {

                    it.message?.let { walletResponse ->
                        Log.e("Error", walletResponse)

                    }
                }

            }
        })
    }
}