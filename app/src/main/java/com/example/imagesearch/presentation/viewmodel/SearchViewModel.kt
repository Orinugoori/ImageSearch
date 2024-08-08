package com.example.imagesearch.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.repository.SearchRepositoryImpl
import com.example.imagesearch.presentation.ListItem
import com.example.imagesearch.presentation.sortedByDatetime
import com.example.imagesearch.presentation.toImageItem
import com.example.imagesearch.presentation.toVideoItem
import kotlinx.coroutines.launch

class SearchViewModel(private val repository : SearchRepositoryImpl) : ViewModel() {
    private val _searchResults = MutableLiveData<List<ListItem>>()
    val searchResults: LiveData<List<ListItem>> get() = _searchResults


    fun searchImages(query: String, page: Int = 1) {
        viewModelScope.launch {
            runCatching {
                val imageResponse = repository.getImageList(query, page)
                val videoResponse = repository.getVideoList(query, page)

                val imageResult = imageResponse.documents?.let{toImageItem(it)} ?: emptyList()
                val videoResult = videoResponse.documents?.let{toVideoItem(it)} ?: emptyList()

                _searchResults.value = sortedByDatetime(imageResult.plus(videoResult))
            }.onFailure {
                Log.d("메롱","실패했지롱 : ${it.message}")
            }
        }
    }


}