package com.example.imagesearch


import com.example.imagesearch.data.Image
import com.example.imagesearch.data.Video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET("vclip")
    suspend fun getVideo(
        @Query("query") search: String,
        @Query("sort") sort : String,
        @Query("page") page : Int = 1 ,
        @Query("size") size : Int = 5): Video

    @GET("image")
    suspend fun getImage(
        @Query("query") search: String,
        @Query("sort") sort : String,
        @Query("page") page : Int = 1,
        @Query("size") size : Int = 5
    ): Image
}