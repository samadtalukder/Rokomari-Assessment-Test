package com.samad_talukder.rokomariassessmenttest.ui.book_details

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookDetailsActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    lateinit var rokomariViewModel: RokomariViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)
        preferenceManager = PreferenceManager(this)

        rokomariViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(RokomariViewModel::class.java)

        val bookId = intent.getStringExtra("id")

        Log.e("ID","$bookId")

        MainScope().launch {
            delay(500L)
            rokomariViewModel.booksDetails(
                "Bearer " + preferenceManager.token.toString(),
                bookId.toString(),
            )
        }

        rokomariViewModel.newArrivalResponse.observe(this, { response ->
            when (response) {
                is HandleResource.Loading -> {

                }
                is HandleResource.Success -> {

                    response.data?.let { bookDetailsResponse ->
                        textViewBookTitle.text = bookDetailsResponse.models[0].summary
                    }
                }
                is HandleResource.Error -> {

                    response.message?.let { response -> Log.e("Error", response) }
                }
            }
        })
    }
}