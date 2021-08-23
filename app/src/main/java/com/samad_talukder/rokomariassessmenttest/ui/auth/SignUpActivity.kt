package com.samad_talukder.rokomariassessmenttest.ui.auth

import android.content.Intent
import android.content.Intent.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.model.request.SignUpRequest
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {
    lateinit var rokomariViewModel: RokomariViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)

        rokomariViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(RokomariViewModel::class.java)

        buttonSignUp.setOnClickListener {
            signUp()
        }

        textViewSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signUp() {
        if (editTextUserName.text.toString().isEmpty()) {
            editTextUserName.error = "Enter user name"
            editTextUserName.requestFocus()
            return
        }
        if (editTextUserEmail.text.toString().isEmpty()) {
            editTextUserEmail.error = "Enter Email"
            editTextUserEmail.requestFocus()
            return
        }
        if (editTextUserPassword.text.toString().isEmpty()) {
            editTextUserPassword.error = "Enter Password"
            editTextUserPassword.requestFocus()
            return
        }

        val userName = editTextUserName.text.toString()
        val userEmail = editTextUserEmail.text.toString()
        val userPassword = editTextUserPassword.text.toString()

        val signUpRequest = SignUpRequest(userEmail, userPassword, userName)

        MainScope().launch {
            delay(500L)
            rokomariViewModel.signUp(signUpRequest)
        }

        callObserver()
    }

    private fun callObserver() {
        rokomariViewModel.signUpStatus.observe(this, { response ->
            when (response) {
                is HandleResource.Loading -> {
                    showProgressBar()
                }
                is HandleResource.Success -> {
                    hideProgressBar()
                    response.data?.let { signUpResponse ->

                        Toast.makeText(this, signUpResponse.success, Toast.LENGTH_SHORT).show()
                        goToHomeActivity()
                    }
                }
                is HandleResource.Error -> {
                    hideProgressBar()
                    response.message?.let { signUpErrorResponse ->
                        Toast.makeText(this, signUpErrorResponse, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        })
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}