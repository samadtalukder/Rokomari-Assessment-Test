package com.samad_talukder.rokomariassessmenttest.utils

import com.samad_talukder.rokomariassessmenttest.model.response.BookModel

interface ItemClickListener {
    fun onItemClick(position: BookModel)
}