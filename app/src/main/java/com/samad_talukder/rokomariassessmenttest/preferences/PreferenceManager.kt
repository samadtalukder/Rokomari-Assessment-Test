package com.samad_talukder.rokomariassessmenttest.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager (context: Context){
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("rokomari_pref",Context.MODE_PRIVATE)


    var token: String?
        get() = sharedPreferences.getString("token", "")
        set(token) = sharedPreferences.edit().putString("token", token).apply()
}