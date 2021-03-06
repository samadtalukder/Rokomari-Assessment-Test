package com.samad_talukder.rokomariassessmenttest.ui.auth

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.model.request.SignInRequest
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository
import com.samad_talukder.rokomariassessmenttest.ui.home.MainActivity
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.RokomariViewModel
import com.samad_talukder.rokomariassessmenttest.ui.viewmodel.ViewModelProviderFactory
import com.samad_talukder.rokomariassessmenttest.utils.HandleResource
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SignInActivity : AppCompatActivity() {
    lateinit var rokomariViewModel: RokomariViewModel
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val randomUserRepository = RokomariRepository()
        val viewModelProviderFactory = ViewModelProviderFactory(randomUserRepository)
        preferenceManager = PreferenceManager(this)



        rokomariViewModel =
            ViewModelProvider(this, viewModelProviderFactory)
                .get(RokomariViewModel::class.java)

        buttonSignIn.setOnClickListener {
            signInApiCall()
        }

        textViewSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInApiCall() {

        val userName = editTextLoginUserName.text.toString()
        val userPassword = editTextLoginUserPassword.text.toString()

        if (TextUtils.isEmpty(userName)) {
            editTextLoginUserName.error = "Enter User Name"
            editTextLoginUserName.requestFocus()
            return
        }
        if (TextUtils.isEmpty(userPassword)) {
            editTextLoginUserPassword.error = "Enter Password"
            editTextLoginUserPassword.requestFocus()
            return
        }

        val signInRequest = SignInRequest(userPassword, userName)

        MainScope().launch {
            delay(500L)
            rokomariViewModel.signIn(signInRequest)
        }

        callObserver()

    }

    private fun callObserver() {
        rokomariViewModel.signInStatus.observe(this, { response ->
            when (response) {
                is HandleResource.Loading -> {
                    showProgressBar()
                }
                is HandleResource.Success -> {
                    hideProgressBar()
                    response.data?.let { signInResponse ->
                        preferenceManager.token = signInResponse.access
                        goToHomeActivity()
                    }
                }
                is HandleResource.Error -> {
                    hideProgressBar()
                    response.message?.let { signUpErrorResponse ->
                        Toast.makeText(this, signUpErrorResponse, Toast.LENGTH_SHORT).show()

                    }
                }
                else -> {

                }
            }
        })
    }

    private fun goToHomeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar() {
        progressDialog.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressDialog.visibility = View.GONE
    }
}