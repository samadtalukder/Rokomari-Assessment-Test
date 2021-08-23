package com.samad_talukder.rokomariassessmenttest.model.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String
)