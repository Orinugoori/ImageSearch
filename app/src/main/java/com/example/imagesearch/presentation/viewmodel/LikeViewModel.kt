package com.example.imagesearch.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.imagesearch.data.repository.LikeRepository
import com.example.imagesearch.presentation.ListItem

class LikeViewModel(private val likeRepository: LikeRepository) : ViewModel() {
    private val _likes = MutableLiveData<List<ListItem>>()
    val likes: LiveData<List<ListItem>> get() = _likes

    init {
        loadLikes()
        Log.d("로그","${likeRepository.likedList}")
    }

    private fun loadLikes() {
        _likes.value = likeRepository.likedList
    }

    fun addLike(item: ListItem) {
        likeRepository.addLike(item)
        loadLikes()
    }

    fun removeLike(item: ListItem) {
        likeRepository.removeLike(item)
        loadLikes()
    }

    fun toggleAction(item: ListItem) {
        val target = _likes.value?.find { it.uid == item.uid }
        if (target != null) {
            removeLike(target)
        } else {
            addLike(item)
        }
    }
}
