package com.example.imagesearch.data.repository

import android.util.Log
import com.example.imagesearch.presentation.ListItem
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class CustomDeserializer : JsonDeserializer<ListItem> {
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): ListItem {
        val jsonObject = json.asJsonObject
        Log.d("CustomDeserializer", "Deserializing: $jsonObject")

        return try {
            if (jsonObject.has("thumbnail")) {
                ListItem.VideoItem(
                    uid = jsonObject.get("uid")?.asString ?: "",
                    thumbnail = jsonObject.get("thumbnail")?.asString ?: "",
                    title = jsonObject.get("title")?.asString ?: "",
                    datetime = jsonObject.get("datetime")?.asString ?: "",
                    isLiked = jsonObject.get("isLiked")?.asBoolean ?: false
                )
            } else {
                ListItem.ImageItem(
                    uid = jsonObject.get("uid")?.asString ?: "",
                    thumbnailUrl = jsonObject.get("thumbnailUrl")?.asString ?: "",
                    siteName = jsonObject.get("siteName")?.asString ?: "",
                    datetime = jsonObject.get("datetime")?.asString ?: "",
                    isLiked = jsonObject.get("isLiked")?.asBoolean ?: false
                )
            }
        } catch (e: Exception) {
            Log.e("CustomDeserializer", "Deserialization failed", e)
            throw e
        }
    }
}