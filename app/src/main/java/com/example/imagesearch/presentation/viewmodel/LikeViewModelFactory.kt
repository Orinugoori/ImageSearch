package com.example.imagesearch.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.repository.LikeRepository

class LikeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LikeViewModel::class.java) -> LikeViewModel(
                LikeRepository(context.applicationContext)
            ) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}