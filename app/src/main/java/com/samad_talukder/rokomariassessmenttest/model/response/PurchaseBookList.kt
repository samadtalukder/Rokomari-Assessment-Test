package com.samad_talukder.rokomariassessmenttest.model.response

data class PurchaseBookList(
    val book: Book,
    val id: Int,
    val is_purchased: Boolean
)