package com.samad_talukder.rokomariassessmenttest.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.samad_talukder.rokomariassessmenttest.repository.RokomariRepository


class ViewModelProviderFactory(private val rokomariRepository: RokomariRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RokomariViewModel(rokomariRepository) as T
    }


}