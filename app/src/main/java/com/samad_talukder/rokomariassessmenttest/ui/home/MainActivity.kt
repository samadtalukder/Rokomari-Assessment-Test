package com.samad_talukder.rokomariassessmenttest.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.samad_talukder.rokomariassessmenttest.R
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager

class MainActivity : AppCompatActivity() {
    lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        preferenceManager = PreferenceManager(this)


    }
}