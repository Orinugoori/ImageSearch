package com.example.imagesearch.presentation

import com.example.imagesearch.data.model.ImageDocument
import com.example.imagesearch.data.model.VideoDocument
import java.util.UUID

sealed class ListItem {
    abstract val uid : String
    abstract val isLiked : Boolean
    data class  ImageItem(
        override val uid : String = UUID.randomUUID().toString() ,
        val thumbnailUrl : String,
        val siteName : String,
        val datetime : String,
        override val isLiked : Boolean = false
    ):ListItem()

    data class VideoItem(
        override val uid : String = UUID.randomUUID().toString() ,
        val thumbnail : String,
        val title : String,
        val datetime: String,
        override val isLiked: Boolean = false
    ):ListItem()

}


fun toImageItem(target: List<ImageDocument>) : List<ListItem> = with(target) {
    return map{ target ->
        ListItem.ImageItem(
            thumbnailUrl = target.thumbnailUrl ?: "",
            siteName = target.displaySitename ?: "",
            datetime = target.datetime ?: "",
            isLiked = false
        )
    }
}

fun toVideoItem(target: List<VideoDocument>) :List<ListItem> = with(target){
    return map{ target ->
        ListItem.VideoItem(
            thumbnail = target.thumbnail ?: "",
            title = target.title ?: "",
            datetime = target.datetime ?: "",
            isLiked = false
        )
    }
}

fun sortedByDatetime(list : List<ListItem>) :List<ListItem>{
    return list.sortedByDescending {
        if (it is ListItem.ImageItem) it.datetime else (it as ListItem.VideoItem).datetime
    }
}
