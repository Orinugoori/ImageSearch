package com.example.imagesearch.data.repository


import com.example.imagesearch.data.model.ImageResponse
import com.example.imagesearch.data.model.VideoResponse

interface SearchRepository {
    suspend fun getImageList(searchText : String, page: Int) : ImageResponse
    suspend fun getVideoList(searchText: String, page: Int) : VideoResponse

}