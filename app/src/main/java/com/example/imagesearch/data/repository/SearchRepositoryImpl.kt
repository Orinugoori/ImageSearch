package com.example.imagesearch.data.repository

import android.content.SharedPreferences
import com.example.imagesearch.data.model.ImageResponse
import com.example.imagesearch.data.model.VideoResponse
import com.example.imagesearch.data.remote.ApiInterface

class SearchRepositoryImpl (private val networkClient : ApiInterface) : SearchRepository {
    override suspend fun getImageList(searchText: String, page: Int): ImageResponse {
        return networkClient.getImage(search = searchText, page = page)
    }

    override suspend fun getVideoList(searchText: String, page: Int): VideoResponse {
        return networkClient.getVideo(search = searchText, page = page)
    }




}