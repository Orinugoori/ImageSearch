package com.example.imagesearch.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.repository.SearchRepositoryImpl

class SearchViewModelFactory(private val repository: SearchRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }
        throw IllegalStateException("Unknown ViewModel class")
    }
}