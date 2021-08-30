package com.samad_talukder.rokomariassessmenttest.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager (context: Context){
    private val PREFS_FILENAME = "rokomari_prefs"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var token: String?
        get() = sharedPreferences.getString("token", "")
        set(token) = sharedPreferences.edit().putString("token", token).apply()


}