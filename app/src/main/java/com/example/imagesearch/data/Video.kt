package com.example.imagesearch.data

import com.google.gson.annotations.SerializedName

data class Video(val response : VideoResponse)

data class VideoResponse(
    @SerializedName("meta")
    val meta : VideoMeta?,
    @SerializedName("documents")
    val documents : List<VideoDocument>?
)

data class VideoMeta(
    @SerializedName("total_count")
    val totalCount : Int,
    @SerializedName("pageable_count")
    val pageableCount : Int,
    @SerializedName("is_end")
    val isEnd : Boolean
)

data class VideoDocument(
    @SerializedName("author")
    val author: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("play_time")
    val playTime: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?
)
