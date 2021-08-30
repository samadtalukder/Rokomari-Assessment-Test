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
import com.samad_talukder.rokomariassessmenttest.model.response.BookModel
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.auth.SignInActivity
import com.samad_talukder.rokomariassessmenttest.ui.book_details.BookDetailsActivity
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import com.samad_talukder.rokomariassessmenttest.utils.ItemClickListener
import com.samad_talukder.rokomariassessmenttest.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ItemClickListener {
    private lateinit var preferenceManager: PreferenceManager
    private lateinit var newArrivalAdapter: NewArrivalAdapter
    private lateinit var exploreAdapter: ExploreAdapter
    private lateinit var rokomariViewModel: RokomariViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)
        preferenceManager = PreferenceManager(this)

        if (preferenceManager.token == "") {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

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
                    progressBar.visible(true)
                }
                is HandleResource.Success -> {
                    progressBar.visible(false)
                    response.data?.let { randomUserResponse ->
                        newArrivalAdapter.differ.submitList(randomUserResponse.models)
                    }
                }
                is HandleResource.Error -> {
                    progressBar.visible(false)
                    response.message?.let { errorResponse ->
                        Toast.makeText(this, errorResponse, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })

        rokomariViewModel.exploreBookResponse.observe(this, {
            when (it) {
                is HandleResource.Loading -> {
                    progressBar.visible(true)
                }
                is HandleResource.Success -> {
                    progressBar.visible(false)
                    it.data?.let { randomUserResponse ->
                        exploreAdapter.differ.submitList(randomUserResponse.models)
                    }
                }
                is HandleResource.Error -> {
                    progressBar.visible(false)
                    it.message?.let { errorResponse ->
                        Toast.makeText(this, errorResponse, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })

    }

    private fun setUpRecyclerView() {
        newArrivalAdapter = NewArrivalAdapter()
        exploreAdapter = ExploreAdapter(this)

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

    override fun onItemClick(position: BookModel) {
        val intent = Intent(this, BookDetailsActivity::class.java)
        intent.putExtra("book_id", position.id.toString())
        startActivity(intent)
    }

}