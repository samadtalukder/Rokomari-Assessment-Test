package com.samad_talukder.rokomariassessmenttest.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.adapter.ExploreAdapter
import com.samad_talukder.rokomariassessmenttest.adapter.NewArrivalAdapter
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.book_details.BookDetailsActivity
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager
    private lateinit var newArrivalAdapter: NewArrivalAdapter
    private lateinit var exploreAdapter: ExploreAdapter
    lateinit var rokomariViewModel: RokomariViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)
        preferenceManager = PreferenceManager(this)



        rokomariViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(RokomariViewModel::class.java)

        setUpRecyclerView()



        MainScope().launch {
            delay(500L)
            rokomariViewModel.newArrivalBooks(
                "Bearer " + preferenceManager.token.toString(),
                1,
                10,
                "True"
            )

            rokomariViewModel.newArrivalBooks(
                "Bearer " + preferenceManager.token.toString(),
                1,
                10,
                "False"
            )
        }

        rokomariViewModel.newArrivalResponse.observe(this, { response ->
            when (response) {
                is HandleResource.Loading -> {

                }
                is HandleResource.Success -> {

                    response.data?.let { randomUserResponse ->
                        newArrivalAdapter.differ.submitList(randomUserResponse.models)
                    }
                }
                is HandleResource.Error -> {

                    response.message?.let { response -> Log.e("Error", response) }
                }
            }
        })

        rokomariViewModel.exploreBookResponse.observe(this, { response ->
            when (response) {
                is HandleResource.Loading -> {

                }
                is HandleResource.Success -> {

                    response.data?.let { randomUserResponse ->
                        exploreAdapter.differ.submitList(randomUserResponse.models)
                    }
                }
                is HandleResource.Error -> {

                    response.message?.let { response -> Log.e("Error", response) }
                }
            }
        })

    }

    private fun setUpRecyclerView() {
        newArrivalAdapter = NewArrivalAdapter()
        exploreAdapter = ExploreAdapter()

        rvNewArrival.apply {
            rvNewArrival.adapter = newArrivalAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)

        }

        newArrivalAdapter.setOnClickListener {
            val intent = Intent(this, BookDetailsActivity::class.java)
            intent.putExtra("book_id", it.id.toString())
            startActivity(intent)
        }

        rvExplorer.apply {
            rvExplorer.adapter = exploreAdapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)

        }

    }

}