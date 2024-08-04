package com.example.imagesearch.data

import com.google.gson.annotations.SerializedName

data class Image(val response : ImageResponse)

data class ImageResponse(
    @SerializedName("meta")
    val meta : ImageMeta,
    @SerializedName("documents")
    val document : ImageDocument
)

data class ImageMeta(
    @SerializedName("total_count")
    val totalCount : Int,
    @SerializedName("pageable_count")
    val pageableCount : Int,
    @SerializedName("is_end")
    val isEnd : Boolean
)

data class ImageDocument(
    @SerializedName("collection")
    val collection: String?,
    @SerializedName("datetime")
    val datetime: String?,
    @SerializedName("display_sitename")
    val displaySitename: String?,
    @SerializedName("doc_url")
    val docUrl: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,
    @SerializedName("width")
    val width: Int?
)