package com.samad_talukder.rokomariassessmenttest.app

import android.app.Application
import com.samad_talukder.rokomariassessmenttest.preferences.PreferenceManager

class RokomariApp : Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager(this)
    }
}