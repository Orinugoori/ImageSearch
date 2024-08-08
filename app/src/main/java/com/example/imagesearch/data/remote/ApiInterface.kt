package com.example.imagesearch.data.remote


import com.example.imagesearch.data.model.ImageResponse
import com.example.imagesearch.data.model.VideoResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET("vclip")
    suspend fun getVideo(
        @Query("query") search: String,
        @Query("sort") sort : String = "recency",
        @Query("page") page : Int = 1,
        @Query("size") size : Int = 30
    ): VideoResponse

    @GET("image")
    suspend fun getImage(
        @Query("query") search: String,
        @Query("sort") sort : String = "recency",
        @Query("page") page : Int = 1,
        @Query("size") size : Int = 80
    ): ImageResponse
}