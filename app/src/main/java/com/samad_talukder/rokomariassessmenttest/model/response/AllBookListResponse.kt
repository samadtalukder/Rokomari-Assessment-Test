package com.samad_talukder.rokomariassessmenttest.model.response

data class AllBookListResponse(
    val models: List<BookModel>,
    val total_count: Int
)