package com.example.imagesearch.presentation.viewmodel

import com.example.imagesearch.presentation.ListItem

interface LikeEvent {
    fun like(item : ListItem)
}