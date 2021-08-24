package com.samad_talukder.rokomariassessmenttest.model.response

data class PurchaseBookListResponse(
    val models: List<PurchaseBookList>,
    val total_count: Int
)