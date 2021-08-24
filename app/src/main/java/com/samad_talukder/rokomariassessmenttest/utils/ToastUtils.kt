package com.samad_talukder.rokomariassessmenttest.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {
    fun showToastMessage(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}