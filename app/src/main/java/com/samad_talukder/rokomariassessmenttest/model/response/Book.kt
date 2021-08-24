package com.samad_talukder.rokomariassessmenttest.model.response

data class Book(
    val author_name_bn: String,
    val id: Int,
    val image_path: String,
    val is_available: Boolean,
    val is_newarrival: Boolean,
    val is_unavailable: Boolean,
    val lang: String,
    val name_bn: String,
    val name_en: String,
    val position: Int,
    val price: Int,
    val publisher_name_bn: String,
    val summary: String
)